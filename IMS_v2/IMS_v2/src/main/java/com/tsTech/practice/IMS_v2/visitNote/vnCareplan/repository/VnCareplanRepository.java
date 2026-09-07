package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.repository;

import com.tsTech.practice.IMS_v2.common.exception.BusinessValidationException;
import com.tsTech.practice.IMS_v2.common.exception.DuplicateResourceException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.visitNote.vnCareplan.entity.VnCareplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: VisitNote Careplan Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1018 - visit careplan entity coding)

/// //////////////////////////////////////////

@Repository
public interface VnCareplanRepository extends JpaRepository<VnCareplan, Long> {

    List<VnCareplan> findByVisitNote_TranIdOrderByTranIdDesc(Long pnId);

    Boolean existsByVisitNote_TranIdAndCareplan_TranId(Long pnId, Long careplanId);

    // =========================================================================
    //  Default Methods
    // =========================================================================

    default VnCareplan getEntityById(Long pnId, Long vnCareplanId) {
        VnCareplan vnCareplan = findById(vnCareplanId)
                .orElseThrow(() -> new ResourceNotFoundException("VnCareplan", vnCareplanId));

        if (!vnCareplan
                .getVisitNote()
                .getTranId()
                .equals(pnId))
            throw new BusinessValidationException("Vn Careplan of id: " + vnCareplanId + " doesn't fall under Visit Note of id: " + pnId, "VISIT_NOTE_VN_CAREPLAN_MISMATCH");

        return vnCareplan;
    }

    default boolean checkVisitAndCareplanExists(Long pnId, Long careplanId) {
        if (existsByVisitNote_TranIdAndCareplan_TranId(pnId, careplanId)) {
            throw new DuplicateResourceException("DUPLICATE_VN_CAREPLAN_FOR_VISIT", "VnCareplan already exists for pnId : " + pnId + " and for careplan of id: " + careplanId);
        }
        return false;
    }

}