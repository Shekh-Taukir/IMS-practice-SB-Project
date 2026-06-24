package com.tsTech.practice.IMS_v2.dtos.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDTO {

    private Long tranId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonProperty("isActive")
    private Boolean isActive = true;
}
