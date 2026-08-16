package com.tsTech.practice.IMS_v2.scheduler.service;

/////////////////////////////////////////////
//
// Name: Case Service
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 16, 2026 || TaukirS (ER 1013 - case master coding)

import com.tsTech.practice.IMS_v2.scheduler.dto.request.CaseRequest;
import com.tsTech.practice.IMS_v2.scheduler.dto.response.CaseResponse;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;

/////////////////////////////////////////////

public interface CaseService {

    CaseResponse createCase(Long patientId, CaseRequest request);

    CaseResponse getCaseById(Long patientId, Long caseId);

    List<CaseResponse> getAllCases(Long patientId);

    CaseResponse updateCaseById(Long patientId, Long caseId, CaseRequest request);

    void deleteCaseById(Long patientId, Long caseId);

    CaseResponse patchCaseById(Long patientId, Long caseId, Map<String, Object> patchData);
}
