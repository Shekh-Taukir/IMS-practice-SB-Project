package com.tsTech.practice.IMS_v2.patient.service.impl;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.patient.dtos.PatientInsuranceDTO;
import com.tsTech.practice.IMS_v2.patient.dtos.records.NextPriorityRecord;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import com.tsTech.practice.IMS_v2.patient.entities.PatientInsurance;
import com.tsTech.practice.IMS_v2.patient.enums.InsurancePriority;
import com.tsTech.practice.IMS_v2.patient.repository.PatientInsuranceRepository;
import com.tsTech.practice.IMS_v2.patient.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.patient.service.PatientInsuranceService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

////////////////////////////////////////////////
//
// Name: Patient Insurance Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 01, 2026 || TaukirS (ER 1005 - patient insurance setup)
////////////////////////////////////////////////

@Service
@RequiredArgsConstructor
public class PatientInsuranceServiceImpl implements PatientInsuranceService {

    private final PatientInsuranceRepository patientInsuranceRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PatientInsuranceDTO> getAllInsuranceByPatient(Long patientId) {
        return patientInsuranceRepository
                .findByPatient_TranId(patientId)
                .stream()
                .map(insuranceDto -> modelMapper.map(insuranceDto, PatientInsuranceDTO.class))
                .toList();
    }

    @Override
    public PatientInsuranceDTO getPatientInsuranceById(Long insId) {
        return modelMapper.map(patientInsuranceRepository.getPatientInsuranceEntityById(insId), PatientInsuranceDTO.class);
    }

    @Override
    public PatientInsuranceDTO addPatientInsuranceById(PatientInsuranceDTO patientInsuranceDTO) {
        Long patientId = patientInsuranceDTO.getPatientId();
        PatientInsurance patientInsurance = modelMapper.map(patientInsuranceDTO, PatientInsurance.class);
        Patient patient = patientRepository.getPatientEntityById(patientId);

        /// have to check that incoming priority is not set in any other insurance for that patient, other than OTHER priority
        if(patientInsuranceDTO.getPriority() != InsurancePriority.OTHER)
            if (patientInsuranceRepository.existsByPatient_TranIdAndPriority(patientId, patientInsuranceDTO.getPriority()))
                throw new EntityExistsException("Selected Insurance priority is already been used.");

        patientInsurance.setPatient(patient);

        return modelMapper.map(patientInsuranceRepository.save(patientInsurance), PatientInsuranceDTO.class);
    }

    @Override
    public Boolean deletePatientInsuranceById(Long insId) {
        PatientInsurance insurance = patientInsuranceRepository.getPatientInsuranceEntityById(insId);
        patientInsuranceRepository.delete(insurance);
        return true;
    }

    @Override
    public PatientInsuranceDTO putPatientInsuranceById(Long insId, PatientInsuranceDTO insuranceDTO) {
        PatientInsurance insurance = patientInsuranceRepository.getPatientInsuranceEntityById(insId);
        insuranceDTO.setTranId(insId);
        /// Bug here, modelMapper is changing the value of insurance.patient.tran_id to insuranceDTO.tranId, and due to that exception occurs,
        /// so have to switch to Mapstruct from modelMapper.
        modelMapper.map(insuranceDTO, insurance);
        PatientInsurance newInsurance = patientInsuranceRepository.save(insurance);
        return modelMapper.map(newInsurance, PatientInsuranceDTO.class);
    }

    @Override
    public PatientInsuranceDTO patchPatientInsuranceById(Long insId, Map<String, Object> patchUpdates) {
        PatientInsurance insurance = patientInsuranceRepository.getPatientInsuranceEntityById(insId);

        patchUpdates.forEach((key, value) -> {
            Field field = ReflectionUtils.getRequiredField(PatientInsurance.class, key);
            field.setAccessible(true);

            if(field.getType().isEnum()){
                Class<Enum> enumType = (Class<Enum>) field.getType();
                Enum enumValue = Enum.valueOf(enumType, value.toString());
                ReflectionUtils.setField(field, insurance, enumValue);
            }
            else
                ReflectionUtils.setField(field, insurance, value);
        });

        return modelMapper.map(patientInsuranceRepository.save(insurance), PatientInsuranceDTO.class);
    }

    @Override
    public NextPriorityRecord getNextPriority(Long patientId){
        Set<String> currentPriorities = new HashSet<>(patientInsuranceRepository.getCurrentPriorities(patientId));

        if (currentPriorities.isEmpty())
            throw new ResourceNotFoundException("Patient not found for id: "+patientId);

        for (InsurancePriority prio : InsurancePriority.values())
            if (!currentPriorities.contains(prio.toString()))
                return new NextPriorityRecord(prio.toString(), currentPriorities);

        return new NextPriorityRecord(InsurancePriority.OTHER.toString(), currentPriorities);
    }
}
