package com.tsTech.practice.IMS_v2.visitNote.labOrder.entity;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/// //////////////////////////////////////////
//
// Name: Lab Order Entitiy
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 06, 2026 || TaukirS (ER 1019 - lab order entity coding)
/// //////////////////////////////////////////

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lab_order_hdr")
public class LabOrder extends BaseEntity{

    @Column(length = 100, nullable = false)
    private String labName;

    @Column(length = 200, nullable = false)
    private String labTestName;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String note;
}
