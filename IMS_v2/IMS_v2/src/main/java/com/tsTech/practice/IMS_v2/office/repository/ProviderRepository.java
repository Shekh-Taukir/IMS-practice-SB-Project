package com.tsTech.practice.IMS_v2.office.repository;

import com.tsTech.practice.IMS_v2.common.exception.BusinessValidationException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.office.entities.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

////////////////////////////////////////////////
//
// Name: Provider Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
// v1.2 || type : Change || Jul 30, 2026 || TaukirS (ER 1011 - flyway integration & add office and provider in patient)
////////////////////////////////////////////////

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Long> {

    //function is used in provider CRUD Process
    default Provider getEntityById(Long providerId){
        return findById(providerId)
                .orElseThrow(()->new ResourceNotFoundException("Provider", providerId));
    }

    //Start Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
    default Provider getEntityByIdAndOffice(Long providerId, Long officeId){
        /* NOTE:
              This functions does both work:
              1. get the full entity, and throw resource not found if not there
              2. check with office as well, that does this provider falls under this office.
        */

        Provider provider = getEntityById(providerId);

        if(!provider.getOffice().getTranId().equals(officeId)){
            throw new BusinessValidationException(
                    "Current provider does not belongs to the new office. Please provide a valid provider for that office.",
                    "PROVIDER_OFFICE_MISMATCH"
            );
        }
        return provider;
    }

    default boolean checkProviderFallsUnderOffice(Long providerId, Long newOfficeId){
        Long officeId = findOfficeIdByProviderId(providerId)
                .orElseThrow(()->new ResourceNotFoundException("Provider", providerId));

        if (!officeId.equals(newOfficeId))
            throw new BusinessValidationException(
                    "Current provider does not belongs to the new office. Please provide a valid provider for that office.",
                    "PROVIDER_OFFICE_MISMATCH"
            );

        return true;
    }

    // =========================================================================
    //  Internal Helper Methods
    // =========================================================================

    boolean existsByTranIdAndOffice_TranId(Long providerId, Long officeId);

    @Query("SELECT prov.office.tranId from Provider prov WHERE prov.tranId = :providerId ")
    Optional<Long> findOfficeIdByProviderId(@Param("providerId") Long providerId);
    //End Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
}
