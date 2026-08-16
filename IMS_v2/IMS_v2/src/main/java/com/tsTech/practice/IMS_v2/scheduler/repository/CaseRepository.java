package com.tsTech.practice.IMS_v2.scheduler.repository;

import com.tsTech.practice.IMS_v2.common.exception.BusinessValidationException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.scheduler.dto.projection.CaseProjection;
import com.tsTech.practice.IMS_v2.scheduler.entities.Case;
import io.micrometer.observation.aop.ObservationKeyValueAnnotationHandler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/////////////////////////////////////////////
//
// Name: Case Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 16, 2026 || TaukirS (ER 1013 - case master coding)
/////////////////////////////////////////////

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {

    String sqlQueryP1 ="""
            SELECT 
                c.name as name, c.description as description, c.note as note, 
                p.tranId as patientId, o.tranId as officeId, 
                p.lastName ||', '|| p.firstName as patientName, o.officeName ||' ('|| o.officeCode ||')' as officeName, +
                c.tranId as tranId, c.createdAt as createdAt, c.updatedAt as updatedAt, c.isActive as isActive 
            FROM 
                Case c 
            INNER JOIN 
                Patient p 
                ON p.tranId = c.patient.tranId
            """;

    String sqlQueryP2 ="""
            INNER JOIN 
                Office o 
                ON o.tranId = c.office.tranId
            """;

    @Query(sqlQueryP1 +" and p.tranId = :patientId "+ sqlQueryP2 + " ORDER BY c.tranId desc ")
    List<CaseProjection> findAllWithPatientAndOffice(@Param("patientId") Long patientId);

    @Query(sqlQueryP1 + sqlQueryP2 +" WHERE c.tranId = :caseId ")
    Optional<CaseProjection> findCaseById(@Param("caseId") Long caseId);

    // =========================================================================
    //  Default Methods
    // =========================================================================

    default CaseProjection getCaseByIdAndPatientId(Long caseId, Long patientId){
        /*CaseProjection caseProjection = findCaseById(caseId)
                .orElseThrow(()-> new ResourceNotFoundException("patient_case", caseId));

        if(!caseProjection.getPatientId().equals(patientId))
            throw new BusinessValidationException("Case of id: "+caseId+" is not for patient: "+patientId, "PATIENT_CASE_MISMATCH");
        return caseProjection;*/
        return getGenericCaseByIdOrThrow(
                findCaseById(caseId),
                patientId,
                caseId,
                CaseProjection::getPatientId
        );
    }

    default Case getCaseEntityByIdAndPatientId(Long caseId, Long patientId){
        /*Case case1 = findById(caseId)
                .orElseThrow(()-> new ResourceNotFoundException("patient_case", caseId));

        if(!case1.getPatient().getTranId().equals(patientId))
            throw new BusinessValidationException("Case of id: "+caseId+" is not for patient: "+patientId, "PATIENT_CASE_MISMATCH");

        return case1;*/
        return getGenericCaseByIdOrThrow(
                findById(caseId),
                patientId,
                caseId,
                c->c.getPatient().getTranId()
        );
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================


    default <T> T getGenericCaseByIdOrThrow(Optional<T> result, Long patientId, Long caseId, Function<T, Long> patientIdExtractor){
        T  entity = result.orElseThrow(()-> new ResourceNotFoundException("patient_case", caseId));
        if(!patientIdExtractor.apply(entity).equals(patientId))
            throw new BusinessValidationException("Case of id: "+caseId+" is not for patient: "+patientId, "PATIENT_CASE_MISMATCH");

        return entity;
    }
}
