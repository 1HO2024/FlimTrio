package com.example.flim.util;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef"; // 비밀 키

    // JWT 토큰 생성
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1시간 동안 유효
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // JWT 토큰에서 사용자 이름 추출
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰이 만료되었는지 확인
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // 토큰 만료 시간 추출
    public Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    // 토큰이 유효한지 확인
    public boolean isTokenValid(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(SECRET_KEY)  
                                .build()
                                .parseClaimsJws(jwtToken)
                                .getBody();

            // 유효기간 확인 만료 = 예외 발생)
            if (claims.getExpiration().before(new Date())) {
                return false; // 만료된 토큰
            }

            // 토큰 유효 = true 반환
            return true;

        } catch (SignatureException e) {
            // 예외 처리
            return false;
        } catch (Exception e) {
            // 다른 예외 처리 (토큰 형식이 잘못된 경우 등)
            return false;
        }
    }
}
