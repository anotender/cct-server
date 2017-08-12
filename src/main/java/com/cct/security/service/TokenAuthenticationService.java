package com.cct.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class TokenAuthenticationService {
    private final long expirationTime = 864_000_000; // 10 days
    private final String secret = "ThisIsASecret";
    private final String prefix = "Bearer";
    private final String header = "Authorization";

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
