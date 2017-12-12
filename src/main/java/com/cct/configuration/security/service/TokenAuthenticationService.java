package com.cct.configuration.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class TokenAuthenticationService {
    private final long expirationTime;
    private final String secret;
    private final String prefix;
    private final String header;

    public TokenAuthenticationService(
            @Value("#{new Long('${security.token.expiration-time}')}") Long expirationTime,
            @Value("${security.token.secret}") String secret
    ) {
        this.expirationTime = expirationTime;
        this.secret = secret;
        this.prefix = "Bearer";
        this.header = "Authorization";
    }

    public void addAuthentication(HttpServletResponse res, String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        res.addHeader(header, prefix + " " + token);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(header);
        String user = null;
        if (token != null) {
            user = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token.replace(prefix, ""))
                    .getBody()
                    .getSubject();
        }
        return user != null ?
                new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList()) :
                null;
    }
}
