package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.repository;

import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity.VnLabOrderIcdMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
    
}