package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.repository;

import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.dto.projection.VnCareplanIcdMapProjection;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.entity.VnCareplanIcdMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Vn Careplan Icd Map Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 08, 2026 || TaukirS (ER 1021 - vn careplan icd map entity coding)
// v1.2 || type : Change || Sep 12, 2026 || TaukirS (ER 1022 - vn lab order icd map entity coding)

/// //////////////////////////////////////////

@Repository
public interface VnCareplanIcdMapRepository extends JpaRepository<VnCareplanIcdMap, Long> {

    //Sep 12, 2026 TaukirS (ER 1022 - vn lab order icd map entity coding)- converted 2 variables into single variable
    String sqlForVnCareplanIcdProjection = """
            SELECT
                im.code as code, im.description as description, 
                vcim.seq as seq, vcim.tranId as tranId, vcim.vnCareplan.tranId as vnCareplanId, vcim.icd.tranId as icdId
            FROM
                VnCareplanIcdMap vcim
            LEFT JOIN
                ICD im
                on im.tranId = vcim.icd.tranId
            WHERE
                vcim.vnCareplan.tranId = :vnCareplanId
            ORDER BY 
                vcim.seq asc
            """;

    @Query(sqlForVnCareplanIcdProjection)
    List<VnCareplanIcdMapProjection> getVnCareplanIcdProjection(@Param("vnCareplanId") Long vnCareplanId);

    List<VnCareplanIcdMap> findByVnCareplan_TranId(Long vnCareplanId);

    Boolean existsByVnCareplan_TranId(Long vnCareplanId);
}