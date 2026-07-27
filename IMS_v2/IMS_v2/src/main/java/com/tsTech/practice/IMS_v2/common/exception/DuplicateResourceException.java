package com.tsTech.practice.IMS_v2.common.exception;

import lombok.Getter;
import org.springframework.data.repository.query.Param;

/////////////////////////////////////////////
//
// Name: Duplicate Resource Exception
//
// Description:
//
// Version history:
//
// v1.1 || type : New FUnc || Jul 27, 2026 || TaukirS (ER 1009 - api_error changes for record, func and exception changes)
////////////////////////////////////////////////

@Getter
public class DuplicateResourceException extends RuntimeException {
    private final String errorCode;

    public DuplicateResourceException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
