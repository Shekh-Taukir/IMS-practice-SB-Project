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
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final SystemUserRepository userRepository;
    private final AuthUtil authUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            log.info("Incoming request : {}", request.getRequestURI());

            final String requestTokenHeader = request.getHeader("Authorization");

            //for non authorized api calls
            if (requestTokenHeader == null || requestTokenHeader.isBlank()) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = requestTokenHeader.split("Bearer ")[1];
            String username = authUtil.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                SystemUser user = userRepository.findByUsername(username).orElseThrow();

                UsernamePasswordAuthenticationToken usernameToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(usernameToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e){
            log.error("Exception occured at Filter chain layer for request : {}",request.getRequestURI());
            handlerExceptionResolver.resolveException(request,response,null,e);
        }
    }
}
