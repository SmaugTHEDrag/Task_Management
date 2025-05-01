package com.example.TaskManagementSystem.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;

import java.util.Date;

public class JwtUtils {
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000L;
    private static final String KEY = "TaskManagement";

    public static String generateJwt(Authentication authentication) {
        org.springframework.security.core.userdetails.User user = (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(new Date().getTime() + EXPIRE_TIME))
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }

    public static boolean validateJwt(String jwt) {
        try{
            Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt);
            return true;
        } catch (Exception exception){
            exception.printStackTrace();
        }
        return false;
    }

    public static String getUsername(String jwt) {
        return Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt).getBody().getSubject();
    }
}
