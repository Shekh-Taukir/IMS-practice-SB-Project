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
package com.practiceProject.Ims_Project.security;

import com.practiceProject.Ims_Project.entity.auth.SystemUser;
import com.practiceProject.Ims_Project.repository.SystemUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final SystemUserRepository systemUserRepository;

    //this method will be called by Security Context manager, to get user details, once user is authenticated from Authentication Filter layer
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return systemUserRepository.findByUsername(username).orElseThrow();
    }
}
