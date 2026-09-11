package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request;

import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisIcdMapRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Vn Lab Order Icd Map Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 11, 2026 || TaukirS (ER 1022 - vn lab order icd map entity coding)

/// //////////////////////////////////////////

public record VnLabOrderIcdMapRequest(
        @Valid
        @NotEmpty(message = "Icd item list cannot be null")
        @Size(max = 8, message = "Icd list cannot contain more than 8 icds")
        List<DiagnosisIcdMapRequest> icdItemList
) {
}
