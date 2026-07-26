package com.tsTech.practice.IMS_v2.patient.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.tsTech.practice.IMS_v2.common.dto.base.BaseDTO;
import com.tsTech.practice.IMS_v2.patient.enums.InsurancePriority;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

////////////////////////////////////////////////
//
// Name: Patient Insurance DTO
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 29, 2026 || TaukirS (ER 1005 - patient insurance setup)
////////////////////////////////////////////////

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"tranId"})
@ToString
public class PatientInsuranceDTO extends BaseDTO {
    private String planName;
    private String planCode;
    private Double copay;

    @Enumerated(EnumType.STRING)
    private InsurancePriority priority;

//    @JsonProperty("patient_id")
    private Long patientId;

    @Column(length = 2000)
    private String note;

    private LocalDate expiresAt;
}
