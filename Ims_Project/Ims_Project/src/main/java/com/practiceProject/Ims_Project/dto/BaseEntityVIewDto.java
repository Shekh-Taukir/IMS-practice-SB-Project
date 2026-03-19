package com.practiceProject.Ims_Project.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@JsonPropertyOrder(alphabetic = false)
public abstract class BaseEntityVIewDto {
    private Long tranId;
//    private Long createdById;
//    private LocalDateTime createdAt;
//    private Long changedById;
//    private LocalDateTime changedAt;
}
