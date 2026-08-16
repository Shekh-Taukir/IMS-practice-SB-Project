package com.tsTech.practice.IMS_v2.scheduler.dto.request;

import com.tsTech.practice.IMS_v2.common.annotations.EntityStringValidation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/////////////////////////////////////////////
//
// Name: Case Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 16, 2026 || TaukirS (ER 1013 - case master coding)
/////////////////////////////////////////////

public record CaseRequest(

        @EntityStringValidation(max = 100)
        String name,

        @NotBlank(message = "Description cannot be blank")
        @Size(max = 1000, message = "Description cannot be more than 1000 chars")
        String description,

//        @NotNull(message = "Office cannot be null")
//        Long officeId,  //TODO: remove officeId from request, because as patient is fixed for case, then so do office.
        String note
) {
}
