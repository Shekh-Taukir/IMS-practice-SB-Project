package com.tsTech.practice.IMS_v2.setup.visitType.entities;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/// //////////////////////////////////////////
//
// Name: Visit Type Entity
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Aug 15, 2026 || TaukirS (ER 1012 - visit type entity code)

/////////////////////////////////////////////

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "visit_type",
        indexes = {@Index(name = "idx_visit_type_name_upper", columnList = "UPPER(name)", unique = true)}
)
public class VisitType extends BaseEntity {

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 1000)
    private String note;
}
