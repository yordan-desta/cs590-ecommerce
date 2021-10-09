package edu.miu.cs590.SA.Ecommerce.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class TokenExtractor {
    public static String extractToken(){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal != null && !principal.toString().isEmpty() && principal.toString().split(":").length > 1)
                return principal.toString().split(":")[1];
        return null;
    }
}
