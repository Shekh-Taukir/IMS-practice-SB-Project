package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request;

import org.openapitools.jackson.nullable.JsonNullable;

/// //////////////////////////////////////////
//
// Name: VisitNote Careplan Patch Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1018 - visit careplan entity coding)

/// //////////////////////////////////////////

public record VnCareplanPatchRequest(
        JsonNullable<String> description,
        JsonNullable<Long> seq,
        JsonNullable<Long> careplanId,
        JsonNullable<Boolean> isActive
) {
}
