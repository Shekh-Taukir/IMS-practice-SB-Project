package com.practiceProject.Ims_Project.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DrugDto {
    private Long drug_id;
    private Double stockRequired;
}
