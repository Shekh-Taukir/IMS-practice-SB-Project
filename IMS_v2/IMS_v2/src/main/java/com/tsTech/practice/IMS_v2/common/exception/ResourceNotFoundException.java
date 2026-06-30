package com.tsTech.practice.IMS_v2.common.exception;

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
////////////////////////////////////////////////

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message) {
        super(message);
    }

    //Start Jun 30, 2026 TaukirS (ER 1005 - patient insurance setup)
    /// FOr future,
    ///Have to add this function for throwing patient not found, instead of writing it everywhere the same line.
    /*
    public static ResourceNotFoundException forPatient(Long patientId) {
        return new ResourceNotFoundException("Patient not found for id: "+patientId);
    }
     */
    //End Jun 30, 2026 TaukirS (ER 1005 - patient insurance setup)
}
