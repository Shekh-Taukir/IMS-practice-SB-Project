package com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.repository;

import com.tsTech.practice.IMS_v2.visitNote.vnLabOrder.entity.VnLabOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VnLabOrderRepository extends JpaRepository<VnLabOrder, Long> {
}
