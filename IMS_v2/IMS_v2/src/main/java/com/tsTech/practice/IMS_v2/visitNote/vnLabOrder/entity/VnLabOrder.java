package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.visitNote.core.entities.VisitNote;
import com.tsTech.practice.IMS_v2.visitNote.labOrder.entity.LabOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
        name = "vn_lab_order",
        indexes = {
                @Index(name = "idx_vn_lab_order_pn_id_desc", columnList = "pn_id DESC"),
                @Index(name = "idx_vn_lab_order_lab_order_id_desc", columnList = "lab_order_id DESC")
        }
)
public class VnLabOrder extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pn_id", nullable = false)
    private VisitNote visitNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lab_order_id", nullable = false)
    private LabOrder labOrder;

    @Column(nullable = false)
    private LocalDateTime takenAt;

    @Column(length = 1000)
    private String note;
}
