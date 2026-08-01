package com.tsTech.practice.IMS_v2.patient.entities;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserPrefixEnum;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserSexEnum;
import com.tsTech.practice.IMS_v2.common.entity.enums.UserSexualOrientation;
import com.tsTech.practice.IMS_v2.office.entities.Office;
import com.tsTech.practice.IMS_v2.office.entities.Provider;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;

////////////////////////////////////////////////
//
// Name: Patient Mst entity
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
// v1.2 || type : Change || Jul 23, 2026 || TaukirS (ER 1007 - logging and dto to record changes)
// v1.3 || type : Change || Jul 29, 2026 || TaukirS (ER 1011 - flyway integration & add office and provider in patient)
////////////////////////////////////////////////

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_mst_sb")
public class Patient extends BaseEntity {

    //Jul 23, 2026 TaukirS (ER 1007 - logging and dto to record changes) -> added nullable = false validations in entity level

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String middleName;

    @Column(nullable = false, length = 50)
    private String aka;

    @Column(length = 500, nullable = false)
    private String address1;

    @Column(length = 500)
    private String address2;

    @Column(nullable = false)
    private LocalDate birthDate;
    private LocalDate deceasedDate;

    @Email
    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserPrefixEnum prefix;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserSexEnum sex;

    @Column(length = 2000)
    private String note;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UserSexualOrientation sexualOrientation;

    //Start Jul 29, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id", nullable = false)
    Office office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    Provider provider;
    //End Jul 29, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
}
