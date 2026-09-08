package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.projection;

/// //////////////////////////////////////////
//
// Name: VisitNote Careplan Icd Map Projection
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 08, 2026 || TaukirS (ER 1021 - vn careplan icd map entity coding)

/// //////////////////////////////////////////

public interface VnCareplanIcdMapProjection {
    String getCode();

    Long getSeq();

    Long getIcdId();

    Long getTranId();

    Long getVnCareplanId();
}
