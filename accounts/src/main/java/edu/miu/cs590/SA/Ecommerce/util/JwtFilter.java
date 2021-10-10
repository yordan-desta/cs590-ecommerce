package edu.miu.cs590.SA.Ecommerce.util;

import edu.miu.cs590.SA.Ecommerce.domain.Account;
import edu.miu.cs590.SA.Ecommerce.repository.AccountRepository;
import edu.miu.cs590.SA.Ecommerce.service.MyUserDetailsServiceImpl;
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
import java.util.Optional;

@Service
public class JwtFilter extends OncePerRequestFilter {

    private AccountRepository accountRepository;

    private final String secret;

    private final MyUserDetailsServiceImpl userDetailsService;

    @Autowired
    public JwtFilter(@Value("${jwt.secret}") String secret, MyUserDetailsServiceImpl userDetailsService,
                     AccountRepository accountRepository){
        this.userDetailsService = userDetailsService;
        this.secret = secret;
        this.accountRepository = accountRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            chain.doFilter(request, response);
            return;
        }

        final String jwt = authorizationHeader.substring(7);


        if (JwtUtil.isTokenValid(jwt, secret) && JwtUtil.extractUserId(jwt, secret) != null) {
            final String userId = JwtUtil.extractUserId(jwt, secret);

            Optional<Account> account = accountRepository.getAccountById(Long.valueOf(userId));
            String username = account.get().getUsername();

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        chain.doFilter(request, response);
    }
}

