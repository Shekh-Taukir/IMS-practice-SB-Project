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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.practiceProject.Ims_Project.entity.baseFiles.BaseEntityEMR;
import com.practiceProject.Ims_Project.entity.type.BloodGroupEnum;
import com.practiceProject.Ims_Project.entity.type.UserPrefixEnum;
import com.practiceProject.Ims_Project.entity.type.UserSexEnum;
import com.practiceProject.Ims_Project.entity.type.UserSexOrientationEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "patient_mst_sb")
public class Patient extends BaseEntityEMR {
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

    private String email;

    //Enums dependent fields
    @Enumerated(EnumType.STRING)
    private UserPrefixEnum prefix;

    @Enumerated(EnumType.STRING)
    private BloodGroupEnum bloodGroup;

    @Enumerated(EnumType.STRING)
    private UserSexEnum sex;

    @Enumerated(EnumType.STRING)
    private UserSexOrientationEnum sexualOrientation;

    @Column(length = 2000)
    private String note;

    private Long testId;

// Mar 27, 2026 TaukirS (ER 1102 - Completing all necessary modules API's) updated form One to One -> Many to ONe
    @ManyToOne
    @JoinColumn(name = "office_id", foreignKey = @ForeignKey(name="fk_patient_mst_sb_office_mst_sb"))
    @JsonIgnore
    @ToString.Exclude
    private Office office;

    // Mar 27, 2026 TaukirS (ER 1102 - Completing all necessary modules API's) updated form One to One -> Many to ONe
    @ManyToOne
    @JoinColumn(name = "doctor_id", foreignKey = @ForeignKey(name="fk_patient_mst_sb_doctor_mst_sb"))
    @JsonIgnore
    @ToString.Exclude
    private Doctor doctor;

//    -------------------------------------------
//    Other Module related fields
//    private Long zipId;
//    private Long pharmacyId;
//    -------------------------------------------

    //--------------------------------Foreign Key Columns

    @JsonProperty("officeId")
    @Column(updatable = false, insertable = false)
    @ToString.Include(name = "officeId")
    public Long getOfficeId(){
        return office!=null ? office.getTranId() : null;
    }

    @JsonProperty("doctorId")
    @Column(updatable = false, insertable = false)
    @ToString.Include(name = "doctorId")
    public Long getDoctorId(){
        return doctor!=null ? doctor.getTranId() : null;
    }
}
