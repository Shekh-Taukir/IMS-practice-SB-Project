package com.practiceProject.Ims_Project.controller;

import com.practiceProject.Ims_Project.dto.AppointmentDto;
import com.practiceProject.Ims_Project.service.AppointmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Appointment Master", description = "Appointment Master Api's")
@RequestMapping("/appointment")
@RequiredArgsConstructor
@RestController
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/list")
    public ResponseEntity<List<AppointmentDto>> getAppointmentList(){
        return ResponseEntity.ok(appointmentService.getAppointmentList());
    }
}
