package com.practiceProject.Ims_Project.service.impl;

import com.practiceProject.Ims_Project.dto.PatientDto;
import com.practiceProject.Ims_Project.entity.Patient;
import com.practiceProject.Ims_Project.exception.ResourceNotFoundException;
import com.practiceProject.Ims_Project.repository.PatientRepository;
import com.practiceProject.Ims_Project.service.PatientService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PatientDto> getPatientsList() {
        List<Patient> patientList = patientRepository.findAll();

        return patientList
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .toList();
    }

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient newPatient = modelMapper.map(patientDto, Patient.class);
        newPatient = patientRepository.save(newPatient);
        return modelMapper.map(newPatient, PatientDto.class);
    }

    @Override
    public PatientDto getPatientById(Long patientId) {
        isPatientExists(patientId);
        Patient patient = patientRepository.findById(patientId).get();

        return modelMapper.map(patient,PatientDto.class);
//        return patientRepository.findById(patientId).map(patient -> modelMapper.map(patient, PatientDto.class));
    }

    @Override
    public PatientDto updatePatient(Long patientId, PatientDto patientDto) {
        isPatientExists(patientId);
        Patient patient = patientRepository.findById(patientId).get();

        patientDto.setTranId(patientId);
        modelMapper.map(patientDto, patient);
        patient = patientRepository.save(patient);

        return modelMapper.map(patient,PatientDto.class);
    }

    @Override
    public String deletePatientById(Long patientId) {
        isPatientExists(patientId);
        patientRepository.deleteById(patientId);
        return "Patient Deleted | ID : "+patientId;
    }

    @Override
    public PatientDto updatePartialPatient(Long patientId, Map<String, Object> updates) {
        isPatientExists(patientId);

        Patient patient = patientRepository.findById(patientId).get();

        updates.forEach((field, value)->{
            Field fieldToBeUdpated = ReflectionUtils.getRequiredField(Patient.class,field);
            fieldToBeUdpated.setAccessible(true);

            if (fieldToBeUdpated.getType().isEnum()){
                ReflectionUtils.setField(
                        fieldToBeUdpated,
                        patient,
                        Enum.valueOf((Class<Enum>)fieldToBeUdpated.getType(),value.toString()));
            }
            else {
                ReflectionUtils.setField(fieldToBeUdpated, patient, value);
            }
        });

        return modelMapper.map(patientRepository.save(patient),PatientDto.class);
    }


    //----------------------INternal Methods for Service Methods
    private void isPatientExists(Long patientId){
        if (!patientRepository.existsById(patientId))
            throw new ResourceNotFoundException("Patient not found for id : "+patientId);
    }
}
