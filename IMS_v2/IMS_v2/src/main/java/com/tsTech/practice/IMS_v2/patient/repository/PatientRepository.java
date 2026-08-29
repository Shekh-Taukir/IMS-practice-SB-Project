package com.tsTech.practice.IMS_v2.patient.repository;

import com.tsTech.practice.IMS_v2.common.constants.SqlQueryConstants;
import com.tsTech.practice.IMS_v2.common.exception.BusinessValidationException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.patient.dto.projectionInterface.PatientProjection;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

////////////////////////////////////////////////
//
// Name: Patient Mst Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
// v1.2 || type : Change || Jun 29, 2026 || TaukirS (ER 1005 - patient insurance setup)
// v1.3 || type : Change || Jul 30, 2026 || TaukirS (ER 1011 - flyway integration & add office and provider in patient)
// v1.4 || type : Change || Aug 16, 2026 || TaukirS (ER 1013 - case master coding)
// v1.5 || type : Change || Aug 21, 2026 || TaukirS (ER 1015 - visitnote entity coding)
// v1.6 || type : Change || Aug 23, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
////////////////////////////////////////////////

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    //Aug 21, 2026 TaukirS (ER 1015 - visitnote entity coding) - added query constants for office and provider name expression
    String sqlQuery ="""
            SELECT 
                p.firstName AS firstName, p.lastName AS lastName, p.middleName AS middleName, p.aka AS aka, p.address1 as address1, 
                p.address2 as address2, p.birthDate as birthDate, p.deceasedDate as deceasedDate, p.email as email, p.prefix as prefix, 
                p.sex as sex, p.note as note, p.sexualOrientation as sexualOrientation, +
                p.tranId as tranId, p.createdAt as createdAt, p.updatedAt as updatedAt, p.isActive as isActive, """ +
                SqlQueryConstants.OFFICE_NAME_EXPR +", "+SqlQueryConstants.PROVIDER_NAME_EXPR+"""
                , off.tranId as officeId, prov.tranId as providerId 
            FROM 
                Patient p 
            INNER JOIN 
                Office off
                ON off.tranId = p.office.tranId 
            INNER JOIN 
                Provider prov 
                ON prov.tranId = p.provider.tranId """;

    //Start Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
    @Query( sqlQuery )
    List<PatientProjection> findAllWithOfficeAndProvider();

    @Query( sqlQuery + " where p.tranId = :patientId " )
    Optional<PatientProjection> findByIdWithOfficeAndProvider(@Param("patientId") Long patientId);
    //End Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)

    // =========================================================================
    //  Default Methods
    // =========================================================================

    //Start Jul 01, 2026 TaukirS (ER 1005 - patient insurance setup)
    default Patient getPatientEntityById(Long patientId){
        //Aug 16, 2026 TaukirS (ER 1013 - case master coding) make the exception throwing logic common into function
        return unwrapOrThrow(findById(patientId), patientId);
    }
    //End Jul 01, 2026 TaukirS (ER 1005 - patient insurance setup)

    default PatientProjection getPatientWithOfficeAndProviderEntityById(Long patientId){
        //Aug 16, 2026 TaukirS (ER 1013 - case master coding) make the exception throwing logic common into function
        return unwrapOrThrow(findByIdWithOfficeAndProvider(patientId), patientId);
    }

    //Start Aug 16, 2026 TaukirS (ER 1013 - case master coding)
    /*default Patient getPatientEntityByIdAndOfficeId(Long patientId, Long officeId){
        Patient patient = getPatientEntityById(patientId);
        if(!patient.getOffice().getTranId().equals(officeId))
            throw new BusinessValidationException("Patient of id: "+patientId+" doesn't fall under the office of id: "+officeId, "PATIENT_OFFICE_MISMATCH");

        return patient;
    }*/

    default void patientExistsById(Long patientId){
        //Aug 23, 2026 TaukirS (ER 1016 - diagnosis entity coding) - made patient not found a common logic to throw ResourceNotFoundException for patient
        if(!existsById(patientId))
            throw patientNotFound(patientId);
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    private static <T> T unwrapOrThrow(Optional<T> result, Long patientId){
        //Aug 23, 2026 TaukirS (ER 1016 - diagnosis entity coding) - made patient not found a common logic to throw ResourceNotFoundException for patient
        return result.orElseThrow(()-> patientNotFound(patientId));
    }
    //End Aug 16, 2026 TaukirS (ER 1013 - case master coding)

    //Start Aug 23, 2026 TaukirS (ER 1016 - diagnosis entity coding)
    private static ResourceNotFoundException patientNotFound(Long patientId){
        return new ResourceNotFoundException("Patient", patientId);
    }
    //End Aug 23, 2026 TaukirS (ER 1016 - diagnosis entity coding)
}
