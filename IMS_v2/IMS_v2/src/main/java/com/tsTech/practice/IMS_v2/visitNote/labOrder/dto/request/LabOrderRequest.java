package com.tsTech.practice.IMS_v2.visitNote.labOrder.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/// //////////////////////////////////////////
//
// Name: LabOrder Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 06, 2026 || TaukirS (ER 1019 - - lab order entity coding)
/// //////////////////////////////////////////

public record LabOrderRequest(
        @NotBlank(message = "Lab Name cannot be null")
        @Length(max = 100, message = "lab name cannot be of more than 100 characters")
        String labName,

        @NotBlank(message = "Lab Test Name cannot be null")
        @Length(max = 200, message = "lab name cannot be of more than 200 characters")
        String labTestName,

        @Length(max = 1000, message = "Description cannot be of more than 1000 characters")
        String description,

        @Length(max = 1000, message = "Note cannot be of more than 1000 characters")
        String note
) {
}
