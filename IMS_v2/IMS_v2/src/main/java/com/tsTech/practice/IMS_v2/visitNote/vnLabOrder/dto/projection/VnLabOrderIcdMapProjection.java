package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.projection;

/// //////////////////////////////////////////
//
// Name: Vn Lab Order Icd Map Projection
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 11, 2026 || TaukirS (ER 1022 - vn lab order icd map entity coding)

/// //////////////////////////////////////////

public interface VnLabOrderIcdMapProjection {
    String getCode();

    String getDescription();

    Long getSeq();

    Long getIcdId();

    Long getTranId();

    Long getVnLabOrderId();
}
