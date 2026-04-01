package com.practiceProject.Ims_Project.inventory_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

////////////////////////////////////////////////
//
// Name:
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Apr 01, 2026 || TaukirS (ER 1106 - Eureka server configs)
 // v1.1 || type : Change || Apr 01, 2026 || TaukirS (ER 1108 - OpenFeign client setup)
////////////////////////////////////////////////

@SpringBootApplication
//Apr 01, 2026 TaukirS (ER 1108 - OpenFeign client setup)
@EnableFeignClients
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}
