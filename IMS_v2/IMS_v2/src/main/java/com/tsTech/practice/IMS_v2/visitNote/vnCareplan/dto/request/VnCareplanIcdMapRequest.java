package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request;

import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisIcdMapRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/// //////////////////////////////////////////
//
// Name: Vn Careplan ICD Map Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 08, 2026 || TaukirS (ER 1021 - vn careplan icd map entity coding)

/// //////////////////////////////////////////

public record VnCareplanIcdMapRequest(
        @Valid
        @NotEmpty(message = "Icd item list cannot be null")
        @Size(max = 8, message = "Icd list cannot contain more than 8 icds")
        List<DiagnosisIcdMapRequest> icdItemList
) {
}
