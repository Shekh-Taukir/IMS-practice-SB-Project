package com.practiceProject.Ims_Project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.practiceProject.Ims_Project.dto.baseFiles.BaseEntityVIewDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(value = {"tranId"})
public class OfficeDto extends BaseEntityVIewDto {
    private String name;
    private String code;
    private String phoneNumber;
    private String email;
    private String faxNumber;
    private Boolean isBilling;
    private String npi;
}
