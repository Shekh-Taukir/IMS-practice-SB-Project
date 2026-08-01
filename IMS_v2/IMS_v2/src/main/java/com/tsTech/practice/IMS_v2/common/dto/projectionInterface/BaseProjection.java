package com.tsTech.practice.IMS_v2.common.dto.projectionInterface;

import java.time.LocalDateTime;

////////////////////////////////////////////////
//
// Name: Base Projection Interface
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 30, 2026 || TaukirS (ER 1011 - flyway integration & add office and provider in patient)
////////////////////////////////////////////////

public interface BaseProjection {
    Long getTranId();
    LocalDateTime getCreatedAt();
    LocalDateTime getUpdatedAt();
    Boolean getIsActive();
}
