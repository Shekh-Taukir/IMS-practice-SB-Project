package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

/// //////////////////////////////////////////
//
// Name: VisitNote Careplan Entity
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1018 - visit careplan entity coding)

/// //////////////////////////////////////////

public record VnCareplanRequest(
        @NotBlank(message = "Description cannot be blank")
        @Length(message = "size of description cannot be more than 1000 chars", max = 1000)
        String description,

        @Positive(message = "Seq cannot be negative or zero")
        Long seq,

        @NotNull(message = "Careplan Id cannot be null")
        @Positive(message = "Careplan Id cannot be negative or zero")
        Long careplanId
) {
}
