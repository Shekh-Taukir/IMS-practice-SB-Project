package com.practiceProject.Ims_Project.inventory_service.dto.baseFiles;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@JsonPropertyOrder(alphabetic = false)
public abstract class BaseViewDto {

    private Long tranId;

    @NotNull(message = "isActive is required")
    private Boolean isActive;
}