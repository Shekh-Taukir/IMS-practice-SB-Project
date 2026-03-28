////////////////////////////////////////////////
//
 // Name:
//
 // Description:
//
 // Version history:
//
// v1.1 || type : Change || Mar 29, 2026 || TaukirS (ER 1104 - Jwt Authentication integration changes)
 ////////////////////////////////////////////////
package com.practiceProject.Ims_Project.repository;

import com.practiceProject.Ims_Project.entity.auth.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Repository
public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    Optional<SystemUser> findByUsername(String username);
}