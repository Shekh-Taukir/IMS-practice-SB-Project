package com.practiceProject.Ims_Project.repository;

import com.practiceProject.Ims_Project.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficeRepository extends JpaRepository<Office, Long> {
}