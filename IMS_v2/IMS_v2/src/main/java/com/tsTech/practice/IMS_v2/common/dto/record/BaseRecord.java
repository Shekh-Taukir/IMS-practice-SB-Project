package com.tsTech.practice.IMS_v2.common.dto.record;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/////////////////////////////////////////////
//
// Name: Base Record
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 24, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
////////////////////////////////////////////////

public record BaseRecord(

        // TODO:    1. Need to createdBy and updatedBY fields with default value for now.
        //          2. Once the spring security is implemented, then need to updated createdBy and updatedBy fields with actual login user with default value,

        Long tranId,
        
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")    //Jul 25, 2026 TaukirS (ER 1007 - logging and dto to record changes) - updated the date time format
        LocalDateTime createdAt,
        
        @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")    //Jul 25, 2026 TaukirS (ER 1007 - logging and dto to record changes) - updated the date time
        LocalDateTime updatedAt,

        @JsonProperty("isActive")
        @NotNull(message = "isActive field cannot be null")
        Boolean isActive
) {
    //Jul 24, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    // NOTE:    In base DTO, we use isActive = true, so don't need to set it true for every add api,
    //          so to achieve that, we use compact constructor method to set it true when null, which acts like that flow only.

    /* //But now Base record will be used only in Response Records for now, so for now no need of this logic, as we are not using this in request records
    public BaseRecord {
        if(isActive==null)
            isActive=true;
    }*/
}
