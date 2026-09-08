package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request.DiagnosisIcdMapRequest;

import java.time.LocalDate;
import java.util.List;

/// //////////////////////////////////////////
//
// Name: Vn Careplan ICD Map Patch Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 08, 2026 || TaukirS (ER 1021 - vn careplan icd map entity coding)

/// //////////////////////////////////////////

public record VnCareplanIcdMapResponse(
        Long vnCareplanId,
        Long icdId,
        Long seq,
        String code,
        String description,
        @JsonUnwrapped BaseRecord baseRecord
) {
}
