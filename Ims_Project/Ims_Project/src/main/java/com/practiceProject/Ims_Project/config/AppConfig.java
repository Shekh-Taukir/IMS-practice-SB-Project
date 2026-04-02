package com.practiceProject.Ims_Project.config;

import io.swagger.v3.oas.annotations.media.Schema;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

////////////////////////////////////////////////
//
 // Name: App Config file
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : New Func || Mar 19, 2026 || TaukirS (ER 1101)
 // v1.1 || type : Change || Mar 29, 2026 || TaukirS (ER 1104 - Jwt Authentication integration changes)
 // v1.1 || type : Change || Apr 02, 2026 || TaukirS (ER 1109 - implement drug module in ims_project)
////////////////////////////////////////////////

@Configuration
@Schema(hidden = true)
//Apr 02, 2026 TaukirS (ER 1109 - implement drug module in ims_project) changed the file name to AppConfig
public class AppConfig {

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    //Start Mar 29, 2026 TaukirS (ER 1104 - Jwt Authentication integration changes)
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /// Authentication manager object is used at time of our Custom Jwt filter that is added in Security Filter Chain
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    //End Mar 29, 2026 TaukirS (ER 1104 - Jwt Authentication integration changes)
}
