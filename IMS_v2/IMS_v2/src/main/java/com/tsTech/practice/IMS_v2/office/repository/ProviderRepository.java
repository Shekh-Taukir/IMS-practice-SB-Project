package com.tsTech.practice.IMS_v2.office.repository;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.office.entities.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

////////////////////////////////////////////////
//
// Name: Provider Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    default Provider getEntityById(Long providerId){
        return findById(providerId)
                .orElseThrow(()->new ResourceNotFoundException("Provider", providerId));
    }
}
