package com.tsTech.practice.IMS_v2.service.impl;

import com.tsTech.practice.IMS_v2.dtos.PatientDTO;
import com.tsTech.practice.IMS_v2.entities.Patient;
import com.tsTech.practice.IMS_v2.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    public Optional<PatientDTO> getPatientById(Long tranId) {
        return getPatientEntityById(tranId)
                .map(patient->modelMapper.map(patient, PatientDTO.class));
    }

    @Override
    public Optional<PatientDTO> updatePatientById(Long tranId, PatientDTO patientDTO) {

        return getPatientEntityById(tranId).map(patient -> {
           patientDTO.setTranId(tranId);
           modelMapper.map(patientDTO, patient);
           return modelMapper.map(patientRepository.save(patient), PatientDTO.class);
        });
    }

    @Override
    public Boolean deletePatientById(Long tranId) {
        return getPatientEntityById(tranId).map(patient1->{
            patientRepository.delete(patient1);
            return true;
        }).orElse(false);

    }

    @Override
    public Optional<PatientDTO> patchPatientById(Long tranId, Map<String, Object> patchData) {
        return getPatientEntityById(tranId).map(patient -> {
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
        });
    }

    //Internal Function
    public Optional<Patient> getPatientEntityById(Long tranId){
        return patientRepository.findById(tranId);
    }
}
