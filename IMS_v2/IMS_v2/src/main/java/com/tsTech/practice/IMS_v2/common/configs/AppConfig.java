package com.tsTech.practice.IMS_v2.common.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

////////////////////////////////////////////////
//
// Name: App Config class
//
 // Description: Config class, to setup config beans, eg: modelMapper.
//
 // Version history:
//
 // v1.1 || type : Change || Jun 18, 2026 || TaukirS (ER 1001 - patient mst setup)
////////////////////////////////////////////////
@Configuration
@EnableJpaAuditing
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
