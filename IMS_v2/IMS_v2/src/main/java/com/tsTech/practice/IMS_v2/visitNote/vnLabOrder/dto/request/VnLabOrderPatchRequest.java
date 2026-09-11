package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request;

import org.openapitools.jackson.nullable.JsonNullable;

import java.time.LocalDateTime;

public record VnLabOrderPatchRequest(
        JsonNullable<Long> labOrderId,
        JsonNullable<LocalDateTime> takenAt,
        JsonNullable<String> note,
        JsonNullable<Boolean> isActive
) {
}
