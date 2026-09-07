package com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;

/// //////////////////////////////////////////
//
// Name: LabOrder Response
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 06, 2026 || TaukirS (ER 1019 - lab order entity coding)
/// //////////////////////////////////////////

public record LabOrderResponse(
        String labName,
        String labTestName,
        String description,
        String note,
        @JsonUnwrapped BaseRecord baseRecord
) {
}
