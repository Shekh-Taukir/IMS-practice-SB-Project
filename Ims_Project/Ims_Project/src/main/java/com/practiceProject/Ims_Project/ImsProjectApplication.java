package com.practiceProject.Ims_Project;

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
 // v1.1 || type : New Func || Apr 01, 2026 || TaukirS (ER 1101)
 // v1.2 || type : Change || Apr 01, 2026 || TaukirS (ER 1108 - OpenFeign client setup)
////////////////////////////////////////////////

@SpringBootApplication
//Apr 01, 2026 TaukirS (ER 1108 - OpenFeign client setup)
@EnableFeignClients
public class ImsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImsProjectApplication.class, args);
	}

}
