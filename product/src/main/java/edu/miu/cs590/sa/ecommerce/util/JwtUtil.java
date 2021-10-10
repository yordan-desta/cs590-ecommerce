package edu.miu.cs590.sa.ecommerce.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.function.Function;

import io.jsonwebtoken.JwtException;

public class JwtUtil {

    public static String extractUserId(String token, String secret) {
        return extractClaim(token, Claims::getSubject, secret);
    }

    public static Date extractExpiration(String token, String secret) {
        return extractClaim(token, Claims::getExpiration, secret);
    }

    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver, String secret) {
        return claimsResolver.apply(extractAllClaims(token, secret));
    }

    private static Claims extractAllClaims(String token, String secret) {
        return  Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private static Boolean isTokenExpired(String token, String secret) {
        return extractExpiration(token, secret).before(new Date());
    }

    private static Boolean isTokenTrustable(String token, String secret) {
        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    public static Boolean isTokenValid(String token, String secret){
        return isTokenTrustable(token, secret) && !isTokenExpired(token, secret);
    }
}