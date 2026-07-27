package com.tsTech.practice.IMS_v2.patient.repository;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.patient.entities.PatientInsurance;
import com.tsTech.practice.IMS_v2.patient.enums.InsurancePriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    List<PatientInsurance> findByPatient_TranId(Long patientId);

    default PatientInsurance getPatientInsuranceEntityById(Long patientId, Long insId){
        return findByPatient_TranIdAndTranId(patientId, insId)
                .orElseThrow(()-> new ResourceNotFoundException("Insurance",insId));
    }

    //Jul 26, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    // for such records like patient is there, but insurance are not there, so left join returns a null row,
    // and this breaks the condition of isEmpty(), so changed to inner join, so that empty rows are not there.
    @Query(value =
            "select " +
            "   ins.priority " +
            "from " +
            "   patient_mst_sb as pat " +
            "inner join " +
            "   patient_insurance_sb as ins " +
            "   on ins.patient_id = pat.tran_id " +
            "where " +
            "   pat.tran_id = :patientId ",
            nativeQuery = true)
    List<String> getCurrentPriorities(@Param("patientId") Long patientId);

    /// Function checks that incoming insurance priority is used in any other insurance or not?
    Boolean existsByPatient_TranIdAndPriority(Long patientId, InsurancePriority priority);

    //Jul 25, 2026 TaukirS (ER 1007 - logging and dto to record changes)
    Optional<PatientInsurance> findByPatient_TranIdAndTranId(Long patientId, Long insId);
}
