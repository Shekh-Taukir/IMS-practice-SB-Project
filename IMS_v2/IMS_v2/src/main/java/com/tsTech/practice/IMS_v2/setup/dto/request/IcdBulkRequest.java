package com.tsTech.practice.IMS_v2.setup.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record IcdBulkRequest(
        @NotEmpty(message = "Icd list cannot be null")
        @Valid
        List<IcdRequest> icdRequestList
) {
}
