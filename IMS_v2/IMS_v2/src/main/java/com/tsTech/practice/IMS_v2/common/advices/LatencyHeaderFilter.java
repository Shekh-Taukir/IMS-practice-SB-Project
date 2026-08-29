package com.tsTech.practice.IMS_v2.common.advices;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
@Order(Integer.MIN_VALUE)
public class LatencyHeaderFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(LatencyHeaderFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //get the start time in nano seconds for precision
        long startTime = System.nanoTime();
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(request, responseWrapper);
        } finally {
            String latencyHeaderName = "X-Response-Time-Millis";
            long durationMs = (System.nanoTime() - startTime)/ 1_000_000; //converts the duration to milliseconds
            log.debug("response committed here as well: {} | does header exists : {}", response.isCommitted(), response.containsHeader(latencyHeaderName));

            if(!response.containsHeader(latencyHeaderName))
                response.addHeader(latencyHeaderName, String.valueOf(durationMs));  //add the duration in header for each response

            responseWrapper.copyBodyToResponse();
            //log the duration and method as well (which will be further used in monitoring tools like (Splunk, ELK, Datadog)
            log.info("METHOD = {} | PATH = {} | STATUS = {} | DURATION = {}ms",
                    request.getMethod(),
                    request.getRequestURL(),
                    response.getStatus(),
                    durationMs
            );
        }
    }
}
