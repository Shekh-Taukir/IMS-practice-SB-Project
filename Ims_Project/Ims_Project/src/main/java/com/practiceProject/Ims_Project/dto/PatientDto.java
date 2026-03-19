package com.practiceProject.Ims_Project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.practiceProject.Ims_Project.entity.type.BloodGroupEnum;
import com.practiceProject.Ims_Project.entity.type.UserPrefixEnum;
import com.practiceProject.Ims_Project.entity.type.UserSexEnum;
import com.practiceProject.Ims_Project.entity.type.UserSexOrientationEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(value = {"tranId"}, alphabetic = true)
public class PatientDto extends BaseEntityVIewDto{
    private String firstName;
    private String lastName;
    private String middleName;
    private String aka;
    private String address1;
    private String address2;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate deceasedDate;

    //Enums dependent fields
    @Enumerated(EnumType.STRING)
    private UserPrefixEnum prefix;

    @Enumerated(EnumType.STRING)
    private BloodGroupEnum bloodGroup;

    @Enumerated(EnumType.STRING)
    private UserSexEnum sex;

    @Enumerated(EnumType.STRING)
    private UserSexOrientationEnum sexualOrientation;

    private Boolean isActive;
}
