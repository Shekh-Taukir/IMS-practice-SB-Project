package com.tsTech.practice.IMS_v2.visitNote.dto.response;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.tsTech.practice.IMS_v2.common.dto.record.BaseRecord;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: Visit Note Request Record
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 20, 2026 || TaukirS (ER 1015 - visit note entity coding)
/////////////////////////////////////////////

public record VisitNoteResponse(
        String description,
        Boolean isBillable,

        Long patientId,
        String patientName,

        Long officeId,
        String officeName,

        Long providerId,
        String providerName,

        Long visitTypeId,
        String visitTypeName,

        LocalDate encounterDate,
        String procedure,
        String note,
        @JsonUnwrapped BaseRecord baseRecord
) {
}
