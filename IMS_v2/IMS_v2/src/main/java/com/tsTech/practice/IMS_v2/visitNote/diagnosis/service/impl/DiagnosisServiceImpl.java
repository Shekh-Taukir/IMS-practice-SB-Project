package com.tsTech.practice.IMS_v2.visitNote.diagnosis.service.impl;

import com.tsTech.practice.IMS_v2.common.exception.DuplicateResourceException;
import com.tsTech.practice.IMS_v2.patient.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.setup.entities.ICD;
import com.tsTech.practice.IMS_v2.setup.repository.IcdRepository;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/////////////////////////////////////////////
//
// Name: Diagnosis Service Impl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

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

    @Override
    @Transactional
    public DiagnosisResponse createDiagnosis(Long pnId, DiagnosisCreateRequest request) {
        log.debug("Entering createVisit() | pnId: {}", pnId);

        checkDuplicateSeqInRequest(request);

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
                .map(icdItem->{
                    ICD icd = icdRepository.getEntityById(icdItem.icdId());

                    return DiagnosisIcdMap.builder()
                            .diagnosis(updatedDiagnosis)
                            .icd(icd)
                            .seq(icdItem.seq())
                            .build();
                }).toList();

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

        logResult("Patient's Diagnosis Retrieved", "getDiagnosisByPatientId", null, null, "PatientID: "+patientId, responseList);
        return responseList;
    }

    @Override
    @Transactional
    public DiagnosisResponse updateDiagnosis(Long pnId, Long diagnosisId, DiagnosisCreateRequest request) {
        log.debug("Entering updateDiagnosis() | pnId: {} | diagnosisId: {}", pnId, diagnosisId);
        Diagnosis diagnosis = diagnosisRepository.getEntityById(pnId, diagnosisId);
        List<DiagnosisIcdMap> diagnosisIcdMapList = diagnosisIcdMapRepository.findByDiagnosis_TranId(diagnosisId);

        HashMap<Long, DiagnosisIcdMap> diagIcdHashMap = (HashMap<Long, DiagnosisIcdMap>) diagnosisIcdMapList
                .stream()
                .collect(
                        Collectors.toMap(x->x.getIcd().getTranId(), Function.identity())
                );

        HashMap<Long, DiagnosisIcdMap> diagIcdSeqHashMap = (HashMap<Long, DiagnosisIcdMap>) diagnosisIcdMapList
                .stream()
                .collect(
                        Collectors.toMap(x->x.getSeq(), Function.identity())
                );

        Set<Long> icdIdsSet = new HashSet<>(diagIcdHashMap.keySet());

///        ******************Icd Map update Loop******************
        request.icdItemList().forEach((diagnosisIcdMapItr -> {
            Long newIcdId = diagnosisIcdMapItr.icdId();
            Long newSeq = diagnosisIcdMapItr.seq();

            log.debug("iterating for icd: {}", newIcdId);

///            checks the newIcd exists in existing list and if exists then checks that does its seq is updated, if yes, then updates the seq
            if(diagIcdHashMap.containsKey(newIcdId)){
                log.debug("icd: {} | icd exists in orig list", newIcdId);
                Long existingSeq = diagIcdHashMap.get(newIcdId).getSeq();
                icdIdsSet.remove(newIcdId);

///                this blocks updates the seq for matching icd of existing and new request's icd list
                if(!existingSeq.equals(newSeq)) {
                    log.debug("icd: {} | icd exists but seq needs to be updated", newIcdId);

///                    as in obj, seq is getting updated in next statement, so to make seq map in sync, have to update the obj's key value mapping.:
//                    eg: initially data was seq: 1 -> obj: 10, and | new data is seq:2 -> obj : 10
                    if(diagIcdSeqHashMap.containsKey(newSeq)) {
                        diagIcdSeqHashMap.replace(newSeq, diagIcdHashMap.get(newIcdId));
//                        diagIcdSeqHashMap.remove(existingSeq);
                    }
                    else
                        diagIcdSeqHashMap.put(newSeq, diagIcdHashMap.get(newIcdId));

                    diagIcdHashMap.get(newIcdId).setSeq(newSeq);
                    //after updating the entity's seq, we have to remove the old entries for which seq's key and icd objects seq are mismatch, and that are the old entries, so have to delete that
                    if(!diagIcdSeqHashMap.get(existingSeq).getSeq().equals(existingSeq))
                        diagIcdSeqHashMap.remove(existingSeq);

                }
            }
            //checks if newIcd's seq exists in existing icdMap list, and newIcd doesn't exists, if get matching seq, with unmatched icd, then have to update the icdId
            else if(diagIcdSeqHashMap.containsKey(newSeq) && !diagIcdHashMap.containsKey(newIcdId)) {
                log.debug("icd: {} | icd doesn't exists but seq exists", newIcdId);
                Long existingIcdId = diagIcdSeqHashMap
                        .get(newSeq)
                        .getIcd()
                        .getTranId();

                icdIdsSet.remove(existingIcdId);
                /*diagIcdHashMap.get(diagIcdSeqHashMap
                                .get(diagnosisIcdMapItr.seq())
                                .getIcd()
                                .getTranId())
                        .setIcd(icdRepository.getEntityById(newIcdId));*/
                diagIcdHashMap.put(newIcdId, diagIcdHashMap.remove(existingIcdId));
                diagIcdHashMap.get(newIcdId).setIcd(icdRepository.getEntityById(newIcdId));
            }

            //if new icd doesn't exists in existing icdMap list and newIcd's seq also doesn't collapse with any existing icd, then have to add that
            else {
                log.debug("icd: {} | brand new icd", newIcdId);
                diagIcdHashMap.put(
                        newIcdId,
                        DiagnosisIcdMap.builder()
                                .diagnosis(diagnosis)
                                .seq(newSeq)
                                .icd(icdRepository.getEntityById(newIcdId))
                                .build()
                );
            }
        }));

        List<DiagnosisIcdMap> toBeDeletedIcdMap = (!icdIdsSet.isEmpty()) ?
                icdIdsSet
                        .stream()
                        .map(diagIcdHashMap::remove)
                        .toList():
                null;

        diagnosisIcdMapList = diagIcdHashMap
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(x->x.getValue())
                .toList();

        if(toBeDeletedIcdMap!=null)
            diagnosisIcdMapRepository.deleteAll(toBeDeletedIcdMap);

        List<DiagnosisIcdMapResponse> finalDiagIcdMapResponseList = diagnosisIcdMapMapper
                .toResponseList( diagnosisIcdMapRepository.saveAll(diagnosisIcdMapList) );

        DiagnosisResponse response = diagnosisMapper
                .toResponse(
                        diagnosisRepository.save(diagnosis),
                        finalDiagIcdMapResponseList
                );

        logResult("Diagnosis Updated", "updateDiagnosis", pnId, diagnosisId, request, response);
        return response;
    }

    @Override
    @Transactional
    public DiagnosisResponse patchUpdateDiangnosisById(Long pnId, Long diagnosisId, DiagnosisPatchRequest request) {
        return null;
    }

    @Override
    @Transactional
    public void deleteDiagnosisById(Long pnId, Long diagnosisId) {

    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    private static void logResult(String action, String methodName, Long pnId, Long diagnosisId, Object inputData, Object resultData){
        String logString = "Diagnosis: "+action+" | "+methodName;

        if(pnId!=null)
            logString+=" | pnId: "+pnId;

        if(diagnosisId!=null)
            logString+=" | diagnosisId: "+diagnosisId;

        log.debug(logString);
        if(log.isTraceEnabled()){

            if(inputData!=null)
                logString+=" | inputData: "+inputData;

            if(resultData!=null)
                logString+=" | resultData: "+resultData;

            log.trace(logString);
        }
    }

    private static void checkDuplicateSeqInRequest(DiagnosisCreateRequest request) {
        //Checking that sequence list in request object is not getting repeated
        Set<Long>  uniqueSets = new HashSet<>(
                request
                        .icdItemList()
                        .stream()
                        .map(DiagnosisIcdMapRequest::seq) //Other way to write this: icdItem -> icdItem.seq()
                        .toList()
        );

        if(uniqueSets.size()!= request.icdItemList().size())
            throw new DuplicateResourceException("DUPLICATE_ICD_SEQUENCE", "Duplicate Icd sequence provided in input");
    }

}
