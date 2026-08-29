package com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.response;

/////////////////////////////////////////////
//
// Name: Diagnosis Icd Item Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)

import com.fasterxml.jackson.annotation.JsonInclude;

/////////////////////////////////////////////

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DiagnosisIcdMapResponse(
        Long diagnosisId,
        Long icdId,
        Long seq,
        String code,
        String description,
        Long diagnosisIcdMapId
) {
}
