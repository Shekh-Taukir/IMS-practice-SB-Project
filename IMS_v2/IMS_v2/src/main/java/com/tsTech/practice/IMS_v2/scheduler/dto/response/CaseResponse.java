package com.tsTech.practice.IMS_v2.scheduler.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;

/////////////////////////////////////////////
//
// Name: Case Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 16, 2026 || TaukirS (ER 1013 - case master coding)
/////////////////////////////////////////////

public record CaseResponse(
        String name,
        String description,
        Long patientId,
        Long officeId,
        String patientName,
        String officeName,
        String note,
        @JsonUnwrapped BaseRecord baseRecord
) {
}
