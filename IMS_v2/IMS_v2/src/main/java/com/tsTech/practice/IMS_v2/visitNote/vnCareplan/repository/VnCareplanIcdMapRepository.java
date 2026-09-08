package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.repository;

import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.entity.VnCareplanIcdMap;
import org.springframework.data.jpa.repository.JpaRepository;
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

/// //////////////////////////////////////////

@Repository
public interface VnCareplanIcdMapRepository extends JpaRepository<VnCareplanIcdMap, Long> {

    List<VnCareplanIcdMap> findByVnCareplan_TranId(Long vnCareplanId);

    Boolean existsByVnCareplan_TranId(Long vnCareplanId);
}