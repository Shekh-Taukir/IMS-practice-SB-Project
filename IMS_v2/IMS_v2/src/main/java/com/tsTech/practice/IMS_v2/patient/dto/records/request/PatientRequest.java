package com.tsTech.practice.IMS_v2.patient.dto.records.request;

import com.tsTech.practice.IMS_v2.common.annotations.EntityStringValidation;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserPrefixEnum;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserSexEnum;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserSexualOrientation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: Patient Request Record
//
// Description:
//
// Version history:
//
 // v1.1 || type : Change || Jul 24, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
 // v1.2 || type : Change || Jul 30, 2026 || TaukirS (ER 1011 - flyway integration & add office and provider in patient)
////////////////////////////////////////////////

public record PatientRequest(

        @EntityStringValidation()
        String firstName,

        @EntityStringValidation(min = 3, max = 10)
        String lastName,

        @EntityStringValidation()
        String middleName,

        @EntityStringValidation()
        String aka,

        @NotBlank(message = "Address 1 cannot be blank")
        String address1,

        @NotBlank(message = "Address 2 cannot be blank")
        String address2,

        @PastOrPresent(message = "BirthDate cannot be future date")
        @NotNull(message = "BirthDate cannot be null")
        LocalDate birthDate,

        @PastOrPresent(message = "Deceased date cannot be future date")
        LocalDate deceasedDate,

        @NotNull(message = "Email cannot be null")
        @Email(message = "Provide a valid email")
        String email,

        @Enumerated(EnumType.STRING)
        @NotNull(message = "Prefix field cannot be null")
        UserPrefixEnum prefix,

        @Enumerated(EnumType.STRING)
        @NotNull(message = "Sex field cannot be null")
        UserSexEnum sex,

        String note,

        @NotNull(message = "Sexual Orientation field cannot be null")
        @Enumerated(EnumType.STRING)
        UserSexualOrientation sexualOrientation,

        //Start Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
        @NotNull(message = "Office cannot be null")
        Long officeId,

        @NotNull(message = "Provider cannot be null")
        Long providerId
        //End Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
) {
}
