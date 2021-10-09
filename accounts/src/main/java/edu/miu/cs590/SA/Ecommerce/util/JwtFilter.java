package edu.miu.cs590.SA.Ecommerce.util;

import edu.miu.cs590.SA.Ecommerce.domain.Account;
import edu.miu.cs590.SA.Ecommerce.repository.AccountRepository;
import edu.miu.cs590.SA.Ecommerce.service.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Optional;

@Service
public class JwtFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;
    private MyUserDetailsServiceImpl userDetailsService;
    private AccountRepository accountRepository;

    @Autowired
    public JwtFilter(JwtUtil jwtUtil, MyUserDetailsServiceImpl userDetailsService, AccountRepository accountRepository){
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        Long userId = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            userId = Long.valueOf(jwtUtil.extractUserId(jwt));
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Account> account = accountRepository.getAccountById(userId);
            String username = account.get().getUsername();
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}

