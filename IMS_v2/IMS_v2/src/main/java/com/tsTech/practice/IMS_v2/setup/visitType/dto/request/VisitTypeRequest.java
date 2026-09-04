package com.tsTech.practice.IMS_v2.setup.visitType.dto.request;

import com.tsTech.practice.IMS_v2.common.annotations.EntityStringValidation;

/// //////////////////////////////////////////
//
// Name: Visit Type Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 15, 2026 || TaukirS (ER 1012 - visit type entity code)

/////////////////////////////////////////////

public record VisitTypeRequest(

        @EntityStringValidation(max = 50)
        String name,
        String note
) {
}
