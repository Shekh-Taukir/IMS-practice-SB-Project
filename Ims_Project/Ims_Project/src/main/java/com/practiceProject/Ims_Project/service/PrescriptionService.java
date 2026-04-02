package com.practiceProject.Ims_Project.service;

import com.practiceProject.Ims_Project.dto.PrescriptionDto;
import com.practiceProject.Ims_Project.dto.PrescriptionRequestDto;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;

public interface PrescriptionService {
    PrescriptionRequestDto getPatientPrescriptions(Long patientId);

    PrescriptionRequestDto createPrescription(PrescriptionRequestDto prescriptionRequestDto);
}
