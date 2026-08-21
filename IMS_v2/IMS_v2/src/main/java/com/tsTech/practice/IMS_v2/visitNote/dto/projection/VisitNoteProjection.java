package com.tsTech.practice.IMS_v2.visitNote.dto.projection;

import com.tsTech.practice.IMS_v2.common.dto.projectionInterface.BaseProjection;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: Visit Note Proejection
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 20, 2026 || TaukirS (ER 1015 - visit note entity coding)
/////////////////////////////////////////////

public interface VisitNoteProjection extends BaseProjection {
    String getDescription();
    Long getPatientId();
    String getPatientName();
    Long getOfficeId();
    String getOfficeName();
    Long getProviderId();
    String getProviderName();
    Boolean getIsBillable();
    Long getVisitTypeId();
    String getVisitTypeName();
    LocalDate getEncounterDate();
    String getProcedure();
    String getNote();
}
