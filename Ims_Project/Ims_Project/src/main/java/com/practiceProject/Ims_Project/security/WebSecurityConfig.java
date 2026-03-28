package com.practiceProject.Ims_Project.security;

import com.practiceProject.Ims_Project.entity.auth.SystemUser;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // Mar 29, 2026 TaukirS (ER 1104 - Jwt Authentication integration changes)
        /// This setup is done, to use default security layer that is in Memory user Based
        /*
        httpSecurity
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/public/**", "/doctor/**").permitAll()
                        .requestMatchers("/appointment/**","/patient/**").authenticated()
                        .requestMatchers("/office/**").hasAnyRole( "FRONTDESK", "DOCTOR", "ADMIN")
                )
                .formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

    @Bean
    UserDetailsService userDetailsService(){
        UserDetails patient = User
                .withUsername("pat1")
                .password(passwordEncoder.encode("pat1"))
                .roles("PATIENT")
                .build();

        UserDetails admin = User
                .withUsername("system")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();

        UserDetails doctor = User
                .withUsername("doc1")
                .password(passwordEncoder.encode("doc1"))
                .roles("DOCTOR")
                .build();

        return new InMemoryUserDetailsManager(patient,admin,doctor);
    }
    */

        // Start Mar 29, 2026 TaukirS (ER 1104 - Jwt Authentication integration changes)
        /// this setup is done, to remove the csrf configurations for default spring security flow
        /// and have added public and auth apis to be accessible, and rest requires authentication for proceeding further
        /// and added Jwt Auth filter in Spring security filter chain
        httpSecurity
                .csrf(csrfConfig -> csrfConfig.disable())
                .sessionManagement(
                        sessionConfig -> sessionConfig
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**", "/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
        // End Mar 29, 2026 TaukirS (ER 1104 - Jwt Authentication integration changes)
    }
}
