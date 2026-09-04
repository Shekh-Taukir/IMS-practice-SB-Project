package com.tsTech.practice.IMS_v2.setup.icd.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record IcdBulkRequest(
        @NotEmpty(message = "Icd list cannot be null")
        @Valid
        List<IcdRequest> icdRequestList
) {
}
