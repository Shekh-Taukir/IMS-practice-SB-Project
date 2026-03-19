////////////////////////////////////////////////
//
 // Name: PatientMst.java
//
 // Description: Entity Class that maps with patient_mst table
//
 // Version history:
// v1.0 || type : New Class || Mar 16, 2026 || TaukirS (ER 1101 - Patient Master Base Changes in SB project)
////////////////////////////////////////////////

package com.practiceProject.Ims_Project.entity;

import com.practiceProject.Ims_Project.entity.type.BloodGroupEnum;
import com.practiceProject.Ims_Project.entity.type.UserPrefixEnum;
import com.practiceProject.Ims_Project.entity.type.UserSexEnum;
import com.practiceProject.Ims_Project.entity.type.UserSexOrientationEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "patient_mst")
@ToString
public class Patient extends BaseEntityEMR{
    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;
    private String middleName;

    @Column(nullable = false, length = 30)
    private String aka;

    @Column(nullable = false, length = 500)
    private String address1;

    @Column(length = 500)
    private String address2;

    @Column(nullable = false)
    private LocalDate birthDate;
    private LocalDate deceasedDate;

    //Enums dependent fields
    @Enumerated(EnumType.STRING)
    private UserPrefixEnum prefix;

    @Enumerated(EnumType.STRING)
    private BloodGroupEnum bloodGroup;

    @Enumerated(EnumType.STRING)
    private UserSexEnum sex;

    @Enumerated(EnumType.STRING)
    private UserSexOrientationEnum sexualOrientation;

    //Other Module related fields
//    private Long officeId;
//    private Long doctorId;
//    private Long zipId;
//    private Long pharmacyId;

    @Column(length = 2000)
    private String note;

}
