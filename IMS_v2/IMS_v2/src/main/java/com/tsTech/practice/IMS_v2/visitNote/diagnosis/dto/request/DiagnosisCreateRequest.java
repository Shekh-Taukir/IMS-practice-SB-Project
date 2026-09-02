package com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

/////////////////////////////////////////////
//
// Name: Diagnosis Create Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

public record DiagnosisCreateRequest(
        @NotNull(message = "Taken date cannot be null")
        @PastOrPresent
        LocalDate takenAt,
        String note,

        @Valid
        @NotEmpty(message = "Icd item list cannot be null")
        @Size(max = 8, message = "Icd list cannot contain more than 8 icds")
        List<DiagnosisIcdMapRequest> icdItemList
) {
}
