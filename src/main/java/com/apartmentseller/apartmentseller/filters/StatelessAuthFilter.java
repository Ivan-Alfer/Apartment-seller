package com.apartmentseller.apartmentseller.filters;

import com.apartmentseller.apartmentseller.services.TokenAuthService;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

public class StatelessAuthFilter extends GenericFilterBean {

    private final TokenAuthService tokenAuthService;

    private final String authHeaderName;

    public StatelessAuthFilter(@NonNull TokenAuthService tokenAuthService, String authHeaderName) {
        this.tokenAuthService = tokenAuthService;
        this.authHeaderName = authHeaderName;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (Objects.isNull(request)) {
            return;
        }
        setSecurityAuthentication((HttpServletRequest) request);
        chain.doFilter(request, response);
    }

    private void setSecurityAuthentication(HttpServletRequest request) {
        String header = request.getHeader(authHeaderName);
        SecurityContextHolder.getContext().setAuthentication(
                tokenAuthService.getAuthentication(header).orElse(null)
        );
    }
}
