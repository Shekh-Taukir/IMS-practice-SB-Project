package com.practiceProject.Ims_Project.inventory_service.controller;

import com.practiceProject.Ims_Project.inventory_service.advices.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

////////////////////////////////////////////////
 //
// Name:
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Apr 01, 2026 || TaukirS (ER 1107 - API gateway config changes)
////////////////////////////////////////////////
@RestController
@AllArgsConstructor
public class DrugController {

    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;

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
}
