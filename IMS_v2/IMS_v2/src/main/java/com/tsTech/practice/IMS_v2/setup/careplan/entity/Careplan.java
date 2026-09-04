package com.tsTech.practice.IMS_v2.setup.careplan.entity;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "careplan_mst")
public class Careplan extends BaseEntity {

    @Column(length = 200)
    private String name;

    @Column(nullable = false)
    private Boolean toBePrint = true;
}
