package com.tsTech.practice.IMS_v2.office.dtos.request;

import com.tsTech.practice.IMS_v2.common.annotations.EntityStringValidation;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserPrefixEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

////////////////////////////////////////////////
//
// Name: Provider Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

public record ProviderRequest(
        @EntityStringValidation
        String firstName,

        @EntityStringValidation
        String lastName,

        @EntityStringValidation
        String middleName,

        @EntityStringValidation
        String suffix,

        @Enumerated(value = EnumType.STRING)
        @NotNull(message="Prefix cannot be null")
        UserPrefixEnum prefix,

        @EntityStringValidation
        String designation,

        @NotBlank(message = "address1 cannot be null")
        String address1,

        @NotNull(message = "OfficeId cannot be null")
        Long officeId,

        @Email
        @NotBlank(message = "email cannot be null")
        String email,

        String npi,

        @NotNull(message = "Birth date cannot be null")
        @Past(message = "birth Date cannot be present or future")
        LocalDate birthDate
) {
}
