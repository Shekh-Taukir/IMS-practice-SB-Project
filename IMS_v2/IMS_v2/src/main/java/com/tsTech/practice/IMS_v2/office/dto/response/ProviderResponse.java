package com.tsTech.practice.IMS_v2.office.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserPrefixEnum;

import java.time.LocalDate;

////////////////////////////////////////////////
//
// Name: Provider Response
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

public record ProviderResponse (
    String firstName,
    String lastName,
    String middleName,
    String suffix,
    UserPrefixEnum prefix,
    String designation,
    String address1,
    Long officeId,
    String email,
    String npi,
    LocalDate birthDate,
    @JsonUnwrapped BaseRecord baseRecord
){
}
