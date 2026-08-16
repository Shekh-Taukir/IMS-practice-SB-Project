package com.tsTech.practice.IMS_v2.scheduler.dto.projection;

import com.tsTech.practice.IMS_v2.common.dto.projectionInterface.BaseProjection;

public interface CaseProjection extends BaseProjection {
    String getName();
    String getDescription();
    Long getPatientId();
    Long getOfficeId();
    String getPatientName();
    String getOfficeName();
    String getNote();
}
