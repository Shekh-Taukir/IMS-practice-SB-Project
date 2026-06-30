package com.tsTech.practice.IMS_v2.patient.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tsTech.practice.IMS_v2.common.annotations.EntityStringValidation;
import com.tsTech.practice.IMS_v2.common.dto.base.BaseDTO;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserPrefixEnum;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserSexEnum;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserSexualOrientation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: PatientDTO
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1002 - patient mst apis)
// v1.2 || type : Change || Jun 24, 2026 || TaukirS (ER 1003 - validation and generalize response and error coding)
////////////////////////////////////////////////

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"tranId"})
public class PatientDTO extends BaseDTO {

    @EntityStringValidation()
    private String firstName;

    @EntityStringValidation(min = 3, max = 10)
    private String lastName;

    @EntityStringValidation()
    private String middleName;

    @EntityStringValidation()
    private String aka;

    @NotBlank(message = "Address 1 cannot be blank")
    private String address1;

    @NotBlank(message = "Address 2 cannot be blank")
    private String address2;

    @PastOrPresent(message = "BirthDate cannot be future date")
    @NotNull(message = "BirthDate cannot be null")
    private LocalDate birthDate;

    @PastOrPresent(message = "Deceased date cannot be future date")
    private LocalDate deceasedDate;

    @NotNull(message = "Email cannot be null")
    @Email(message = "Provide a valid email")
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Prefix field cannot be null")
    private UserPrefixEnum prefix;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Sex field cannot be null")
    private UserSexEnum sex;

    private String note;

    @NotNull(message = "Sexual Orientation field cannot be null")
    @Enumerated(EnumType.STRING)
    private UserSexualOrientation sexualOrientation;
}
