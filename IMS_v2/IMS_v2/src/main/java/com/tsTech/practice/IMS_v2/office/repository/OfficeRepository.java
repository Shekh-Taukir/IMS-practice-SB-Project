package com.tsTech.practice.IMS_v2.office.repository;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.office.entities.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

////////////////////////////////////////////////
//
// Name: Office Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {

    default Office getEntityById(Long officeId){
        return findById(officeId)
                .orElseThrow(()->new ResourceNotFoundException("Office", officeId));
    }
}
