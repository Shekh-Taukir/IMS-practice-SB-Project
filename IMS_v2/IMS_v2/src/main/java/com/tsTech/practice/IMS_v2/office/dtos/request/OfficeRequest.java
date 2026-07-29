package com.tsTech.practice.IMS_v2.office.dtos.request;

import com.tsTech.practice.IMS_v2.common.annotations.EntityStringValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

////////////////////////////////////////////////
//
// Name: Office Request
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

public record OfficeRequest(

        @EntityStringValidation
        String officeName,

        @EntityStringValidation
        String officeCode, 

        @NotBlank(message = "Phone no. cannot be null")
        String phoneNo,

        @NotBlank(message = "fax no. cannot be null")
        String faxNo,

        @NotBlank(message = "Address cannot be null")
        String address1,
        
        @Email
        @NotBlank(message = "email cannot be null")
        String email,

        @NotBlank(message = "zip cannot be null")
        String zipCode
) {
}
