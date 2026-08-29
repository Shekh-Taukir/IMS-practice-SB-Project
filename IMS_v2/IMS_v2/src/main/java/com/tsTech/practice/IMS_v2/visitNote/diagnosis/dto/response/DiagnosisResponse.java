package com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;

import java.time.LocalDate;
import java.util.List;

/////////////////////////////////////////////
//
// Name: Diagnosis Response
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

public record DiagnosisResponse(
        Long pnId,
        String vnDescription,
        Long patientId,
        String patientName,
        LocalDate takenAt,
        String note,
        List<DiagnosisIcdMapResponse> icdItemList,
        @JsonUnwrapped BaseRecord baseRecord
        ) {
}
