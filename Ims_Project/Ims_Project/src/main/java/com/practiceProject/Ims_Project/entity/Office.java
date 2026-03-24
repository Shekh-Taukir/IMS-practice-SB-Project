package com.practiceProject.Ims_Project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "office_mst_sb")
@AllArgsConstructor
@NoArgsConstructor
public class Office extends BaseEntityEMR{
    private String name;
    private String code;
    private String phoneNumber;
    private String email;
    private String faxNumber;
    private Boolean isBilling;
    private String npi;

    //in future
    //office group
    //zip
}
