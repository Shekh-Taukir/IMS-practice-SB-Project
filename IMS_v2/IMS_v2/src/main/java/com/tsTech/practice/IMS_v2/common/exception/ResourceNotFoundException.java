package com.tsTech.practice.IMS_v2.common.exception;

import lombok.Getter;

/////////////////////////////////////////////
//
// Name: Resource Not Found Exception
//
// Description:
//
// Version history:
//
// v1.1 || type : New FUnc || Jun 24, 2026 || TaukirS (ER 1003 - validation and generalize response and error coding)
// v1.2 || type : Change || Jun 30, 2026 || TaukirS (ER 1005 - patient insurance setup)
// v1.3 || type : Change || Jul 27, 2026 || TaukirS (ER 1009 - api_error changes for record, func and exception changes)
// v1.4 || type : Change || Aug 21, 2026 || TaukirS (ER 1015 - visitnote entity coding)
////////////////////////////////////////////////

@Getter
public class ResourceNotFoundException extends RuntimeException{

    private final String resource;
    private final Object identifier;

    public ResourceNotFoundException(String resource, Object identifier) {
        //Aug 21, 2026 TaukirS (ER 1015 - visitnote entity coding) , updated the message
        super(resource+" not found for id : "+identifier);
        this.resource = resource;
        this.identifier = identifier;
    }

    //Start Jun 30, 2026 TaukirS (ER 1005 - patient insurance setup)
    //NOTE: FOr future, Have to add this function for throwing patient not found, instead of writing it everywhere the same line.
    /*
    public static ResourceNotFoundException forPatient(Long patientId) {
        return new ResourceNotFoundException("Patient not found for id: "+patientId);
    }
     */
    //End Jun 30, 2026 TaukirS (ER 1005 - patient insurance setup)
}
