package com.tsTech.practice.IMS_v2.common.exception;

/// //////////////////////////////////////////
//
// Name: Invalid Patch Request Exception
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1018 - visit careplan entity coding)

import java.util.Map;

/// //////////////////////////////////////////

public class InvalidPatchRequestException extends RuntimeException {
    private final Map<String, String> violations;

    public InvalidPatchRequestException(Map<String, String> violations) {
        super("Input Validations failed for patch request");
        this.violations = violations;
    }

    public Map<String, String> getViolations() {
        return violations;
    }
}
