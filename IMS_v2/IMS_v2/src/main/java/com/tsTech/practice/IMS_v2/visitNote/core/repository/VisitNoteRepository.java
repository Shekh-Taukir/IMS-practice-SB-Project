package com.tsTech.practice.IMS_v2.visitNote.core.repository;

import com.tsTech.practice.IMS_v2.common.constants.SqlQueryConstants;
import com.tsTech.practice.IMS_v2.common.exception.BusinessValidationException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.visitNote.core.dto.projection.VisitNoteProjection;
import com.tsTech.practice.IMS_v2.visitNote.core.entities.VisitNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/////////////////////////////////////////////
//
// Name: VisitNote Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 20, 2026 || TaukirS (ER 1015 - visitnote entity coding)
// v1.2 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
/////////////////////////////////////////////

@Repository
public interface VisitNoteRepository extends JpaRepository<VisitNote, Long> {

    String sqlQueryP1 = """
        select 
            vn.tranId as tranId, vn.createdAt as createdAt, vn.updatedAt as updatedAt, vn.isActive as isActive,
            vn.description as description, vn.encounterDate as encounterDate, vn.procedure as procedure, vn.note as note,
            pat.tranId as patientId, """+ SqlQueryConstants.PATIENT_NAME_EXPR +
            ", prov.tranId as providerId, " + SqlQueryConstants.PROVIDER_NAME_EXPR+
            ", off.tranId as officeId, " + SqlQueryConstants.OFFICE_NAME_EXPR +
            """
            , visitType.tranId as visitTypeId, visitType.name as visitTypeName
        from 
            VisitNote vn
        INNER JOIN
            Patient pat
            on pat.tranId = vn.patient.tranId
        """;

    String sqlQueryP2 = """
        INNER JOIN
            Office off
            on off.tranId = vn.office.tranId
        INNER JOIN
            Provider prov
            on prov.tranId = vn.provider.tranId
        LEFT JOIN
            VisitType visitType
            on visitType.tranId = vn.visitType.tranId
        """;

    @Query(sqlQueryP1 + sqlQueryP2 +" WHERE vn.tranId = :pnId")
    Optional<VisitNoteProjection> findVisitNoteById(@Param("pnId") Long pnId);

    @Query(sqlQueryP1+" AND pat.tranId = :patientId "+sqlQueryP2 +" ORDER BY vn.tranId desc")
    List<VisitNoteProjection> findAllVisitNotes(@Param("patientId") Long patientId);

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    default VisitNote getEntityById(Long patientId, Long pnId) {
        /*VisitNote visitNote = findById(pnId)
                .orElseThrow(()->new ResourceNotFoundException("visit_note", pnId));

        if(!(visitNote.getPatient().getTranId().equals(patientId)))
            throw new BusinessValidationException("Visit of ID: "+pnId+" is not of patient_id: "+patientId, "PATIENT_VISIT_MISMATCH");

        return visitNote;*/
        return getGenericVisitNoteById(findById(pnId), patientId, pnId, v->v.getPatient().getTranId());
    }

    default VisitNoteProjection getVisitNoteProjById(Long patientId, Long pnId){
        /*
        VisitNoteProjection visitNoteProjection = findVisitNoteById(pnId)
                .orElseThrow(()->new ResourceNotFoundException("visit_note", pnId));

        if(!(visitNoteProjection.getPatientId().equals(patientId)))
            throw new BusinessValidationException("Visit of ID: "+pnId+" is not of patient_id: "+patientId, "PATIENT_VISIT_MISMATCH");

        return visitNoteProjection;
         */
        return getGenericVisitNoteById(findVisitNoteById(pnId), patientId, pnId, v->v.getPatientId());
    }

    //Start Aug 21, 2026 TaukirS (ER 1016 - diagnosis entity coding)
    default VisitNote getVisitNoteEntityById(Long pnId){
        return findById(pnId)
                .orElseThrow(()->visitNoteNotFound(pnId));
    }
    //End Aug 21, 2026 TaukirS (ER 1016 - diagnosis entity coding)

    default <T> T getGenericVisitNoteById(Optional<T> result, Long patientId, Long pnId, Function<T, Long> patientIdExtractor){
        T entity = result.orElseThrow(()-> visitNoteNotFound(pnId));

        if(!(patientIdExtractor.apply(entity).equals(patientId)))
            throw new BusinessValidationException("Visit of ID: "+pnId+" is not of patient_id: "+patientId, "PATIENT_VISIT_MISMATCH");

        return entity;
    }

    //Start Aug 22, 2026 TaukirS (ER 1016 - diagnosis entity coding)
    default boolean checkVisitNoteExistsById(Long pnId){
        if(!existsById(pnId))
            throw visitNoteNotFound(pnId);

        return true;
    }

    private ResourceNotFoundException visitNoteNotFound(Long pnId){
        return new ResourceNotFoundException("visit_note", pnId);
    }
    //End Aug 22, 2026 TaukirS (ER 1016 - diagnosis entity coding)
}
