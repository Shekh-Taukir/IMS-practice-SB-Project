package com.tsTech.practice.IMS_v2.office.entities;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

////////////////////////////////////////////////
//
// Name: Office Entity
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jul 28, 2026 || TaukirS (ER 1010 - office api setup changes)
////////////////////////////////////////////////

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "office_mst_sb")
public class Office extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String officeName;

    @Column(nullable = false, length = 50)
    private String officeCode;

    @Column(nullable = false, length = 50)
    private String phoneNo;

    @Column(length = 50)
    private String faxNo;

    @Column(nullable = false)
    private String address1;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String zipCode;
}
