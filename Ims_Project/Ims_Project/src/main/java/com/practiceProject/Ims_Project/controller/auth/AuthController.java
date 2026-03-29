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
package com.practiceProject.Ims_Project.controller.auth;

import com.practiceProject.Ims_Project.advices.ApiResponse;
import com.practiceProject.Ims_Project.dto.auth.LoginRequestDto;
import com.practiceProject.Ims_Project.dto.auth.LoginResponseDto;
import com.practiceProject.Ims_Project.dto.auth.SignupResponseDto;
import com.practiceProject.Ims_Project.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody LoginRequestDto signupRequestDto){
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler()
                .logout(request,response, SecurityContextHolder.getContext().getAuthentication());

        return ResponseEntity.ok(new ApiResponse<>("User logged out successfully"));
    }

}
