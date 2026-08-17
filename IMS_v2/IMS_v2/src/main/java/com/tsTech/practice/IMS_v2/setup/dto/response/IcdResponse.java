package com.tsTech.practice.IMS_v2.setup.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: Icd Response
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 17, 2026 || TaukirS (ER 1014 - icd entity setup coding)
/////////////////////////////////////////////

public record IcdResponse(
        String code,
        String description,
        String note,
        LocalDate expiredAt,
        @JsonUnwrapped BaseRecord baseRecord
) {
}
