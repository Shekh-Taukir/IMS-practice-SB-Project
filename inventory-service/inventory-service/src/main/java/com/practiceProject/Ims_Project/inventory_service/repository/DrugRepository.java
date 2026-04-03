package com.practiceProject.Ims_Project.inventory_service.repository;

import com.practiceProject.Ims_Project.inventory_service.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrugRepository extends JpaRepository<Drug, Long> {

    boolean existsByTranIdAndStockGreaterThan(Long drugId, Double stockRequired);

    Optional<Drug> findByTranIdAndStockGreaterThan(Long drugId, Double stockRequired);
}