package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.request;

import java.time.LocalDateTime;

public record VnLabOrderRequest(
        Long labOrderId,
        LocalDateTime takenAt,
        String note
) {
}
