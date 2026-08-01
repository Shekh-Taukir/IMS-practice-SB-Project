package com.tsTech.practice.IMS_v2.common.exception;

import lombok.Getter;

/////////////////////////////////////////////
//
// Name: Business Validation Exception
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 31, 2026 || TaukirS (ER 1011 - flyway integration & add office and provider in patient)
////////////////////////////////////////////////

@Getter
public class BusinessValidationException extends RuntimeException{

    private String errorCode;

    public BusinessValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
