////////////////////////////////////////////////
//
 // Name:BaseEntity.Java
//
 // Description: Base tables parent class, to provide common fields definition, eg: tran_id, created_by etc
//
 // Version history:
//
// v1.0 || type : New Func || Mar 16, 2026 || TaukirS (ER 1101 - Patient Master Base Changes in SB project)d
 ////////////////////////////////////////////////

package com.practiceProject.Ims_Project.entity.baseFiles;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
//this annotation tells JPA that, this class is for setting up base only, and don't create any table for this class, as its used in further
// child classes
@MappedSuperclass
@Schema(hidden = true)
public abstract class BaseEntityEMR {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //<- this strategy works like autogenerate system in DB.
    @Column(updatable = false)
    private Long tranId;

    //system for autopopulating value for createdById and changedById, will be implemented afterwards of making the base system for project.
//    @CreatedBy
    private Long createdById;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

//    @LastModifiedBy
    private Long changedById;

    @UpdateTimestamp
    private LocalDateTime changedAt;

    private Boolean isActive;
}
