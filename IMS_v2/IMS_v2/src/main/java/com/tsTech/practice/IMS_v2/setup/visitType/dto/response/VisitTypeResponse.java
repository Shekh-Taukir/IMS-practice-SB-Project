package com.tsTech.practice.IMS_v2.setup.visitType.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;

/// //////////////////////////////////////////
//
// Name: Visit Type Response
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 15, 2026 || TaukirS (ER 1012 - visit type entity code)

/////////////////////////////////////////////

public record VisitTypeResponse(
        String name,
        String note,
        @JsonUnwrapped BaseRecord baseRecord
) {
}
