package com.tsTech.practice.IMS_v2.patient.service.impl;

import com.tsTech.practice.IMS_v2.patient.dtos.records.PatientRequest;
import com.tsTech.practice.IMS_v2.patient.dtos.records.PatientResponse;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import com.tsTech.practice.IMS_v2.patient.mapper.PatientMapper;
import com.tsTech.practice.IMS_v2.patient.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
// v1.5 || type : Change || Jul 24, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

@Service
@RequiredArgsConstructor
@Slf4j  //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
    private final PatientMapper patientMapper;

    @Override
    public List<PatientResponse> getAllPatients() {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering getAllPatients()");

        List<PatientResponse> patientResponseList = patientRepository
                .findAll()
                .stream()
                //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
                .map(patient -> patientMapper.fromEntityToResponse(patient))
                .toList();

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        logResult("List Retrieved","getAllPatients",null, null, patientResponseList);
        return patientResponseList;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientResponse getPatientById(Long patientId) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering getPatientById()");

        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        // made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        PatientResponse patientResponse =  patientMapper.fromEntityToResponse(patientRepository.getPatientEntityById(patientId));

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        logResult("Patient Retrieved","getPatientById", patientId, null, patientResponse);
        return patientResponse;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientResponse addPatient(PatientRequest patientRequest) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering addPatient()");

        Patient patient = patientMapper.fromRequestToEntity(patientRequest);
        //Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        PatientResponse newPatient = patientMapper.fromEntityToResponse(patientRepository.save(patient));

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        logResult("Added new patient","addPatient",null, patientRequest, newPatient);
        return newPatient;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientResponse updatePatientById(Long patientId, PatientRequest patientRequest) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering updatePatientById()");

        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        /// made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        Patient patient = patientRepository.getPatientEntityById(patientId);
//        patientRequest.(patientId);
        //Start Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
        patientMapper.updateEntityFromRequest(patientRequest, patient);
        PatientResponse updatedPatient =  patientMapper.fromEntityToResponse(patientRepository.save(patient));

        logResult("Patient Updated","updatePatientById",patientId, patientRequest, updatedPatient);
        return updatedPatient;
        //End Jul 01, 2026 TaukirS (ER 1006 - mapStruct setup changes)
    }

    @Override
    public Boolean deletePatientById(Long patientId) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering deletePatientById()");

        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        // made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        Patient patient = patientRepository.getPatientEntityById(patientId);
        patientRepository.delete(patient);

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        logResult("Patient Deleted","deletePatientById",patientId, null, null);
        return true;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    @Override
    public PatientResponse patchPatientById(Long patientId, Map<String, Object> patchData) {
        //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        log.debug("Entering patchPatientById()");

        //Jun 29, 2026 TaukirS (ER 1005 - patient insurance setup)
        /// made the getPatientEntityByID function in repo, so that it can be used in other entity's service layer as well.
        Patient patient = patientRepository.getPatientEntityById(patientId);

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
        PatientResponse updatedPatient = patientMapper.fromEntityToResponse(patientRepository.save(patient));

        //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
        logResult("Patient partially updated","patchPatientById", patientId, patchData, updatedPatient);
        return updatedPatient;
        //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    //Start Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    private void logResult(String action, String methodName, Long patientId, Object userData, Object dtoResult){
        String debugString = "Patient Mst | " + action + " | " + methodName + "()";

        if(patientId != null)
            debugString+= " | patientId: " + patientId;

        log.debug(debugString);

        if(log.isTraceEnabled()) {
            if (userData != null)
                debugString += " \n userData: " + userData;

            if (dtoResult != null)
                debugString += " \n result: " + dtoResult;
            log.trace(debugString);
        }
    }
    //End Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
}
