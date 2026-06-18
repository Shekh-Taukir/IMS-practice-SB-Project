package com.tsTech.practice.IMS_v2.entities;

import com.tsTech.practice.IMS_v2.entities.base.BaseEntity;
import com.tsTech.practice.IMS_v2.entityEnums.UserSexEnum;
import com.tsTech.practice.IMS_v2.entityEnums.UserSexualOrientation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

////////////////////////////////////////////////
//
// Name: Patient Mst entity
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
////////////////////////////////////////////////

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "patient_mst_sb")
public class Patient extends BaseEntity {

    private String firstName;
    private String lastName;
    private String middleName;
    private String aka;

    @Column(length = 500)
    private String address1;

    @Column(length = 500)
    private String address2;

    private LocalDate birthDate;
    private LocalDate deceasedDate;

    private String email;

    @Enumerated(EnumType.STRING)
    private UserSexEnum prefix;

    @Column(length = 2000)
    private String note;

    @Enumerated(EnumType.STRING)
    private UserSexualOrientation sexualOrientation;

}
