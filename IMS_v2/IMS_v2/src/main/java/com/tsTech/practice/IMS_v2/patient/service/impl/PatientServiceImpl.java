package com.tsTech.practice.IMS_v2.patient.service.impl;

import com.tsTech.practice.IMS_v2.patient.dtos.PatientDTO;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.patient.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

////////////////////////////////////////////////
//
// Name: PatientServiceImpl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
// v1.2 || type : Change || Jun 25, 2026 || TaukirS (ER 1003 - validation and generalize response and error coding)
////////////////////////////////////////////////

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository
                .findAll()
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO addPatient(PatientDTO patientDTO) {
        Patient patient = modelMapper.map(patientDTO, Patient.class);
        return modelMapper.map(patientRepository.save(patient), PatientDTO.class);
    }

    @Override
    public PatientDTO getPatientById(Long tranId) {
        return modelMapper.map(getPatientEntityById(tranId), PatientDTO.class);
    }

    @Override
    public PatientDTO updatePatientById(Long tranId, PatientDTO patientDTO) {
        Patient patient = getPatientEntityById(tranId);
        patientDTO.setTranId(tranId);
        modelMapper.map(patientDTO, patient);
        return modelMapper.map(patientRepository.save(patient), PatientDTO.class);

    }

    @Override
    public Boolean deletePatientById(Long tranId) {
        Patient patient = getPatientEntityById(tranId);
        patientRepository.delete(patient);
        return true;
    }

    @Override
    public PatientDTO patchPatientById(Long tranId, Map<String, Object> patchData) {
        Patient patient = getPatientEntityById(tranId);

        patchData.forEach((key, value)->{
            Field fieldToBeUpdated = ReflectionUtils.getRequiredField(Patient.class, key);
            fieldToBeUpdated.setAccessible(true);

            if(fieldToBeUpdated.getType().isEnum()){
                Class<Enum> enumType = (Class<Enum>) fieldToBeUpdated.getType();
                Enum enumValue = Enum.valueOf(enumType, value.toString());
                ReflectionUtils.setField(fieldToBeUpdated, patient, enumValue);
            } else
                ReflectionUtils.setField(fieldToBeUpdated, patient, value);
        });
        return modelMapper.map(patientRepository.save(patient), PatientDTO.class);

    }

    //Internal Function
    public Patient getPatientEntityById(Long tranId){
        return patientRepository
                .findById(tranId)
                .orElseThrow(()->new ResourceNotFoundException("Patient not found for id: "+tranId));
    }
}
