package com.practiceProject.Ims_Project.security;

import com.practiceProject.Ims_Project.advices.ApiError;
import com.practiceProject.Ims_Project.advices.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
////////////////////////////////////////////////
//
// Name:
//
 // Description:
//
 // Version history:
//
// v1.1 || type : Change || Mar 29, 2026 || TaukirS (ER 1107 - add authEntryPoint exception class in authorization layer)
 ////////////////////////////////////////////////

@RequiredArgsConstructor
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ApiResponse apiResponse = new ApiResponse(
                new ApiError(
                        "Authorization header is missing. Please add proper Authorization token to access the API.",
                        HttpStatus.UNAUTHORIZED
                )
        );
        objectMapper.writeValue(response.getOutputStream(),apiResponse);
    }
}
