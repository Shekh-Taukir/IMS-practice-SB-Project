/// /////////////////////////////////////////////
//
 // Name: EntityFinder Service
//
 // Description: Common Service class to find entities based on tranId's
//
 // Version history:
//
// v1.0 || type : New Func || Mar 27, 2026 || TaukirS (ER 1102 - Completing all necessary modules API's)
 ////////////////////////////////////////////////
package com.practiceProject.Ims_Project.service.helperServices;

import com.practiceProject.Ims_Project.entity.Doctor;
import com.practiceProject.Ims_Project.entity.Drug;
import com.practiceProject.Ims_Project.entity.Office;
import com.practiceProject.Ims_Project.entity.Patient;
import com.practiceProject.Ims_Project.exception.ResourceNotFoundException;
import com.practiceProject.Ims_Project.repository.DoctorRepository;
import com.practiceProject.Ims_Project.repository.DrugRepository;
import com.practiceProject.Ims_Project.repository.OfficeRepository;
import com.practiceProject.Ims_Project.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

////////////////////////////////////////////////
//
// Name: Entity Finder service Implementation
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : Change || Mar 19, 2026 || TaukirS (ER 1101)
 // v1.2 || type : Change || Apr 02, 2026 || TaukirS (ER 1109 - implement drug module in ims_project)
////////////////////////////////////////////////

@Service
@RequiredArgsConstructor
public class EntityFinder {

    private final OfficeRepository officeRepository;
    private final DoctorRepository doctorRepository;
    //Start Apr 02, 2026 TaukirS (ER 1109 - implement drug module in ims_project)
    private final PatientRepository patientRepository;
    private final DrugRepository drugRepository;
    //End Apr 02, 2026 TaukirS (ER 1109 - implement drug module in ims_project)

    public Office findOfficeByIdOrException(Long officeId){
        return officeRepository
                .findById(officeId)
                .orElseThrow(()-> new ResourceNotFoundException("Office not found for officeId : "+officeId));
    }

    public Doctor findDoctorByIdOrException(Long doctorId){
        return doctorRepository
                .findById(doctorId)
                .orElseThrow(()-> new ResourceNotFoundException("Doctor not found for doctorId : "+doctorId));
    }

    //Start Apr 02, 2026 TaukirS (ER 1109 - implement drug module in ims_project)
    public Patient findPatientByIdOrException(Long patientId){
        return patientRepository
                .findById(patientId)
                .orElseThrow(()-> new ResourceNotFoundException("Patient not found for patientId : "+patientId));
    }

    public Drug findDrugByIdOrException(Long drugId){
        return drugRepository
                .findById(drugId)
                .orElseThrow(()-> new ResourceNotFoundException("Drug not found for drugId : "+drugId));
    }

    //End Apr 02, 2026 TaukirS (ER 1109 - implement drug module in ims_project)
}
