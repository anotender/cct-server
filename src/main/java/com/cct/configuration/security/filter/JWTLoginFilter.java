package com.cct.configuration.security.filter;

import com.cct.configuration.security.model.AccountCredentials;
import com.cct.configuration.security.service.TokenAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cct.exception.ErrorInfo.UNAUTHORIZED;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private final TokenAuthenticationService tokenAuthenticationService;

    public JWTLoginFilter(TokenAuthenticationService tokenAuthenticationService, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher("/api/login"));

        setAuthenticationManager(authManager);
        setContinueChainBeforeSuccessfulAuthentication(true);

        this.tokenAuthenticationService = tokenAuthenticationService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        AccountCredentials credentials = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        AuthorityUtils.createAuthorityList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        tokenAuthenticationService.addAuthentication(res, auth.getName());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {
        res.setStatus(UNAUTHORIZED.getStatus().value());
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Content-Type", "application/json");
        new ObjectMapper().writeValue(res.getWriter(), UNAUTHORIZED);
    }
}
