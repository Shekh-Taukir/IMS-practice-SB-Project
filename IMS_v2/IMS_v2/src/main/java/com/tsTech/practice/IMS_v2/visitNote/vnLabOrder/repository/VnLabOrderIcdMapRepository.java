package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.repository;

import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.dto.projection.VnLabOrderIcdMapProjection;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity.VnLabOrderIcdMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Vn Lab Order Icd Map Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 11, 2026 || TaukirS (ER 1022 - vn lab order icd map entity coding)

/// //////////////////////////////////////////

@Repository
public interface VnLabOrderIcdMapRepository extends JpaRepository<VnLabOrderIcdMap, Long> {

    String sqlForVnLabOrderIcdProjection = """
            SELECT
                im.code as code, im.description as description, 
                vlim.seq as seq, vlim.tranId as tranId, vlim.vnLabOrder.tranId as vnLabOrderId, vlim.icd.tranId as icdId
            FROM
                VnLabOrderIcdMap vlim
            LEFT JOIN
                ICD im
                on im.tranId = vlim.icd.tranId
            WHERE
                vlim.vnLabOrder.tranId = :vnLabOrderId
            ORDER BY 
                vlim.seq asc
            """;

    @Query(sqlForVnLabOrderIcdProjection)
    List<VnLabOrderIcdMapProjection> getVnLabOrderIcdProjection(Long vnLabOrderId);

    boolean existsByVnLabOrder_TranId(Long vnLabOrderId);

    List<VnLabOrderIcdMap> findByVnLabOrder_TranId(Long vnLabOrderId);
}