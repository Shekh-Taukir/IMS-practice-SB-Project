package com.tsTech.practice.IMS_v2.patient.service.impl;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.patient.dtos.PatientInsuranceDTO;
import com.tsTech.practice.IMS_v2.patient.dtos.records.NextPriorityRecord;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import com.tsTech.practice.IMS_v2.patient.entities.PatientInsurance;
import com.tsTech.practice.IMS_v2.patient.enums.InsurancePriority;
import com.tsTech.practice.IMS_v2.patient.mapper.InsuranceMapper;
import com.tsTech.practice.IMS_v2.patient.repository.PatientInsuranceRepository;
import com.tsTech.practice.IMS_v2.patient.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.patient.service.PatientInsuranceService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

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
// v1.2 || type : Change || Jul 01, 2026 || TaukirS (ER 1006 - mapStruct setup changes)
////////////////////////////////////////////////

@Service
@RequiredArgsConstructor
public class PatientInsuranceServiceImpl implements PatientInsuranceService {

    private final PatientInsuranceRepository patientInsuranceRepository;
    private final PatientRepository patientRepository;
    //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
    private final InsuranceMapper insuranceMapper;

    @Override
    public List<PatientInsuranceDTO> getAllInsuranceByPatient(Long patientId) {
        return patientInsuranceRepository
                .findByPatient_TranId(patientId)
                .stream()
                //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
                .map(insurance -> insuranceMapper.toDto(insurance))
                .toList();
    }

    @Override
    public PatientInsuranceDTO getPatientInsuranceById(Long insId) {
        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        return insuranceMapper.toDto(patientInsuranceRepository.getPatientInsuranceEntityById(insId));
    }

    @Override
    public PatientInsuranceDTO addPatientInsuranceById(PatientInsuranceDTO patientInsuranceDTO) {
        Long patientId = patientInsuranceDTO.getPatientId();
        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        PatientInsurance patientInsurance = insuranceMapper.toEntity(patientInsuranceDTO);
        Patient patient = patientRepository.getPatientEntityById(patientId);

        /// have to check that incoming priority is not set in any other insurance for that patient, other than OTHER priority
        if(patientInsuranceDTO.getPriority() != InsurancePriority.OTHER)
            if (patientInsuranceRepository.existsByPatient_TranIdAndPriority(patientId, patientInsuranceDTO.getPriority()))
                throw new EntityExistsException("Selected Insurance priority is already been used.");

        patientInsurance.setPatient(patient);

        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        return insuranceMapper.toDto(patientInsuranceRepository.save(patientInsurance));
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
        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        /// Bug here, modelMapper is changing the value of insurance.patient.tran_id to insuranceDTO.tranId, and due to that exception occurs,
        /// so have to switch to Mapstruct from modelMapper.
        insuranceMapper.updateEntityFromDto(insuranceDTO, insurance);
//        modelMapper.map(insuranceDTO, insurance);
        PatientInsurance newInsurance = patientInsuranceRepository.save(insurance);
        return insuranceMapper.toDto(newInsurance);
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

        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        return insuranceMapper.toDto(patientInsuranceRepository.save(insurance));
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
