package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;

import java.time.LocalDateTime;

public record VnLabOrderResponse(
        Long pnId,
        Long labOrderId,
        LocalDateTime takenAt,
        String note,
        @JsonUnwrapped BaseRecord baseRecord

        //TODO: Need to add this in future
//        Long patientId,
//        Long officeId,
//        Long providerId,
) {
}
