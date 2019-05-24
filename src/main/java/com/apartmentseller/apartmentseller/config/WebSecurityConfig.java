package com.apartmentseller.apartmentseller.config;

import com.apartmentseller.apartmentseller.config.property.TokenHandlerProperty;
import com.apartmentseller.apartmentseller.filters.JwtLoginFilter;
import com.apartmentseller.apartmentseller.filters.StatelessAuthFilter;
import com.apartmentseller.apartmentseller.services.TokenAuthService;
import com.apartmentseller.apartmentseller.services.security.SecurityUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(TokenHandlerProperty.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SecurityUserService userService;

    private final TokenAuthService tokenAuthService;

    @Value("${auth.header.name}")
    private String authHeaderName;

    @Autowired
    public WebSecurityConfig(SecurityUserService userService, TokenAuthService tokenAuthService){
        this.userService = userService;
        this.tokenAuthService = tokenAuthService;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/announcement").authenticated()
                .antMatchers(HttpMethod.PUT, "/announcement/*").authenticated()
                .antMatchers(HttpMethod.DELETE, "/announcement/*").authenticated()
                .antMatchers("/", "/announcement/**", "/js/**", "/login", "/sign-up", "/activate/*").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .logout()
                    .logoutSuccessUrl("/").permitAll()
                .and()
                    .csrf().disable()
                .addFilterBefore(new JwtLoginFilter(authenticationManager(), tokenAuthService, authHeaderName),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new StatelessAuthFilter(tokenAuthService, authHeaderName),
                        UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
