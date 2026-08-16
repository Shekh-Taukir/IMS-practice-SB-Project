package com.tsTech.practice.IMS_v2.scheduler.service.impl;

import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import com.tsTech.practice.IMS_v2.patient.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.scheduler.dto.request.CaseRequest;
import com.tsTech.practice.IMS_v2.scheduler.dto.response.CaseResponse;
import com.tsTech.practice.IMS_v2.scheduler.entities.Case;
import com.tsTech.practice.IMS_v2.scheduler.mapper.CaseMapper;
import com.tsTech.practice.IMS_v2.scheduler.repository.CaseRepository;
import com.tsTech.practice.IMS_v2.scheduler.service.CaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/////////////////////////////////////////////
//
// Name: Case Service IMPL
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 16, 2026 || TaukirS (ER 1013 - case master coding)
/////////////////////////////////////////////

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {

    private final CaseRepository caseRepository;
    private final PatientRepository patientRepository;
    private final CaseMapper caseMapper;

    @Override
    @Transactional
    public CaseResponse createCase(Long patientId, CaseRequest request) {
        log.debug("Entering createCase() | patientId: {}", patientId);

        //TODO: remove officeId from request, because as patient is fixed for case, then so do office.

        Patient patient = patientRepository.getPatientEntityById(patientId);
        Case case1 = caseMapper.fromRequestToEntity(request);

        case1.setPatient(patient);
        log.debug("Setting office to case entity");
        case1.setOffice(patient.getOffice());

        log.debug("saving repo + converting entity to response");
        CaseResponse response = caseMapper.toResponse(caseRepository.save(case1));
        logResult("Case Created", "createCase", patientId, null, request, response);
        return response;
    }

    @Override
    public CaseResponse getCaseById(Long patientId, Long caseId) {
        log.debug("Entering getCaseById | caseId: {} | patientId: {}", caseId, patientId);
        CaseResponse response = caseMapper.fromProjectionToResponse(
                caseRepository.getCaseByIdAndPatientId(caseId, patientId)
        );

        logResult("Retrieved Case by Id", "getCaseById", patientId, caseId, null, response);
        return response;
    }

    @Override
    public List<CaseResponse> getAllCases(Long patientId) {
        log.debug("Entering getAllCases | patientId: {}", patientId);
        patientRepository.patientExistsById(patientId);
        List<CaseResponse> responses = caseMapper.fromProjectionToResponse(
                caseRepository.findAllWithPatientAndOffice(patientId)
        );

        logResult("Retrieved case list by patientId", "getAllCases", patientId, null, null, responses);
        return responses;
    }

    @Override
    @Transactional
    public CaseResponse updateCaseById(Long patientId, Long caseId, CaseRequest request) {
        log.debug("Entering updateCaseById | patientId: {} | caseId: {}", patientId, caseId);
        patientRepository.patientExistsById(patientId);

        Case case1 = caseRepository.getCaseEntityByIdAndPatientId(caseId, patientId);
        caseMapper.updateEntityFromRequest(request, case1);
        CaseResponse response = caseMapper.toResponse(caseRepository.save(case1));

        logResult("Updated case by id", "updateCaseById", patientId, caseId, request, response);
        return response;
    }

    @Override
    @Transactional
    public void deleteCaseById(Long patientId, Long caseId) {
        log.debug("Entering  deleteCaseById | patientId: {} | caseId: {}", patientId, caseId);
        patientRepository.patientExistsById(patientId);

        caseRepository.delete(
                caseRepository.getCaseEntityByIdAndPatientId(caseId, patientId)
        );
        logResult("Deleted case by id", "deleteCaseById", patientId, caseId, null, null);
    }

    @Override
    @Transactional
    public CaseResponse patchCaseById(Long patientId, Long caseId, Map<String, Object> patchData) {
        log.debug("Entering  patchCaseById | patientId: {} | caseId: {}", patientId, caseId);
        patientRepository.getPatientEntityById(patientId);
        Case case1 = caseRepository.getCaseEntityByIdAndPatientId(caseId, patientId);

        patchData.forEach((field, value)->{
            Field fieldToBeUpdated = ReflectionUtils.getRequiredField(Case.class, field);
            fieldToBeUpdated.setAccessible(true);

            ReflectionUtils.setField(fieldToBeUpdated, case1, value);
        });
        CaseResponse response = caseMapper.toResponse(caseRepository.save(case1));

        logResult("Case partial update by id", "patchCaseById", patientId, caseId, patchData, response);
        return response;
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    private void logResult(String action, String methodName, Long patientId, Long caseId, Object inputData, Object resultData){
        String logString = "Case Master : "+action+" | "+methodName+"() | patientId: "+patientId;

        if(caseId!=null)
            logString+=" | caseId: "+caseId;

        log.debug(logString);

        if(log.isTraceEnabled()){

            if(inputData!=null)
                logString+=" | inputData: "+inputData;

            if(resultData!=null)
                logString+=" | resultData: "+resultData;

            log.trace(logString);
        }

    }
}
