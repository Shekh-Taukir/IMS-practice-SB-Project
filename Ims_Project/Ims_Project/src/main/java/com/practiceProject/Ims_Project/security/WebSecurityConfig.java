package com.practiceProject.Ims_Project.security;

import com.practiceProject.Ims_Project.entity.auth.SystemUser;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        /// This setup is done, to use default security layer that is in Memory user Based
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

}
