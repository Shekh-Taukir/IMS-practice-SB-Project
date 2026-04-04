package com.practiceProject.Ims_Project.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

////////////////////////////////////////////////
//
 // Name: GLobal Logging filter class
//
 // Description:
//
 // Version history:
//
 // v1.1 || type : New FUnc || Apr 04, 2026 || TaukirS (ER 1112 - api gateway global filter implmentation)
////////////////////////////////////////////////

@Component
@Slf4j
public class GlobalLoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Global (pre) Log Filter | url : {}", exchange.getRequest().getURI());
        return chain.filter(exchange).then(Mono.fromRunnable(()-> {
            log.info("Global (post) Log Filter | status : {}", exchange.getResponse().getStatusCode());
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
