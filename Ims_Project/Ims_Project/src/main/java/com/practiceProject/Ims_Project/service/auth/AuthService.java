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
package com.practiceProject.Ims_Project.service.auth;

import com.practiceProject.Ims_Project.dto.auth.LoginRequestDto;
import com.practiceProject.Ims_Project.dto.auth.LoginResponseDto;
import com.practiceProject.Ims_Project.dto.auth.SignupResponseDto;
import com.practiceProject.Ims_Project.entity.auth.SystemUser;
import com.practiceProject.Ims_Project.repository.SystemUserRepository;
import com.practiceProject.Ims_Project.security.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthUtil authUtil;
    private final SystemUserRepository systemUserRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        SystemUser user = (SystemUser) authentication.getPrincipal();
        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto().builder().jwt(token).userId(user.getId()).build();
    }

    public SignupResponseDto signup(LoginRequestDto signupRequestDto) {
        SystemUser user = systemUserRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if (user != null){
            throw new IllegalArgumentException("User already exists with this username");
        }

        user = systemUserRepository.save(SystemUser
                .builder()
                .username(signupRequestDto.getUsername())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .build()
        );

        return modelMapper.map(user, SignupResponseDto.class);
    }
}
