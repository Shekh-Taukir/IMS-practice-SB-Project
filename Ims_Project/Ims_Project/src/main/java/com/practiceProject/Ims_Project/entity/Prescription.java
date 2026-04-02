package com.practiceProject.Ims_Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.practiceProject.Ims_Project.entity.baseFiles.BaseEntityEMR;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

////////////////////////////////////////////////
//
 // Name: Prescription entity
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : New Func || Apr 02, 2026 || TaukirS (ER 1109 - implement drug module in ims_project)
////////////////////////////////////////////////

@Table(name = "prescription_mst_sb")
@JsonPropertyOrder(value = {"tranId"})
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Prescription extends BaseEntityEMR {

    private Double doseIntake;
    private String sig;
    private Integer daysToTake;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "drug_id", foreignKey = @ForeignKey(name = "fk_prescription_mst_sb_drug_mst_sb"))
    @ToString.Exclude
    @JsonIgnore
    private Drug drug;

    @ManyToOne
    @JoinColumn(name = "patient_id", foreignKey = @ForeignKey(name = "fk_prescription_mst_sb_patient_mst_sb"))
    @ToString.Exclude
    @JsonIgnore
    private Patient patient;

    /// --------------------------------- Foreign key Columns

    @JsonProperty("test_data")
    @Column(updatable = false, insertable = false)
    public Long getTestData(){
        return 89789L;
    }
}
