package com.practiceProject.Ims_Project.controller;

import com.libraryCoding.commonLibrary.GreetingService;
import com.practiceProject.Ims_Project.advices.ApiResponse;
import com.practiceProject.Ims_Project.dto.AppointmentDto;
import com.practiceProject.Ims_Project.dto.AppointmentResponseDto;
import com.practiceProject.Ims_Project.service.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Appointment Master", description = "Appointment Master Api's")
//@RequestMapping("/appointment")
@RequiredArgsConstructor
@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final GreetingService greetingService;

    @GetMapping("/appointment/list")
    public ResponseEntity<List<AppointmentDto>> getAppointmentList(){
        return ResponseEntity.ok(appointmentService.getAppointmentList());
    }

    @GetMapping("/public/appointment/detailedList")
    public ResponseEntity<List<AppointmentResponseDto>> getAppointmentDetailedList(){
        return ResponseEntity.ok(appointmentService.getAppointmentDetailedList());
    }

    //Start Mar 29, 2026 TaukirS (ER 1105 - Custom Library Integration)
    @GetMapping("/public/libraryTest")
    public ResponseEntity<ApiResponse<String>> libraryCodetesting(){
        String result = greetingService.greet("Taukir [SpringBoot]");

        /// Need to create the ApiResponse Object, because if we return RE<String> or String, then
        return ResponseEntity.ok(new ApiResponse<>(result));
    }
    //End Mar 29, 2026 TaukirS (ER 1105 - Custom Library Integration)
}
