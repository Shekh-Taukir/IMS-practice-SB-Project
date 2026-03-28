package com.practiceProject.Ims_Project.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.practiceProject.Ims_Project.dto.baseFiles.BaseEntityVIewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@JsonPropertyOrder(value = {"tranId"})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentDto extends BaseEntityVIewDto {

    private LocalDate apptDate;
    private LocalTime apptTime;
    private String note;

    private Boolean isConfirmed;
    private Boolean isRescheduled;

    private Integer duration;

    private Long patientId;
    private Long officeId;
    private Long doctorId;
}
