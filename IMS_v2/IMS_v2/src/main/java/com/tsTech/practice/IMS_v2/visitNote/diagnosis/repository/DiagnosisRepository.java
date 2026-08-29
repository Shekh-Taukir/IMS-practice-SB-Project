package com.tsTech.practice.IMS_v2.visitNote.diagnosis.repository;

import com.tsTech.practice.IMS_v2.common.constants.SqlQueryConstants;
import com.tsTech.practice.IMS_v2.common.exception.BusinessValidationException;
import com.tsTech.practice.IMS_v2.common.exception.DuplicateResourceException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.projection.DiagnosisProjection;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.entity.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/////////////////////////////////////////////
//
// Name: Diagnosis Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {

    String sqlQuery = """
            SELECT
                diag.tranId as tranId, diag.createdAt as createdAt, diag.updatedAt as updatedAt, diag.isActive as isActive,
                vn.description as vnDescription,"""+ SqlQueryConstants.PATIENT_NAME_EXPR +"""
                , diag.visitNote.tranId as pnId, diag.patient.tranId as patientId, diag.takenAt as takenAt, diag.note as note
            FROM
                Diagnosis diag
            INNER JOIN
                VisitNote vn
                on vn.tranId = diag.visitNote.tranId
            INNER JOIN
                 Patient pat
                 on pat.tranId = diag.patient.tranId
            """;

    boolean existsByVisitNote_TranId(Long pnId);

    @Query(sqlQuery+" WHERE diag.tranId = :diagnosisId")
    Optional<DiagnosisProjection> getDiagnosisProjectionById(@Param("diagnosisId") Long diagnosisId);

    @Query(sqlQuery+" AND pat.tranId = :patientId ")
    List<DiagnosisProjection> getDiagnosisProjectionListByPatientId(@Param("patientId") Long patientId);


    // =========================================================================
    //  Default Methods
    // =========================================================================

    default boolean checkDiagnosisExistsByPnId(Long pnId){
        if(existsByVisitNote_TranId(pnId))
            throw new DuplicateResourceException("DUPLICATE_DIAGNOSIS_FOR_VISIT", "Diagnosis already exists for visit ID: "+pnId);
        return false;
    }

    default DiagnosisProjection getDiagnosisProjOrThrow(Long pnId, Long diagnosisId){
        DiagnosisProjection diagnosisProjection = getDiagnosisProjectionById(diagnosisId)
                .orElseThrow(()->new ResourceNotFoundException("Diagnosis", diagnosisId));
        checkDiagnosisExistsForVisit(diagnosisId, diagnosisProjection.getPnId(), pnId);
        return diagnosisProjection;
    }

    default Diagnosis getEntityById(Long pnId, Long diagnosisId) {
        Diagnosis diagnosis = findById(diagnosisId)
                .orElseThrow(()->diagnosisNotFound(diagnosisId));
        checkDiagnosisExistsForVisit(diagnosisId, diagnosis.getVisitNote().getTranId(), pnId);
        return diagnosis;
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    private static ResourceNotFoundException diagnosisNotFound(Long diagnosisId){
        return new ResourceNotFoundException("Diagnosis", diagnosisId);
    }

    private static void checkDiagnosisExistsForVisit (Long diagnosisId, Long diagnosisPnId, Long pnId){
        if(!(diagnosisPnId.equals(pnId)))
            throw new BusinessValidationException("Diagnosis of id: "+diagnosisId+" is not for visit of id: "+pnId, "VISIT_DIAGNOSIS_MISMATCH");

    }
}
