package com.practiceProject.Ims_Project.inventory_service.controller;

import com.practiceProject.Ims_Project.inventory_service.advices.ApiResponse;
import com.practiceProject.Ims_Project.inventory_service.dto.DrugDto;
import com.practiceProject.Ims_Project.inventory_service.dto.DrugStockRequestDto;
import com.practiceProject.Ims_Project.inventory_service.services.DrugService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

////////////////////////////////////////////////
 //
// Name: Inventory Drug Controller
//
 // Description:
//
 // Version history:
//
// v1.1 || type : Change || Apr 01, 2026 || TaukirS (ER 1107 - API gateway config changes)
// v1.1 || type : Change || Apr 02, 2026 || TaukirS (ER 1110 - Drug entity implementation in inventory_service)
////////////////////////////////////////////////


@RestController
@RequiredArgsConstructor
@RequestMapping("/drug")
@Slf4j
public class DrugController {

    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;
    private final DrugService drugService;

    @GetMapping("/testIms")
    public String testImsApi(){

        ServiceInstance serviceInstance = discoveryClient.getInstances("ims-project").getFirst();

        ApiResponse<String> response = restClient.get()
                .uri(serviceInstance.getUri()+"/ims/public/libraryTest")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        if(response!=null && response.isSuccess()){
            String data = response.getData();
            return "Response from IMS API : "+data;
        } else {
            return "Response contains error : "+response.getError();
        }
    }

    @PutMapping("/checkStock")
    public Boolean checkDrugAndStock(@RequestBody DrugStockRequestDto drugStockRequestDto){
        log.info("[Prescription Controller] Api called from IMS-project to check drug stocks are there or not.");
        return drugService.checkDrugAndStock(drugStockRequestDto);
    }
}
