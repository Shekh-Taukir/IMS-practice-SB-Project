package com.practiceProject.Ims_Project.entity;

import com.practiceProject.Ims_Project.entity.type.UserPrefixEnum;
import com.practiceProject.Ims_Project.entity.type.UserSexEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="doctor_mst_sb")
public class Doctor extends BaseEntityEMR{
    private String firstName;
    private String lastName;
    private String middleName;

    private UserSexEnum sex;

    private UserPrefixEnum prefix;

    private String specialization;

    private String ssno;

    @Column(length = 200)
    private String streetAddress;

    private String phoneNo;

    //in future
    //private Office office;
    //private Zip zip;

}
