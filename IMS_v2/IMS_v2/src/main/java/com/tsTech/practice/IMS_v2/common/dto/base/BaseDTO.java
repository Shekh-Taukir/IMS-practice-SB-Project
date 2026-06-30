package com.tsTech.practice.IMS_v2.common.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/////////////////////////////////////////////
//
// Name: Base DTO
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1002 - patient mst apis)
// v1.2 || type : Change || Jun 24, 2026 || TaukirS (ER 1003 - validation and generalize response and error coding)
////////////////////////////////////////////////

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDTO {

    private Long tranId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @JsonProperty("isActive")
    @NotNull(message = "isActive field cannot be null")
    private Boolean isActive = true;
}
