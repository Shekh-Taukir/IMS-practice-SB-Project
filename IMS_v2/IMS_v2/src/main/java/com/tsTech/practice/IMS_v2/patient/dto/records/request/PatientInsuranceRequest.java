package com.tsTech.practice.IMS_v2.patient.dto.records.request;

import com.tsTech.practice.IMS_v2.patient.enums.InsurancePriority;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: Patient Insurance Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 24, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

public record PatientInsuranceRequest (
    @NotBlank(message = "Plan name cannot be null")
    String planName,

    @NotBlank(message = "Plan code cannot be null")
    String planCode,
    Double copay,

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Insurance priority cannot be null")
    InsurancePriority priority,

//    @NotNull(message = "patient Id cannot be null")
//    Long patientId,

    @Column(length = 2000)
    String note,

    LocalDate expiresAt
){
}
