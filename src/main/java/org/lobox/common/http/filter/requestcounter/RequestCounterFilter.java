package org.lobox.common.http.filter.requestcounter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RequestCounterFilter extends OncePerRequestFilter {

    private final RequestCounter requestCounter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        requestCounter.countRequest();
        log.debug("{} requests processed!", requestCounter.getRequestCount());

        filterChain.doFilter(request, response);
    }

}
