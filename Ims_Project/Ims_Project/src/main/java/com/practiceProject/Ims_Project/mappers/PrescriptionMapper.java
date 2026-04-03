package com.practiceProject.Ims_Project.mappers;

import com.practiceProject.Ims_Project.dto.PrescriptionDto;
import com.practiceProject.Ims_Project.dto.foreignServices.DrugDto;
import com.practiceProject.Ims_Project.entity.Prescription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import javax.crypto.spec.PSource;
import java.util.List;

////////////////////////////////////////////////
//
 // Name:
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Apr 02, 2026 || TaukirS (ER 1109 - implement drug module in ims_project)
 // v1.2 || type : Change || Apr 03, 2026 || TaukirS (ER 1110 - Drug entity implementation in inventory_service)
////////////////////////////////////////////////

@Mapper(componentModel = "spring")
@Component
public interface PrescriptionMapper {

    @Mapping(source = "drug.tranId", target = "drug_id")
    @Mapping(source = "drug.drugName", target = "drug_name")
    @Mapping(expression = "java(prescription.getTestData())", target = "test_data")
    PrescriptionDto toDto(Prescription prescription);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "drug", ignore = true)
    Prescription toEntity(PrescriptionDto prescriptionDto);

    List<PrescriptionDto> toDtoList(List<Prescription> prescriptionList);

    //Start Apr 03, 2026 TaukirS (ER 1110 - Drug entity implementation in inventory_service)
    @Mapping(expression = "java(prescriptionDto.getDaysToTake() * prescriptionDto.getDoseIntake())", target = "stockRequired")
    DrugDto toDrugDto(PrescriptionDto prescriptionDto);

    List<DrugDto> toDrugDtoList(List<PrescriptionDto> prescriptionDtoList);
    //End Apr 03, 2026 TaukirS (ER 1110 - Drug entity implementation in inventory_service)
}
