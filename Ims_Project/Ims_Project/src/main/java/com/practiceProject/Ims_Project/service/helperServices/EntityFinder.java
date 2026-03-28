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
import com.practiceProject.Ims_Project.entity.Office;
import com.practiceProject.Ims_Project.exception.ResourceNotFoundException;
import com.practiceProject.Ims_Project.repository.DoctorRepository;
import com.practiceProject.Ims_Project.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityFinder {
    private final OfficeRepository officeRepository;
    private final DoctorRepository doctorRepository;

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
}
