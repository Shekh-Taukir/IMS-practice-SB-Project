package com.tsTech.practice.IMS_v2.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tsTech.practice.IMS_v2.dtos.base.BaseDTO;
import com.tsTech.practice.IMS_v2.entityEnums.UserPrefixEnum;
import com.tsTech.practice.IMS_v2.entityEnums.UserSexEnum;
import com.tsTech.practice.IMS_v2.entityEnums.UserSexualOrientation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
////////////////////////////////////////////////

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"tranId"})
public class PatientDTO extends BaseDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String aka;

    private String address1;
    private String address2;

    private LocalDate birthDate;
    private LocalDate deceasedDate;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserPrefixEnum prefix;

    @Enumerated(EnumType.STRING)
    private UserSexEnum sex;

    private String note;

    @Enumerated(EnumType.STRING)
    private UserSexualOrientation sexualOrientation;

}
