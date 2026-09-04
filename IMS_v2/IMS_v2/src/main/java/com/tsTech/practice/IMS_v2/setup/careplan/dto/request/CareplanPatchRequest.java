package com.tsTech.practice.IMS_v2.setup.careplan.dto.request;

import org.openapitools.jackson.nullable.JsonNullable;

public record CareplanPatchRequest(
        JsonNullable<String> name,
        JsonNullable<Boolean> toBePrint,
        JsonNullable<Boolean> isActive
) {
}
