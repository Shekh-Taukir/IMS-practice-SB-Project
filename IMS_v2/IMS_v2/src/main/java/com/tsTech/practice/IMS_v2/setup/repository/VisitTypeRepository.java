package com.tsTech.practice.IMS_v2.setup.repository;

import com.tsTech.practice.IMS_v2.common.exception.DuplicateResourceException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.setup.entities.VisitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/////////////////////////////////////////////
//
// Name: Visit Type Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 15, 2026 || TaukirS (ER 1012 - visit type entity code)
/////////////////////////////////////////////

@Repository
public interface VisitTypeRepository extends JpaRepository<VisitType, Long> {

    Boolean existsByNameIgnoreCase(String name);

    default VisitType getEntityById(Long visitTypeId){
        return findById(visitTypeId)
                .orElseThrow(()->new ResourceNotFoundException("visit_type", visitTypeId));
    }

    default void entityExistsByName(String name){
        if(existsByNameIgnoreCase(name))
            throw new DuplicateResourceException("DUPLICATE_VISIT_TYPE", "Visit Type with name: "+name+" already exists!!");
    }
}
