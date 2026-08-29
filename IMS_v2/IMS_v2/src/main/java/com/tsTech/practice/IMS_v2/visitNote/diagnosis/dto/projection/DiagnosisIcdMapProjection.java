package com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.projection;

/////////////////////////////////////////////
//
// Name: Diagnosis Icd Map Projection
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

public interface DiagnosisIcdMapProjection {
    String getCode();
    String getDescription();
    Long getSeq();
    Long getIcdId();
    Long getTranId();
    Long getDiagnosisId();
}
