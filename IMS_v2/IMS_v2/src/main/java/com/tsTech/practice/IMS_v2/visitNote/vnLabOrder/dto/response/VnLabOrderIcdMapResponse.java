package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.response;

import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisIcdMapRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Vn Lab Order Icd Map Response
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 11, 2026 || TaukirS (ER 1022 - vn lab order icd map entity coding)

/// //////////////////////////////////////////

public record VnLabOrderIcdMapResponse(
        Long vnLabOrderId,
        Long icdId,
        Long seq,
        String code,
        String description,
        Long tranId
) {
}
