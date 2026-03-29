package com.practiceProject.Ims_Project.dto.subClasses;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.practiceProject.Ims_Project.dto.baseFiles.BaseEntityVIewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(value = {"tranId"})
public class AppointmentDoctorDto extends BaseEntityVIewDto {
    private String firstName;
    private String lastName;
    private String specialization;
    private String ssno;
}
