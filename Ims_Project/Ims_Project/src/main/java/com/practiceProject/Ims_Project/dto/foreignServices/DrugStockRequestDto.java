package com.practiceProject.Ims_Project.dto.foreignServices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

////////////////////////////////////////////////
 //
// Name:
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Apr 03, 2026 || TaukirS (ER 1110 - Drug entity implementation in inventory_service)
////////////////////////////////////////////////

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DrugStockRequestDto {
    private List<DrugDto> drugDtoList;
}
