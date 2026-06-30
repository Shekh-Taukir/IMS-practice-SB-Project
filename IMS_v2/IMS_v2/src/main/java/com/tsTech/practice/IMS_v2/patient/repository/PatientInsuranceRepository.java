package com.tsTech.practice.IMS_v2.patient.repository;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.patient.dtos.PatientInsuranceDTO;
import com.tsTech.practice.IMS_v2.patient.entities.PatientInsurance;
import com.tsTech.practice.IMS_v2.patient.enums.InsurancePriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

////////////////////////////////////////////////
//
// Name: Patient Insurance Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : New Func || Jul 01, 2026 || TaukirS (ER 1005 - patient insurance setup)
////////////////////////////////////////////////

@Repository
public interface PatientInsuranceRepository extends JpaRepository<PatientInsurance, Long> {
    List<PatientInsurance> findByPatient_TranId(Long patient_id);

    default PatientInsurance getPatientInsuranceEntityById(Long tranId){
        return findById(tranId)
                .orElseThrow(()-> new ResourceNotFoundException("Insurance Not found for id: "+tranId));
    }

    @Query(value =
            "select " +
            "   ins.priority " +
            "from " +
            "   patient_mst_sb as pat " +
            "left outer join " +
            "   patient_insurance_sb as ins " +
            "   on ins.patient_id = pat.tran_id " +
            "where " +
            "   pat.tran_id = :patient_id ",
            nativeQuery = true)
    List<String> getCurrentPriorities(@Param("patient_id") Long patient_id);

    /// Function checks that incoming insurance priority is used in any other insurance or not?
    Boolean existsByPatient_TranIdAndPriority(Long patient_id, InsurancePriority priority);
}
