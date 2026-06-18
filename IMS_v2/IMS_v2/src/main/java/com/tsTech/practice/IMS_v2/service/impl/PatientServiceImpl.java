package com.tsTech.practice.IMS_v2.service.impl;

import com.tsTech.practice.IMS_v2.repository.PatientRepository;
import com.tsTech.practice.IMS_v2.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

////////////////////////////////////////////////
//
// Name: PatientServiceImpl
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
////////////////////////////////////////////////

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
}
