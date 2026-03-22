package com.practiceProject.Ims_Project.repository;

import com.practiceProject.Ims_Project.dto.PatientBloodGroupCountDto;
import com.practiceProject.Ims_Project.entity.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);

    @Query("select new com.practiceProject.Ims_Project.dto.PatientBloodGroupCountDto(p.bloodGroup, count(1) as patientCount) from Patient p group by p.bloodGroup order by patientCount desc")
    List<PatientBloodGroupCountDto> getBloodGroupStatsList();

    @Modifying
    @Transactional
    @Query("update Patient p set p.lastName = :lastName where tranId = :tranId")
    int updateLastNameById(@Param("lastName") String lastName, @Param("tranId") Long tranId);
}
