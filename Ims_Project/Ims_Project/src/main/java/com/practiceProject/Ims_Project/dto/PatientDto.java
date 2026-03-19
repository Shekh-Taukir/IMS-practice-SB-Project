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
import jakarta.validation.constraints.*;
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

//    @NotNull(message = "first name is required")
    //Notnull is used when have to reject field : null

    //NotEmpty is used to reject | field : "" | field : null
//    @NotEmpty(message = "first name is required")

    //Not Blank is used to reject | field : "   " | field : "" | field : null
    //generally used for string fields
    @NotBlank(message = "first name is required")
    @Size(min = 2,  max = 50, message = "First Name field should be or range 2 to 50 chars")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(min = 2,  max = 50, message = "Last Name field should be of range 2 to 50 chars")
    private String lastName;

    @Size(min = 2,  max = 50, message = "middle Name field should be of range 2 to 50 chars")
    private String middleName;

    @Size(min = 2,  max = 50, message = "aka field should be of range 2 to 50 chars")
    private String aka;

    private String address1;
    private String address2;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate deceasedDate;

    @Email(message = "Email value must be a valid Email")
    @NotBlank(message = "Email field is required")
    private String email;

    //Enums dependent fields
    @Enumerated(EnumType.STRING)
    private UserPrefixEnum prefix;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Blood Group field is required")
    private BloodGroupEnum bloodGroup;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Sex field is required")
    private UserSexEnum sex;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Sexual Orientation is a required field")
    private UserSexOrientationEnum sexualOrientation;
}
