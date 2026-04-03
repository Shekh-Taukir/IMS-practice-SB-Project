package com.practiceProject.Ims_Project.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DrugStockRequestDto {
    private List<DrugDto> drugDtoList;
}
