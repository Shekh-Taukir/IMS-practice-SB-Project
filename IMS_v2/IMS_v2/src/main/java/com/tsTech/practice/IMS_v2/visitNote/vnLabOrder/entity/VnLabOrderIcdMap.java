package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.setup.icd.entities.ICD;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/// //////////////////////////////////////////
//
// Name: VnLabOrder ICD Mapping Entity
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 11, 2026 || TaukirS (ER 1022 - vn lab order icd map entity coding)

/// //////////////////////////////////////////

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "vn_lab_order_icd_map",
        indexes = {
                @Index(name = "idx_vn_lab_order_icd_map_vn_lab_order_id_icd_id", columnList = "vn_lab_order_id, icd_id")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_vn_lab_order_icd_map_vn_lab_order_id_seq_deferrable", columnNames = {"vn_lab_order_id", "seq"})
        }
)
public class VnLabOrderIcdMap extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vn_lab_order_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnLabOrder vnLabOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icd_id", nullable = false)
    private ICD icd;

    @Column(nullable = false, columnDefinition = "BIGINT CHECK (seq BETWEEN 1 AND 8)")
    private Long seq;

    public String toString() {
        return "VnLabOrderIcdMap{VnLabOrder : " + vnLabOrder.getTranId() + " | icd: " + icd.getTranId() + " | seq = " + seq;
    }

}
