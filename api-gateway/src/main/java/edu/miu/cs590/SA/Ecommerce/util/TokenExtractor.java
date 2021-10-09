package edu.miu.cs590.SA.Ecommerce.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class TokenExtractor {
    public static String extractToken(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = principal.toString().split(":")[1];
        return token;
    }
}
