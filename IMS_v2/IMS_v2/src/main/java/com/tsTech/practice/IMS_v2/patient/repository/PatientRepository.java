package com.tsTech.practice.IMS_v2.patient.repository;

import com.tsTech.practice.IMS_v2.common.exception.ResourceNotFoundException;
import com.tsTech.practice.IMS_v2.patient.dtos.projectionInterface.PatientProjection;
import com.tsTech.practice.IMS_v2.patient.dtos.records.response.PatientResponse;
import com.tsTech.practice.IMS_v2.patient.entities.Patient;
import jakarta.validation.OverridesAttribute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

////////////////////////////////////////////////
//
// Name: Patient Mst Repository
//
// Description:
//
// Version history:
//
// v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
// v1.2 || type : Change || Jun 29, 2026 || TaukirS (ER 1005 - patient insurance setup)
// v1.3 || type : Change || Jul 30, 2026 || TaukirS (ER 1011 - flyway integration & add office and provider in patient)
////////////////////////////////////////////////

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {


    @Override
    boolean existsById(Long patientId);

    String sqlQuery =
            "SELECT " +
            "   p.firstName AS firstName, p.lastName AS lastName, p.middleName AS middleName, p.aka AS aka, p.address1 as address1, " +
            "   p.address2 as address2, p.birthDate as birthDate, p.deceasedDate as deceasedDate, p.email as email, p.prefix as prefix, " +
            "   p.sex as sex, p.note as note, p.sexualOrientation as sexualOrientation, "+
            "   p.tranId as tranId, p.createdAt as createdAt, p.updatedAt as updatedAt, p.isActive as isActive, " +
            "   o.officeName as officeName, prov.lastName as providerName, o.tranId as officeId, prov.tranId as providerId " +
            "FROM " +
            "   Patient p " +
            "INNER JOIN " +
            "   Office o " +
            "   ON o.tranId = p.office.tranId " +
            "INNER JOIN " +
            "   Provider prov " +
            "   ON prov.tranId = p.provider.tranId ";

    //Start Jul 01, 2026 TaukirS (ER 1005 - patient insurance setup)
    default Patient getPatientEntityById(Long patientId){
        return findById(patientId)
                .orElseThrow(()-> new ResourceNotFoundException("Patient", patientId));
    }
    //End Jul 01, 2026 TaukirS (ER 1005 - patient insurance setup)

    default PatientProjection getPatientWithOfficeAndProviderEntityById(Long patientId){
        return findByIdWithOfficeAndProvider(patientId)
                .orElseThrow(()-> new ResourceNotFoundException("Patient", patientId));
    }

    //Start Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
    @Query( sqlQuery + " where p.tranId = :patientId ")
    Optional<PatientProjection> findByIdWithOfficeAndProvider(@Param("patientId") Long patientId);

    @Query( sqlQuery)
    List<PatientProjection> findAllWithOfficeAndProvider();
    //End Jul 30, 2026 TaukirS (ER 1011 - flyway integration & add office and provider in patient)
}
