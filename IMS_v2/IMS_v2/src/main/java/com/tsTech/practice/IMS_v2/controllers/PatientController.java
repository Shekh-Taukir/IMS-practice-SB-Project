package com.tsTech.practice.IMS_v2.controllers;

import com.tsTech.practice.IMS_v2.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

////////////////////////////////////////////////
//
// Name: PatientController
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
////////////////////////////////////////////////

@RestController
@RequiredArgsConstructor
@RequestMapping("/patient")
public class PatientController {

    private final PatientService patientService;
}
