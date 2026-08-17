package com.tsTech.practice.IMS_v2.setup.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

/////////////////////////////////////////////
//
// Name: Icd Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 17, 2026 || TaukirS (ER 1014 - icd entity setup coding)
/////////////////////////////////////////////

public record IcdRequest(
        @NotBlank(message = "ICD code cannot be blank")
        @Length(max = 7, min = 3, message = "ICD-10 code can be of range 3 to 7 chars")
        @Pattern(regexp = "^[a-zA-Z][0-9a-zA-Z.]+$", message = "Provide a valid ICD code")
        String code,
        String description,
        String note
) {
}
