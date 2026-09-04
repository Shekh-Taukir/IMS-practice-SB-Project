package com.tsTech.practice.IMS_v2.visitNote.diagnosis.service.impl;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.common.exception.DuplicateResourceException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.patient.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.setup.icd.entities.ICD;
import com.tsTech.practice.IMS_v2.setup.icd.repository.IcdRepository;
import com.tsTech.practice.IMS_v2.visitNote.core.entities.VisitNote;
import com.tsTech.practice.IMS_v2.visitNote.core.repository.VisitNoteRepository;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.projection.DiagnosisProjection;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisCreateRequest;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisIcdMapRequest;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.response.DiagnosisIcdMapResponse;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.response.DiagnosisResponse;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.entity.Diagnosis;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.entity.DiagnosisIcdMap;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.mapper.DiagnosisIcdMapMapper;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.mapper.DiagnosisMapper;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.repository.DiagnosisIcdMapRepository;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.repository.DiagnosisRepository;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.service.DiagnosisService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/// //////////////////////////////////////////
//
// Name: Diagnosis Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)

/// //////////////////////////////////////////

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final DiagnosisIcdMapRepository diagnosisIcdMapRepository;

    private final VisitNoteRepository visitNoteRepository;
    private final DiagnosisIcdMapRepository icdMapRepository;
    private final IcdRepository icdRepository;
    private final PatientRepository patientRepository;

    private final DiagnosisMapper diagnosisMapper;
    private final DiagnosisIcdMapMapper diagnosisIcdMapMapper;

    private final Validator validator;

    @Override
    @Transactional
    public DiagnosisResponse createDiagnosis(Long pnId, DiagnosisCreateRequest request) {
        log.debug("Entering createVisit() | pnId: {}", pnId);

        checkDuplicateSeqAndIcdInRequest(request.icdItemList());

        VisitNote visitNote = visitNoteRepository.getVisitNoteEntityById(pnId);
        diagnosisRepository.checkDiagnosisExistsByPnId(pnId);

        Diagnosis diagnosis = diagnosisMapper.fromRequestToEntity(request);
        diagnosis.setVisitNote(visitNote);
        diagnosis.setPatient(visitNote.getPatient());

        //new diagnosis saved
        Diagnosis updatedDiagnosis = diagnosisRepository.save(diagnosis);

        //get the list of icd mapping objects
        List<DiagnosisIcdMap> diagnosisIcdMapList = request
                .icdItemList()
                .stream()
                .map(icdItem -> {
                    ICD icd = icdRepository.getEntityById(icdItem.icdId());

                    return DiagnosisIcdMap
                            .builder()
                            .diagnosis(updatedDiagnosis)
                            .icd(icd)
                            .seq(icdItem.seq())
                            .build();
                })
                .toList();

        List<DiagnosisIcdMapResponse> updatedIcdItemRequest = diagnosisIcdMapMapper.toResponseList(
                icdMapRepository.saveAll(diagnosisIcdMapList)
        );
        DiagnosisResponse response = diagnosisMapper.toResponse(updatedDiagnosis, updatedIcdItemRequest);
        logResult("Created Diagnosis", "createDiagnosis", pnId, null, request, response);
        return response;
    }

    @Override
    public DiagnosisResponse getDiagnosisById(Long pnId, Long diagnosisId) {
        log.debug("Entering getDiagnosisById() | pnId: {} | diagnosisId: {}", pnId, diagnosisId);
        DiagnosisProjection diagnosisProjection = diagnosisRepository.getDiagnosisProjOrThrow(pnId, diagnosisId);

        List<DiagnosisIcdMapResponse> icdItemResponses = diagnosisIcdMapMapper.fromProjectionToResponseList(
                diagnosisIcdMapRepository.getDiagnosisIcdProjection(diagnosisId)
        );
        DiagnosisResponse response = diagnosisMapper.fromProjectionToResponse(diagnosisProjection, icdItemResponses);

        logResult("Diagnosis Retrieved", "getDiagnosisById", pnId, diagnosisId, null, response);
        return response;
    }

    @Override
    public List<DiagnosisResponse> getDiagnosisByPatientId(Long patientId) {
        log.debug("Entering getDiagnosisById() | patientId: {} ", patientId);
        patientRepository.patientExistsById(patientId);

        List<DiagnosisProjection> diagnosisProjectionList = diagnosisRepository.getDiagnosisProjectionListByPatientId(patientId);
        List<DiagnosisIcdMapResponse> diagnosisIcdMapResponseList = diagnosisIcdMapMapper.fromProjectionToResponseList(
                diagnosisIcdMapRepository.getDiagnosisIcdProjectionByPatientId(patientId)
        );

        Map<Long, List<DiagnosisIcdMapResponse>> diagnosisToIcdListMapping = diagnosisIcdMapResponseList
                .stream()
                .collect(Collectors.groupingBy(DiagnosisIcdMapResponse::diagnosisId));

        List<DiagnosisResponse> responseList = diagnosisProjectionList
                .stream()
                .map(diagnosisProjection -> diagnosisMapper
                        .fromProjectionToResponse(
                                diagnosisProjection,
                                diagnosisToIcdListMapping.get(diagnosisProjection.getTranId())
                        ))
                .toList();

        logResult("Patient's Diagnosis Retrieved", "getDiagnosisByPatientId", null, null, "PatientID: " + patientId, responseList);
        return responseList;
    }

    @Override
    @Transactional
    public DiagnosisResponse updateDiagnosis(Long pnId, Long diagnosisId, DiagnosisCreateRequest request) {
        log.debug("Entering updateDiagnosis() | pnId: {} | diagnosisId: {}", pnId, diagnosisId);
        Diagnosis diagnosis = diagnosisRepository.getEntityById(pnId, diagnosisId);

        diagnosisMapper.updateEntityFromRequest(request, diagnosis);

        //common coding
        /*//checks for the list of seq / icdId provided by user is unique or not?
        checkDuplicateSeqInRequest(request);

        //region Declarations & entity fetch code
        HashMap<Long, DiagnosisIcdMap> diagIcdHashMap =
                (HashMap<Long, DiagnosisIcdMap>) diagnosisIcdMapRepository
                        .findByDiagnosis_TranId(diagnosisId)
                        .stream()
                        .collect(
                                Collectors.toMap(x -> x
                                                .getIcd()
                                                .getTranId(),
                                        Function.identity())
                        );

        /// Holds a map of (icdId, seq) coming from user request obj
        Map<Long, Long> requestIcdIdMap = request
                .icdItemList()
                .stream()
                .collect(Collectors.toMap(DiagnosisIcdMapRequest::icdId, DiagnosisIcdMapRequest::seq));

        /// Set of icdIds which are new, and its not present in existing mapping
        Set<Long> newIcdIdSet = requestIcdIdMap
                .keySet()
                .stream()
                .filter(x -> !diagIcdHashMap.containsKey(x))
                .collect(Collectors.toSet());

        Map<Long, ICD> icdIdMap = getNewIcdsFromReq(newIcdIdSet);
        //endregion

        //region update seq for existing icd mappings
        requestIcdIdMap.forEach((newIcdId, newSeq) -> {
            log.trace("iterating for icd: {}", newIcdId);

            ///checks the newIcd exists in existing list and if exists then checks that does its seq is updated, if yes, then updates the seq
            if (diagIcdHashMap.containsKey(newIcdId) && !diagIcdHashMap
                    .get(newIcdId)
                    .getSeq()
                    .equals(newSeq)) {
                ///this blocks updates the seq for matching icd of existing and new request's icd list
                log.trace("icd: {} | icd exists but seq needs to be updated", newIcdId);
                diagIcdHashMap
                        .get(newIcdId)
                        .setSeq(newSeq);
            }
        });
        //endregion

        //region merge new icds and compute deletions
        icdIdMap
                .values()
                .forEach((icd -> {
                    Long newIcdId = icd.getTranId();
                    diagIcdHashMap.put(
                            newIcdId,
                            DiagnosisIcdMap
                                    .builder()
                                    .diagnosis(diagnosis)
                                    .seq(requestIcdIdMap.get(newIcdId)) //old it was this : .seq(newIcdIdAndSeqMap.get(newIcdId))
                                    .icd(icd)
                                    .build()
                    );
                }));

        Set<Long> icdToBeDeleted = diagIcdHashMap
                .keySet()
                .stream()
                .filter(x -> !requestIcdIdMap.containsKey(x))
                .collect(Collectors.toSet());

        List<DiagnosisIcdMap> toBeDeletedIcdMap = icdToBeDeleted
                .stream()
                .map(diagIcdHashMap::remove)
                .toList();

        List<DiagnosisIcdMap> finalDiagnosisIcdMapList = diagIcdHashMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList();
        //endregion

        //region final response objects and DB updates
        diagnosisIcdMapRepository.deleteAll(toBeDeletedIcdMap);*/

        List<DiagnosisIcdMapResponse> finalDiagIcdMapResponseList = diagnosisIcdMapMapper.toResponseList(
                diagnosisIcdMapRepository.saveAll(updateDiagnosisIcdMapping(diagnosis, request.icdItemList()))
        );
        DiagnosisResponse response = diagnosisMapper.toResponse(
                diagnosisRepository.save(diagnosis),
                finalDiagIcdMapResponseList
        );

        logResult("Diagnosis Updated", "updateDiagnosis", pnId, diagnosisId, request, response);
        return response;
        //endregion
    }

    @Override
    @Transactional
    public DiagnosisResponse patchUpdateDiangnosisById(Long pnId, Long diagnosisId, DiagnosisPatchRequest request) {
        log.debug("Entering patchDiagnosis() | pnId: {} | diagnosisId: {}", pnId, diagnosisId);
        Diagnosis diagnosis = diagnosisRepository.getEntityById(pnId, diagnosisId);

        diagnosisMapper.patch(request, diagnosis);
        List<DiagnosisIcdMapResponse> updatedIcdMap = null;
        JsonNullable<List<DiagnosisIcdMapRequest>> icdMapRequest = request.icdItemList();

        if (icdMapRequest != null && icdMapRequest.isPresent()) {
            //validates the user input if user have provided the icd mapping list
            validateDiagnosisIcdMapRequestData(icdMapRequest.get());

            updatedIcdMap = diagnosisIcdMapMapper.toResponseList(
                    diagnosisIcdMapRepository.saveAll(updateDiagnosisIcdMapping(diagnosis, icdMapRequest.get()))
            );
        }

        DiagnosisResponse response = diagnosisMapper.toResponse(
                diagnosisRepository.save(diagnosis),
                updatedIcdMap
        );
        logResult("Diagnosis Partial Updated", "patchUpdateDiangnosisById", pnId, diagnosisId, request, response);
        return response;
    }

    @Override
    @Transactional
    public void deleteDiagnosisById(Long pnId, Long diagnosisId) {
        log.debug("Entering deleteDiagnosisById() | pnId: {} | diagnosisId: {}", pnId, diagnosisId);
        Diagnosis diagnosis = diagnosisRepository.getEntityById(pnId, diagnosisId);
        diagnosisRepository.delete(diagnosis);
        logResult("Deleted", "deleteDiagnosisById", pnId, diagnosisId, null, null);
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    private static void logResult(String action, String methodName, Long pnId, Long diagnosisId, Object inputData, Object resultData) {
        String logString = "Diagnosis: " + action + " | " + methodName;

        if (pnId != null)
            logString += " | pnId: " + pnId;

        if (diagnosisId != null)
            logString += " | diagnosisId: " + diagnosisId;

        log.debug(logString);
        if (log.isTraceEnabled()) {

            if (inputData != null)
                logString += " | inputData: " + inputData;

            if (resultData != null)
                logString += " | resultData: " + resultData;

            log.trace(logString);
        }
    }

    private static void checkDuplicateSeqAndIcdInRequest(List<DiagnosisIcdMapRequest> requestList) {
        //Checking that sequence list in request object is not getting repeated
        Set<Long> uniqueSets = new HashSet<>(
                requestList
                        .stream()
                        .map(DiagnosisIcdMapRequest::seq) //Other way to write this: icdItem -> icdItem.seq()
                        .toList()
        );

        if (uniqueSets.size() != requestList
                .size())
            throw new DuplicateResourceException("DUPLICATE_ICD_SEQUENCE", "Duplicate Icd sequence provided in input");

        Set<Long> uniqueIcds = new HashSet<>(
                requestList
                        .stream()
                        .map(DiagnosisIcdMapRequest::icdId) //Other way to write this: icdItem -> icdItem.icdId()
                        .toList()
        );
        if (uniqueIcds.size() != requestList.size())
            throw new DuplicateResourceException("DUPLICATE_ICD_ID", "Duplicate Icd ids provided in input");
    }

    private Map<Long, ICD> getNewIcdsFromReq(Set<Long> newIcdIdSet) {

        Map<Long, ICD> icdIdMap = icdRepository
                .findAllById(newIcdIdSet)
                .stream()
                .collect(Collectors
                        .toMap(BaseEntity::getTranId, Function.identity())
                );

        List<Long> missingIcdId = newIcdIdSet
                .stream()
                .filter(x -> !icdIdMap.containsKey(x))
                .toList();

        if (!missingIcdId.isEmpty())
            throw new ResourceNotFoundException("ICD", missingIcdId);

        return icdIdMap;
    }

    private void validateDiagnosisIcdMapRequestData(List<DiagnosisIcdMapRequest> icdMapRequestList) {

        Set<ConstraintViolation<DiagnosisIcdMapRequest>> violations = icdMapRequestList
                .stream()
                .flatMap(item -> validator
                        .validate(item)
                        .stream())
                .collect(Collectors.toSet());

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed for nested Icd Mappings", violations);
        }
    }

    private List<DiagnosisIcdMap> updateDiagnosisIcdMapping(Diagnosis diagnosis, List<DiagnosisIcdMapRequest> requestList) {
        Long diagnosisId = diagnosis.getTranId();
        log.debug("Entering updateDiagnosisIcdMapping() | diagnosisId: {}", diagnosisId);
        //checks for the list of seq / icdId provided by user is unique or not?
        checkDuplicateSeqAndIcdInRequest(requestList);

        //region Declarations & entity fetch code
        Map<Long, DiagnosisIcdMap> diagIcdHashMap =
                diagnosisIcdMapRepository
                        .findByDiagnosis_TranId(diagnosisId)
                        .stream()
                        .collect(
                                Collectors.toMap(x -> x
                                                .getIcd()
                                                .getTranId(),
                                        Function.identity())
                        );

        /// Holds a map of (icdId, seq) coming from user request obj
        Map<Long, Long> requestIcdIdMap = requestList
                .stream()
                .collect(Collectors.toMap(
                        DiagnosisIcdMapRequest::icdId,
                        DiagnosisIcdMapRequest::seq
                ));

        /// Set of icdIds which are new, and its not present in existing mapping
        Set<Long> newIcdIdSet = requestIcdIdMap
                .keySet()
                .stream()
                .filter(x -> !diagIcdHashMap.containsKey(x))
                .collect(Collectors.toSet());

        Map<Long, ICD> icdIdMap = getNewIcdsFromReq(newIcdIdSet);
        //endregion

        //region update seq for existing icd mappings
        requestIcdIdMap.forEach((newIcdId, newSeq) -> {
            log.trace("iterating for icd: {}", newIcdId);

            ///checks the newIcd exists in existing list and if exists then checks that does its seq is updated, if yes, then updates the seq
            if (diagIcdHashMap.containsKey(newIcdId) && !diagIcdHashMap
                    .get(newIcdId)
                    .getSeq()
                    .equals(newSeq)) {
                ///this blocks updates the seq for matching icd of existing and new request's icd list
                log.trace("icd: {} | icd exists but seq needs to be updated", newIcdId);
                diagIcdHashMap
                        .get(newIcdId)
                        .setSeq(newSeq);
            }
        });
        //endregion

        //region merge new icds and compute deletions
        icdIdMap
                .values()
                .forEach((icd -> {
                    Long newIcdId = icd.getTranId();
                    diagIcdHashMap.put(
                            newIcdId,
                            DiagnosisIcdMap
                                    .builder()
                                    .diagnosis(diagnosis)
                                    .seq(requestIcdIdMap.get(newIcdId)) //old it was this : .seq(newIcdIdAndSeqMap.get(newIcdId))
                                    .icd(icd)
                                    .build()
                    );
                }));

        Set<Long> icdToBeDeleted = diagIcdHashMap
                .keySet()
                .stream()
                .filter(x -> !requestIcdIdMap.containsKey(x))
                .collect(Collectors.toSet());

        List<DiagnosisIcdMap> toBeDeletedIcdMap = icdToBeDeleted
                .stream()
                .map(diagIcdHashMap::remove)
                .toList();

        List<DiagnosisIcdMap> finalDiagnosisIcdMapList = diagIcdHashMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toList();
        //endregion

        //final response objects and DB updates
        diagnosisIcdMapRepository.deleteAll(toBeDeletedIcdMap);

        logResult("", "updateDiagnosis", null, diagnosisId, requestList, finalDiagnosisIcdMapList);
        return finalDiagnosisIcdMapList;
    }

}
