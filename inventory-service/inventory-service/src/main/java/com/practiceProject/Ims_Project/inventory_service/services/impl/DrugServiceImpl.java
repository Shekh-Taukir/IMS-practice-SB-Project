package com.practiceProject.Ims_Project.inventory_service.services.impl;

import com.practiceProject.Ims_Project.inventory_service.dto.DrugDto;
import com.practiceProject.Ims_Project.inventory_service.dto.DrugStockRequestDto;
import com.practiceProject.Ims_Project.inventory_service.entity.Drug;
import com.practiceProject.Ims_Project.inventory_service.repository.DrugRepository;
import com.practiceProject.Ims_Project.inventory_service.services.DrugService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

////////////////////////////////////////////////
//
 // Name:
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Apr 03, 2026 || TaukirS (ER 1110 - Drug entity implementation in inventory_service)
////////////////////////////////////////////////

@RequiredArgsConstructor
@Service
@Slf4j
public class DrugServiceImpl implements DrugService {

    private final DrugRepository drugRepository;

    @Override
    @Transactional
    public Boolean checkDrugAndStock(DrugStockRequestDto drugStockRequestDto) {
        log.info("DrugServiceImpl | call from IMS, to check drug availability and update stocks");

        Drug drug;

        for (DrugDto drugDto : drugStockRequestDto.getDrugDtoList()){
            drug = checkDrugAvailability(drugDto);
            if(drug == null)
                return false;

            drug.setStock((long) (drug.getStock() - drugDto.getStockRequired()));
            drugRepository.save(drug);
        }

        return true;
    }

///    --------------------Internal Methods

    public Drug checkDrugAvailability(DrugDto drugDto) {

        return drugRepository
                .findByTranIdAndStockGreaterThan(
                        drugDto.getDrug_id(),
                        drugDto.getStockRequired()
                )
                .orElse(null);
    }
}
