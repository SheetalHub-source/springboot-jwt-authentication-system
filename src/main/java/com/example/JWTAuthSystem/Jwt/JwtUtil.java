package com.example.JWTAuthSystem.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY_STRING = "z4tOJN1PxvXdkRZcsh18kcRAkF7oz2yxG6eOBn8g0Ig=";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(java.util.Base64.getDecoder().decode(SECRET_KEY_STRING));
    private final long expiration = 3600000;//300000; // 1 hour

    public String generateToken(String username, String role, Boolean isActive) {
        Map<String, String> claims = new HashMap<>();
        claims.put("role", role);
        if (isActive != null) claims.put("isActive", isActive.toString());
        var jwtBuilder = Jwts.builder()
                .setSubject(username)
                .header().empty().add("typ", "JWT")
                .and()
                .claims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256);

        if ("USER".equals(role)) {
            jwtBuilder.claim("isActive", isActive);
        }

        return jwtBuilder.compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        try {
            Date expirationDate = extractAllClaims(token).getExpiration();
            return expirationDate.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
