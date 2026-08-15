package com.tsTech.practice.IMS_v2.patient.dto.records.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;
import com.tsTech.practice.IMS_v2.patient.enums.InsurancePriority;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: Patient Insurance Response
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 24, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

public record PatientInsuranceResponse(
        String planName,
        String planCode,
        Double copay,
        InsurancePriority priority,
        Long patientId,
        String note,
        LocalDate expiresAt,
        @JsonUnwrapped BaseRecord baseRecord
        ){
}
