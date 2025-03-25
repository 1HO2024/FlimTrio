package com.example.flim.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.flim.service.AuthService;
import com.example.flim.util.JwtUtil;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthService authService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
			throws jakarta.servlet.ServletException, IOException{
        // 요청에서 JWT 토큰 추출
        String jwtToken = extractJwtFromRequest(request);

        // 토큰이 유효하고, 인증된 사용자가 있으면
        if (jwtToken != null && jwtUtil.validateToken(jwtToken)) {
            String username = jwtUtil.extractUsername(jwtToken);  // 토큰에서 이메일 가져옴
            var userDetails = authService.loadUserByUsername(username);  // 사용자 정보

            // 사용자 인증 객체 
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // 인증 정보를 SecurityContext에 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 요청을 필터 체인에 전달하여 다른 필터들이 처리할 수 있도록 함
        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(jakarta.servlet.http.HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // "Authorization" 헤더에서 "Bearer "로 시작하는 JWT 토큰 추출
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);  //토큰 값 반환
        }

        return null;
    }

}
