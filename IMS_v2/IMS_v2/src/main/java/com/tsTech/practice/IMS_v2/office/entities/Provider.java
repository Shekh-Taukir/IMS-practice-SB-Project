package com.tsTech.practice.IMS_v2.office.entities;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserPrefixEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

////////////////////////////////////////////////
//
// Name: Provider Entity
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
@Table(name = "doctor_mst_sb")
public class Provider extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(length = 50)
    private String middleName;

    @Column(length = 50)
    private String suffix;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserPrefixEnum prefix;

    @Column(nullable = false, length = 50)
    private String designation;

    @Column(length = 500, nullable = false)
    private String address1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @Column(nullable = false)
    private String email;

    @Column(length = 50)
    private String npi;

    @Column(nullable = false)
    private LocalDate birthDate;
}
