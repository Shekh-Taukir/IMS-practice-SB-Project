package com.tsTech.practice.IMS_v2.visitNote.diagnosis.repository;

import com.tsTech.practice.IMS_v2.visitNote.diagnosis.dto.projection.DiagnosisIcdMapProjection;
import com.tsTech.practice.IMS_v2.visitNote.diagnosis.entity.DiagnosisIcdMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/////////////////////////////////////////////
//
// Name: Diagnosis Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 21, 2026 || TaukirS (ER 1016 - diagnosis entity coding)
// v1.2 || type : Change || Sep 10, 2026 || TaukirHp (ER 1021 - vn careplan icd map entity coding)
/////////////////////////////////////////////

@Repository
public interface DiagnosisIcdMapRepository extends JpaRepository<DiagnosisIcdMap, Long> {

    String sqlCommon = """
            SELECT
                im.code as code, im.description as description, 
                dim.seq as seq, dim.tranId as tranId, dim.diagnosis.tranId as diagnosisId, dim.icd.tranId as icdId
            FROM
                DiagnosisIcdMap dim
            """;

    String sqlForSingleDiagnosis = """
            LEFT JOIN
                ICD im
                on im.tranId = dim.icd.tranId
            WHERE
                dim.diagnosis.tranId = :diagnosisId
            ORDER BY 
                dim.seq asc
            """;

    String sqlForPatientDiagnosis = """
            INNER JOIN
                Diagnosis diag
                on diag.tranId = dim.diagnosis.tranId
                and diag.patient.tranId = :patientId
            LEFT JOIN
                ICD im
                on im.tranId = dim.icd.tranId
            ORDER BY 
                dim.seq asc
            """;

    //Start Sep 10, 2026 TaukirHp (ER 1021 - vn careplan icd map entity coding)
    String sqlForDiagnosisIcdList = """
            SELECT
                dim.icd.tranId as icdId
            FROM
                DiagnosisIcdMap dim
            INNER JOIN
                Diagnosis d
                ON d.visitNote.tranId = :pnId
                And d.tranId = dim.diagnosis.tranId
            """;
    //End Sep 10, 2026 TaukirHp (ER 1021 - vn careplan icd map entity coding)

    @Query(sqlCommon + sqlForSingleDiagnosis)
    List<DiagnosisIcdMapProjection> getDiagnosisIcdProjection(@Param("diagnosisId") Long diagnosisId);

    @Query(sqlCommon + sqlForPatientDiagnosis)
    List<DiagnosisIcdMapProjection> getDiagnosisIcdProjectionByPatientId(@Param("patientId") Long patientId);

    //Start Sep 11, 2026 TaukirHp (ER 1021 - vn careplan icd map entity coding)
    @Query(sqlForDiagnosisIcdList)
    List<Long> getDiagnosisIcdListByPnId(@Param("pnId") Long pnId);
    //End Sep 11, 2026 TaukirHp (ER 1021 - vn careplan icd map entity coding)

    List<DiagnosisIcdMap> findByDiagnosis_TranId(Long diagnosisId);


}
