package com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.request;

import org.openapitools.jackson.nullable.JsonNullable;

/// //////////////////////////////////////////
//
// Name: LabOrder Patch Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 06, 2026 || TaukirS (ER 1019 - - lab order entity coding)
/// //////////////////////////////////////////

public record LabOrderPatchRequest(
        JsonNullable<String> labName,
        JsonNullable<String> labTestName,
        JsonNullable<String> description,
        JsonNullable<String> note
) {
}
