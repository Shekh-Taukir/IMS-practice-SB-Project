package com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.request;

import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDate;
import java.util.List;

/// //////////////////////////////////////////
//
// Name: Diagnosis Patch Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)

/////////////////////////////////////////////

public record DiagnosisPatchRequest(
        JsonNullable<String> note,
        JsonNullable<LocalDate> takenAt,
        JsonNullable<List<DiagnosisIcdMapRequest>> icdItemList
) {
}
