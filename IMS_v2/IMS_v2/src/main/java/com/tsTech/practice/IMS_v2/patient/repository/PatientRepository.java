package com.tsTech.practice.IMS_v2.patient.repository;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import jakarta.validation.OverridesAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

////////////////////////////////////////////////
//
// Name: Patient Mst Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
// v1.2 || type : Change || Jun 29, 2026 || TaukirS (ER 1005 - patient insurance setup)
////////////////////////////////////////////////

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    //Start Jul 01, 2026 TaukirS (ER 1005 - patient insurance setup)
    default Patient getPatientEntityById(Long tranId){
        return findById(tranId)
                .orElseThrow(()->new ResourceNotFoundException("Patient not found for id: "+tranId));
    }
    //End Jul 01, 2026 TaukirS (ER 1005 - patient insurance setup)
}
