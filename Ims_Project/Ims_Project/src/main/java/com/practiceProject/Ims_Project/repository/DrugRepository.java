package com.practiceProject.Ims_Project.repository;

import com.practiceProject.Ims_Project.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {
}