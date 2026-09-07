package com.tsTech.practice.IMS_v2.visitNote.labOrder.repository;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.entity.LabOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: LabOrder Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 06, 2026 || TaukirS (ER 1019 - lab order entity coding)
/// //////////////////////////////////////////

@Repository
public interface LabOrderRepository extends JpaRepository<LabOrder, Long> {

    List<LabOrder> findAllByOrderByTranIdDesc();

    // =========================================================================
    //  Default Methods
    // =========================================================================

    default LabOrder getEntityById(Long labOrderId){
        return findById(labOrderId)
                .orElseThrow(()-> new ResourceNotFoundException("Lab_Order", labOrderId));
    }
}
