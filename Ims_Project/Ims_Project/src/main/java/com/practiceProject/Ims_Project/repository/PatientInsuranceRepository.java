package com.practiceProject.Ims_Project.repository;

import com.practiceProject.Ims_Project.entity.PatientInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientInsuranceRepository extends JpaRepository<PatientInsurance,Long> {
}
