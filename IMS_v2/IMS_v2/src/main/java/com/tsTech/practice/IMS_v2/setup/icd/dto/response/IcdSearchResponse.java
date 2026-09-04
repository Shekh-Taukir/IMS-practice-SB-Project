package com.tsTech.practice.IMS_v2.setup.icd.dto.response;

public record IcdSearchResponse(
        Long tranId,
        String code,
        String description
) {
}
