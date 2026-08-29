package com.tsTech.practice.IMS_v2.visitNote.diagnosis.service;

import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisCreateRequest;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisPatchRequest;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.response.DiagnosisResponse;
import jakarta.validation.Valid;

import java.util.List;

/////////////////////////////////////////////
//
// Name: Diagnosis Service
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

public interface DiagnosisService {
    DiagnosisResponse createDiagnosis(Long pnId, DiagnosisCreateRequest request);

    DiagnosisResponse getDiagnosisById(Long pnId, Long diagnosisId);

    List<DiagnosisResponse> getDiagnosisByPatientId(Long patientId);

    DiagnosisResponse updateDiagnosis(Long pnId, Long diagnosisId, DiagnosisCreateRequest request);

    DiagnosisResponse patchUpdateDiangnosisById(Long pnId, Long diagnosisId, DiagnosisPatchRequest request);

    void deleteDiagnosisById(Long pnId, Long diagnosisId);
}
