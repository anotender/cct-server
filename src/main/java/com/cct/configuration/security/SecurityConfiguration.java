package com.cct.configuration.security;

import com.cct.configuration.security.filter.JWTAuthenticationFilter;
import com.cct.configuration.security.filter.JWTLoginFilter;
import com.cct.configuration.security.service.TokenAuthenticationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import static com.cct.exception.ErrorInfo.UNAUTHORIZED;
import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final TokenAuthenticationService tokenAuthenticationService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfiguration(
            JWTAuthenticationFilter jwtAuthenticationFilter,
            TokenAuthenticationService tokenAuthenticationService,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable();

        http
                .authorizeRequests()
                .requestMatchers(CorsUtils::isCorsRequest, CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(POST, "/api/login", "/api/users").permitAll()
                .anyRequest().fullyAuthenticated();

        http
                .addFilterBefore(new JWTLoginFilter(tokenAuthenticationService, authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .exceptionHandling()
                .authenticationEntryPoint((req, resp, e) -> {
                    resp.setStatus(UNAUTHORIZED.getStatus().value());
                    resp.getWriter().print(UNAUTHORIZED.getMessage());
                });
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

}
