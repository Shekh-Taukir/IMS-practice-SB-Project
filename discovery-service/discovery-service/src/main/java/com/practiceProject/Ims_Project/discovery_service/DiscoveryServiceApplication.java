package com.practiceProject.Ims_Project.discovery_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

////////////////////////////////////////////////
//
// Name:
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Apr 01, 2026 || TaukirS (ER 1106 - Eureka servier config)
////////////////////////////////////////////////

@SpringBootApplication
//Apr 01, 2026 TaukirS (ER 1106 - Eureka servier config)
@EnableEurekaServer
public class DiscoveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServiceApplication.class, args);
	}

}
