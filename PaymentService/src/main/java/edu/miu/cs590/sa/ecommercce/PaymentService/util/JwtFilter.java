package edu.miu.cs590.sa.ecommercce.PaymentService.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Service
public class JwtFilter extends OncePerRequestFilter {

    private final String secret;

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";

    @Autowired
    public JwtFilter(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(AUTH_HEADER);

        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        if (JwtUtil.isTokenValid(token, secret) && JwtUtil.extractUsername(token, secret) != null) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(JwtUtil.extractUsername(token, secret), UUID.randomUUID());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }

}