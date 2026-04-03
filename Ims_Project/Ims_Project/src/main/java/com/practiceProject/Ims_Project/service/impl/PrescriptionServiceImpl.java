package com.practiceProject.Ims_Project.service.impl;

import com.practiceProject.Ims_Project.clients.InventoryFeignClient;
import com.practiceProject.Ims_Project.dto.PrescriptionDto;
import com.practiceProject.Ims_Project.dto.PrescriptionRequestDto;
import com.practiceProject.Ims_Project.dto.foreignServices.DrugStockRequestDto;
import com.practiceProject.Ims_Project.entity.Drug;
import com.practiceProject.Ims_Project.entity.Patient;
import com.practiceProject.Ims_Project.entity.Prescription;
import com.practiceProject.Ims_Project.exception.ResourceNotFoundException;
import com.practiceProject.Ims_Project.mappers.PrescriptionMapper;
import com.practiceProject.Ims_Project.repository.PrescriptionRepository;
import com.practiceProject.Ims_Project.service.PrescriptionService;
import com.practiceProject.Ims_Project.service.helperServices.EntityFinder;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

////////////////////////////////////////////////
//
 // Name: Prescription Service Implementation
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Apr 02, 2026 || TaukirS (ER 1109 - implement drug module in ims_project)
 // v1.1 || type : Change || Apr 03, 2026 || TaukirS (ER 1110 - Drug entity implementation in inventory_service)
////////////////////////////////////////////////

@RequiredArgsConstructor
@Service
@Slf4j
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final EntityFinder entityFinder;
    private final PrescriptionMapper prescriptionMapper;
    private final InventoryFeignClient inventoryFeignClient;

    @Override
    public PrescriptionRequestDto getPatientPrescriptions(Long patientId) {
        entityFinder.findPatientByIdOrException(patientId);

        List<Prescription> prescriptionList = prescriptionRepository.findByPatient_TranId(patientId);

        return PrescriptionRequestDto
                .builder()
                .prescriptionList(prescriptionMapper.toDtoList(prescriptionList))
                .patient_id(patientId)
                .build();
    }

    @Override
    @Transactional
    public PrescriptionRequestDto createPrescription(PrescriptionRequestDto prescriptionRequestDto) {
        /// Check if patient exists or not
        Patient patient = entityFinder.findPatientByIdOrException(prescriptionRequestDto.getPatient_id());

        Drug drug;
        Prescription prescription;

        //Start Apr 03, 2026 TaukirS (ER 1110 - Drug entity implementation in inventory_service)
        DrugStockRequestDto drugStockRequestDto = new DrugStockRequestDto(prescriptionMapper
                        .toDrugDtoList(prescriptionRequestDto.getPrescriptionList())
        );

        log.info("[Create Prescription Service Impl] Calling inventory service, to check and reduce durg stock");

        if (!inventoryFeignClient.checkDrugAndStock(drugStockRequestDto)){
            throw new ResourceNotFoundException("Required stock for drug is not available in inventory.");
        }

        for(PrescriptionDto prescriptionDto : prescriptionRequestDto.getPrescriptionList()){
            prescription = prescriptionMapper.toEntity(prescriptionDto);

            /// Check if drug exists or not
            drug = entityFinder.findDrugByIdOrException(prescriptionDto.getDrug_id());

            prescriptionDto.setDrug_name(drug.getDrugName());
            prescription.setPatient(patient);
            prescription.setDrug(drug);
            prescription = prescriptionRepository.save(prescription);

            prescriptionDto.setTranId(prescription.getTranId());
        }

        return prescriptionRequestDto;

    }

    // ----------------------Internal methods


}
