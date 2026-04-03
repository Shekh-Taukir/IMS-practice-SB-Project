package com.practiceProject.Ims_Project.clients;

import com.practiceProject.Ims_Project.dto.foreignServices.DrugStockRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;

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

@FeignClient(name = "inventory-service", path = "/inventory")
public interface InventoryFeignClient {

    @PutMapping("/drug/checkStock")
    public Boolean checkDrugAndStock(DrugStockRequestDto drugStockRequestDto);
}
