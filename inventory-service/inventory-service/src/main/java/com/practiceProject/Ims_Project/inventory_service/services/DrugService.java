package com.practiceProject.Ims_Project.inventory_service.services;

import com.practiceProject.Ims_Project.inventory_service.dto.DrugStockRequestDto;

public interface DrugService {

    Boolean checkDrugAndStock(DrugStockRequestDto drugDto);
}
