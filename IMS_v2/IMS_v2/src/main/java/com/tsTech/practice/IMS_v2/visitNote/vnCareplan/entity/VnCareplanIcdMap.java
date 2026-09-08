package com.tsTech.practice.IMS_v2.visitNote.vnCareplan.entity;

import com.tsTech.practice.IMS_v2.common.entity.base.BaseEntity;
import com.tsTech.practice.IMS_v2.setup.icd.entities.ICD;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

/// //////////////////////////////////////////
//
// Name: VisitNote Careplan Icd Map Entity
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Sep 08, 2026 || TaukirS (ER 1021 - vn careplan icd map entity coding)

/// //////////////////////////////////////////

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(
        name = "vn_careplan_icd_map",
        indexes = {
                @Index(name = "idx_vn_careplan_icd_map_vn_careplan_id_desc", columnList = "vn_careplan_id DESC")
        }
)
public class VnCareplanIcdMap extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vn_careplan_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VnCareplan vnCareplan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "icd_id", nullable = false)
    private ICD icd;

    @Column(nullable = false, columnDefinition = "BIGINT CHECK (seq BETWEEN 1 AND 8)")
    private Long seq;

    public String toString() {
        return "VnCareplanIcdMap{ vnCareplan : " + vnCareplan.getTranId() + " | icd: " + icd.getTranId() + " | seq = " + seq + " }";
    }
}
