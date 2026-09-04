package com.tsTech.practice.IMS_v2.setup.icd.repository;

import com.tsTech.practice.IMS_v2.common.exception.DuplicateResourceException;
import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.setup.icd.entities.ICD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/// //////////////////////////////////////////
//
// Name: Icd Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 17, 2026 || TaukirS (ER 1014 - icd entity setup coding)

/// //////////////////////////////////////////

@Repository
public interface IcdRepository extends JpaRepository<ICD, Long> {

    boolean existsByCode(String code);

    List<ICD> findTop20ByOrderByCodeAsc();

    List<ICD> findTop20ByCodeContainingOrDescriptionContainingIgnoreCaseOrderByCodeAsc(String code, String description);

    List<ICD> findByCodeContainingOrDescriptionContainingIgnoreCase(String code, String description);

    // =========================================================================
    //  Default Methods
    // =========================================================================

    default void checkIcdCodeExistsOrThrow(String code) {
        code = code
                .trim()
                .toUpperCase();
        if (existsByCode(code))
            throw new DuplicateResourceException("DUPLICATE_ICD", "ICD of code: " + code + " already exists");
    }

    default ICD getEntityById(Long icdId) {
        return findById(icdId)
                .orElseThrow(() -> new ResourceNotFoundException("ICD", icdId));
    }
}
