package com.practiceProject.Ims_Project.inventory_service.entity;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.practiceProject.Ims_Project.inventory_service.entity.baseFiles.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonPropertyOrder(value = {"tranId"})
@Table(name = "drug_mst_sb")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Drug extends BaseEntity {
    private Long drugId;
    private Long stock;
}
