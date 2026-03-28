package com.practiceProject.Ims_Project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.practiceProject.Ims_Project.entity.baseFiles.BaseEntityEMR;
import com.practiceProject.Ims_Project.entity.type.UserPrefixEnum;
import com.practiceProject.Ims_Project.entity.type.UserSexEnum;
import jakarta.persistence.*;
import lombok.*;

@Table(name="doctor_mst_sb")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
public class Doctor extends BaseEntityEMR {
    private String firstName;
    private String lastName;
    private String middleName;

    @Enumerated(EnumType.STRING)
    private UserSexEnum sex;

    @Enumerated(EnumType.STRING)
    private UserPrefixEnum prefix;

    private String specialization;

    private String ssno;

    @Column(length = 200)
    private String streetAddress;

    private String phoneNo;

    @JoinColumn(name = "officeId", foreignKey = @ForeignKey(name = "fk_doctor_mst_sb_office_mst_sb"))
    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    private Office office;

    //in future
    //private Office office;
    //private Zip zip;

    //--------------------------------Foreign Key Columns

    @Column(updatable = false, insertable = false)
    @ToString.Include(name="officeId")
    @JsonProperty("officeId")
    public Long getOfficeId(){
        return office.getTranId();
    }
}
