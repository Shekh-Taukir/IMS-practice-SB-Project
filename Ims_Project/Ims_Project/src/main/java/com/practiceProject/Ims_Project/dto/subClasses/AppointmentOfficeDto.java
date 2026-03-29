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
public class AppointmentOfficeDto extends BaseEntityVIewDto {
    private String name;
    private String code;
    private String email;
}
