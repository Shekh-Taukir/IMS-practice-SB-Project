package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;

/// //////////////////////////////////////////
//
// Name: VisitNote Careplan Response
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1018 - visit careplan entity coding)

/// //////////////////////////////////////////

public record VnCareplanResponse(
        String description,
        Long seq,
        Long careplanId,
        Long pnId,
        @JsonUnwrapped BaseRecord baseRecord
) {
}
