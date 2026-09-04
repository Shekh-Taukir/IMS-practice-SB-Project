package com.tsTech.practice.IMS_v2.setup.careplan.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record CareplanRequest(
        @NotBlank(message = "Name cannot be blank")
        @Length(max = 200, message = "name cannot be of more than 200 chars")
        String name,

        @NotNull(message = "To Be print cannot be null")
        Boolean toBePrint
) {
}
