package com.practiceProject.Ims_Project.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/// /////////////////////////////////////////////
//
 // Name: ims-project logging
//
// Description:
//
 // Version history:
//
 // v1.1 || type : New Func || Apr 04, 2026 || TaukirS (ER 1112 - api gateway global filter implmentation)
////////////////////////////////////////////////

@Component
@Slf4j
public class LoggingIMSFilter extends AbstractGatewayFilterFactory<LoggingIMSFilter.Config> {

    public LoggingIMSFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            log.info("Ims-project Pre log | uri : {}", exchange.getRequest().getURI());
            return chain.filter(exchange);
        };
    }

    public static class Config {
    }
}
