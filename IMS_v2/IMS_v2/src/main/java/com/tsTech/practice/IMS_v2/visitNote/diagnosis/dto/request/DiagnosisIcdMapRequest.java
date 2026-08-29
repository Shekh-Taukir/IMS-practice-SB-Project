package com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/////////////////////////////////////////////
//
// Name: Diagnosis Icd Item Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

public record DiagnosisIcdMapRequest(
        @NotNull(message = "Icd Id cannot be null")
        @Positive(message = "icdId should be a positive value")
        Long icdId,

        @NotNull(message = "Sequence cannot be null")
        @Max(value = 8, message = "sequence for icd cannot be more than 8")
        @Positive(message = "Seq should be a positive value")
        Long seq
) {
}
