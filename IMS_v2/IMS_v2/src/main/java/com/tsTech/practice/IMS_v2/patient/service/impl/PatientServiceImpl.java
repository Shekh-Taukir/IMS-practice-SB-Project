package com.tsTech.practice.IMS_v2.patient.service.impl;

import com.tsTech.practice.IMS_v2.patient.dtos.PatientDTO;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
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
// v1.3 || type : Change || Jun 29, 2026 || TaukirS (ER 1005 - patient insurance setup)
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
        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        /// made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        return modelMapper.map(patientRepository.getPatientEntityById(tranId), PatientDTO.class);
    }

    @Override
    public PatientDTO updatePatientById(Long tranId, PatientDTO patientDTO) {
        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        /// made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        Patient patient = patientRepository.getPatientEntityById(tranId);
        patientDTO.setTranId(tranId);
        modelMapper.map(patientDTO, patient);
        return modelMapper.map(patientRepository.save(patient), PatientDTO.class);

    }

    @Override
    public Boolean deletePatientById(Long tranId) {
        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        /// made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        Patient patient = patientRepository.getPatientEntityById(tranId);
        patientRepository.delete(patient);
        return true;
    }

    @Override
    public PatientDTO patchPatientById(Long tranId, Map<String, Object> patchData) {
        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        /// made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        Patient patient = patientRepository.getPatientEntityById(tranId);

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
}
