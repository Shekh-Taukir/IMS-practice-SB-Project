package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.repository;

import com.tsTech.practice.IMS_v2.common.exception.BusinessValidationException;
import com.tsTech.practice.IMS_v2.common.exception.DuplicateResourceException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity.VnLabOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: VisitNote LabRepository
//
// Description:
//
// Version history:
//
// v1.1 || type : New Func || Sep 08, 2026 || TaukirHp (ER 1020 - vn lab order entity coding)
// v1.2 || type : Change || Sep 11, 2026 || TaukirS (ER 1020 - vn lab order entity coding)
// v1.3 || type : Change || Sep 12, 2026 || TaukirS (ER 1022 - vn lab order icd map entity coding)

/// //////////////////////////////////////////

@Repository
public interface VnLabOrderRepository extends JpaRepository<VnLabOrder, Long> {

    Boolean existsByVisitNote_TranIdAndLabOrder_TranId(Long pnId, Long labOrderId);

    List<VnLabOrder> findByVisitNote_TranIdOrderByTranIdDesc(Long pnId);

    // =========================================================================
    //  Default Methods
    // =========================================================================

    default void checkLabOrderAndVisitExists(Long pnId, Long labOrderId) {
        if (existsByVisitNote_TranIdAndLabOrder_TranId(pnId, labOrderId))
            throw new DuplicateResourceException("DUPLICATE_VN_LABORDER_FOR_VISIT", "VnLabOrder already exists for pnId: " + pnId + " and for labOrder of id: " + labOrderId);
    }

    default VnLabOrder getEntityById(Long pnId, Long vnLabOrderId) {
        //Sep 12, 2026 TaukirS (ER 1022 - vn lab order icd map entity coding)
        VnLabOrder vnLabOrder = getEntityById(vnLabOrderId);

        if (!vnLabOrder.getVisitNote().getTranId().equals(pnId))
            throw new BusinessValidationException("Vn LabOrder of id: " + vnLabOrderId + " doesn't fall under Visit Note of id: " + pnId, "VISIT_NOTE_VN_LABORDER_MISMATCH");

        return vnLabOrder;
    }

    //Start Sep 12, 2026 TaukirS (ER 1022 - vn lab order icd map entity coding)
    default VnLabOrder getEntityById(Long vnLabOrderId) {
        return findById(vnLabOrderId)
                .orElseThrow(() -> new ResourceNotFoundException("VnLabOrder", vnLabOrderId));
    }
    //End Sep 12, 2026 TaukirS (ER 1022 - vn lab order icd map entity coding)
}
