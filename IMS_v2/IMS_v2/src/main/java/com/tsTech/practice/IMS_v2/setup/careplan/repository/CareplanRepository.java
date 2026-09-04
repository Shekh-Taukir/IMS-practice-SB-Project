package com.tsTech.practice.IMS_v2.setup.careplan.repository;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.setup.careplan.entity.Careplan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareplanRepository extends JpaRepository<Careplan, Long> {

    List<Careplan> findAllByOrderByTranIdDesc();

    // =========================================================================
    //  Default Methods
    // =========================================================================

    default Careplan getEntityById(Long careplanId) {
        return findById(careplanId)
                .orElseThrow(() -> new ResourceNotFoundException("Careplan", careplanId));
    }

}