package com.tsTech.practice.IMS_v2.patient.service.impl;

import com.tsTech.practice.IMS_v2.patient.dtos.PatientDTO;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import com.tsTech.practice.IMS_v2.patient.mapper.PatientMapper;
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
// v1.4 || type : Change || Jul 01, 2026 || TaukirS (ER 1006 - mapStruct setup changes)
////////////////////////////////////////////////

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
    private final PatientMapper patientMapper;

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientRepository
                .findAll()
                .stream()
                //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
                .map(patient -> patientMapper.toDto(patient))
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO addPatient(PatientDTO patientDTO) {
        Patient patient = patientMapper.toEntity(patientDTO);
        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        return patientMapper.toDto(patientRepository.save(patient));
    }

    @Override
    public PatientDTO getPatientById(Long tranId) {
        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        /// made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        return patientMapper.toDto(patientRepository.getPatientEntityById(tranId));
    }

    @Override
    public PatientDTO updatePatientById(Long tranId, PatientDTO patientDTO) {
        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        /// made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        Patient patient = patientRepository.getPatientEntityById(tranId);
        patientDTO.setTranId(tranId);
        //Start Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        patientMapper.updateEntityFromDto(patientDTO, patient);
        return patientMapper.toDto(patientRepository.save(patient));
        //End Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)

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

        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        return patientMapper.toDto(patientRepository.save(patient));
    }
}
