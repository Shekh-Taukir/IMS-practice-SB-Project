package com.practiceProject.Ims_Project.repository;

import com.practiceProject.Ims_Project.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    List<Prescription> findByPatient_TranId(Long patientId);
}