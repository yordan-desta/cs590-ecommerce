package edu.miu.cs590.SA.Ecommerce.util;

import edu.miu.cs590.SA.Ecommerce.service.UserDetailsServiceImpl;
import io.jsonwebtoken.SignatureException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {

    private final String secret;

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtFilter(@Value("${jwt.secret}") String secret, UserDetailsServiceImpl userDetailsService){
        this.userDetailsService = userDetailsService;
        this.secret = secret;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, SignatureException {

        final String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            chain.doFilter(request, response);
            return;
        }

        final String token = authorizationHeader.substring(7);

        if(JwtUtil.isTokenValid(token, secret) && JwtUtil.extractUsername(token, secret) != null){
            final String userId = JwtUtil.extractUsername(token, secret);

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userId+":"+token, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        chain.doFilter(request, response);
    }

}
