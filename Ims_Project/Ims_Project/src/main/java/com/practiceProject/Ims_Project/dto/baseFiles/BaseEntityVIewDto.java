package com.practiceProject.Ims_Project.dto.baseFiles;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@JsonPropertyOrder(alphabetic = false)
public abstract class BaseEntityVIewDto {
    private Long tranId;

    @NotNull(message = "isActive is required")
    private Boolean isActive;
//    private Long createdById;
//    private LocalDateTime createdAt;
//    private Long changedById;
//    private LocalDateTime changedAt;
}
