package com.tsTech.practice.IMS_v2.patient.dto.projectionInterface;

import com.tsTech.practice.IMS_v2.common.dto.projectionInterface.BaseProjection;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserPrefixEnum;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserSexEnum;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserSexualOrientation;

import java.time.LocalDate;

////////////////////////////////////////////////
//
// Name: Patient Projection Interface
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 30, 2026 || TaukirS (ER 1011 - flyway integration & add office and provider in patient)
////////////////////////////////////////////////

public interface PatientProjection extends BaseProjection {
    String getFirstName();
    String getLastName();
    String getMiddleName();
    String getAka();
    String getAddress1();
    String getAddress2();
    LocalDate getBirthDate();
    LocalDate getDeceasedDate();
    String getEmail();
    UserPrefixEnum getPrefix();
    UserSexEnum getSex();
    String getNote();
    UserSexualOrientation getSexualOrientation();
    String getOfficeName();
    String getProviderName();
    Long getOfficeId();
    Long getProviderId();
}
