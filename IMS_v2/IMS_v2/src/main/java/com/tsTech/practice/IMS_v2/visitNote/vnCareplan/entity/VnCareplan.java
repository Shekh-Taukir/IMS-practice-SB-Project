package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.entity;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.setup.careplan.entity.Careplan;
import com.tsTech.practice.IMS_v2.visitNote.core.entities.VisitNote;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/// //////////////////////////////////////////
//
// Name: VisitNote Careplan Entity
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 04, 2026 || TaukirS (ER 1018 - visit careplan entity coding)

/// //////////////////////////////////////////

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "vn_careplan_mst",
        indexes = {
                @Index(name = "idx_diagnosis_mst_pn_id_careplan_id_desc", columnList = "pn_id DESC, careplan_id DESC")
        }
)
public class VnCareplan extends BaseEntity {

    @Column(length = 1000)
    private String description;

    private Long seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pn_id", nullable = false)
    private VisitNote visitNote;

    @JoinColumn(name = "careplan_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Careplan careplan;
}
