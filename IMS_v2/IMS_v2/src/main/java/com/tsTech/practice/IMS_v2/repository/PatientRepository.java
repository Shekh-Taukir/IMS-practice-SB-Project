package com.tsTech.practice.IMS_v2.repository;

import com.tsTech.practice.IMS_v2.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

////////////////////////////////////////////////
//
// Name: Patient Mst Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
////////////////////////////////////////////////

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
