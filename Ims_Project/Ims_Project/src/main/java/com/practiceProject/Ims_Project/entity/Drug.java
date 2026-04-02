package com.practiceProject.Ims_Project.entity;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.practiceProject.Ims_Project.entity.baseFiles.BaseEntityEMR;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

////////////////////////////////////////////////
//
// Name: Drug Entity
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Apr 02, 2026 || TaukirS (ER 1109 - implement drug module in ims_project)
////////////////////////////////////////////////

@JsonPropertyOrder(value = {"tranId"})
@Table(name = "drug_mst_sb")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Drug extends BaseEntityEMR {
    private String drugName;
    private String drugSig;
    private String mg;
}
