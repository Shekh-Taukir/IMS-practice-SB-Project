package com.tsTech.practice.IMS_v2.office.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;

////////////////////////////////////////////////
//
// Name: Office Response
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

public record OfficeResponse(
        String officeName,
        String officeCode,
        String phoneNo,
        String faxNo,
        String address1,
        String email,
        String zipCode,
        @JsonUnwrapped BaseRecord baseRecord
) {
}
