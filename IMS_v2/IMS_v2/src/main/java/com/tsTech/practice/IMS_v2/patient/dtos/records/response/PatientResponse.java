package com.tsTech.practice.IMS_v2.patient.dtos.records.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserPrefixEnum;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserSexEnum;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserSexualOrientation;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: Patient Response Record
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 24, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

public record PatientResponse(
        String firstName,
        String lastName,
        String middleName,
        String aka,
        String address1,
        String address2,
        LocalDate birthDate,
        LocalDate deceasedDate,
        String email,
        UserPrefixEnum prefix,
        UserSexEnum sex,
        String note,
        UserSexualOrientation sexualOrientation,
        @JsonUnwrapped BaseRecord baseRecord
) {
}
