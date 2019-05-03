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

public class StatelessAuthFilter extends GenericFilterBean {

    private final TokenAuthService tokenAuthService;

    public StatelessAuthFilter(@NonNull TokenAuthService tokenAuthService){
        this.tokenAuthService = tokenAuthService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(
                tokenAuthService.getAuthentication((HttpServletRequest) request).orElse(null)
        );
        chain.doFilter(request, response);
    }
}
