package com.tsTech.practice.IMS_v2.visitNote.core.dto.request;

import com.tsTech.practice.IMS_v2.common.annotations.EntityStringValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

/////////////////////////////////////////////
//
// Name: VisitNote Request Record
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 20, 2026 || TaukirS (ER 1015 - visitnote entity coding)
/////////////////////////////////////////////

public record VisitNoteRequest(
        @EntityStringValidation(max = 100)
        String description,

        @NotNull(message = "Patient id cannot be null")
        Long patientId,

        @NotNull(message = "Provider id cannot be null")
        Long providerId,
        Boolean isBillable,

        @NotNull(message = "Visit Type cannot be null")
        Long visitTypeId,

        @NotNull(message = "Encounter Date cannot be null")
        @PastOrPresent(message = "Encounter date cannot be future date")
        LocalDate encounterDate,

        @Length(message = "Procedure cannot be more than 200 chars", max = 200)
        String procedure,

        @Length(message = "Note cannot be more than 1000 chars", max = 1000)
        String note
) {
}
