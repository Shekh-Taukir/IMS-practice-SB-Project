package com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.projection;

import com.tsTech.practice.IMS_v2.common.dto.projectionInterface.BaseProjection;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: Diagnosis Projection
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

public interface DiagnosisProjection extends BaseProjection {
    Long getPnId();
    String getVnDescription();
    Long getPatientId();
    String getPatientName();
    LocalDate getTakenAt();
    String getNote();
}
