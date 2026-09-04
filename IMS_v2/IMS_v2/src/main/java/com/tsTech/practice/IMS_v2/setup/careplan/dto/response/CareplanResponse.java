package com.tsTech.practice.IMS_v2.setup.careplan.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;

public record CareplanResponse(
        String name,
        Boolean toBePrint,
        @JsonUnwrapped BaseRecord baseRecord
) {
}
