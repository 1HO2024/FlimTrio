package com.example.flim.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import com.example.flim.filter.JwtAuthenticationFilter;
import com.example.flim.service.AuthService;
import com.example.flim.util.JwtUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthService authService;
    private final JwtUtil jwtUtil;


    
    public SecurityConfig(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return authService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 보호 비활성화
        http.csrf().disable()
            .authorizeRequests()
                .requestMatchers("/api/v1/signin", 
                		"/api/v1/signup",
                        "/api/v1/movies/topmovie",
                        "/api/v1/movies/topgenre",
                		"/api/v1/search-password",
                        "/api/v1/movies/search",
                		"/api/v1/movies",
                		"/api/v1/fetch-movies",
                		"/api/v1/movie-detail/view-reviews",
                		"/api/v1/similarity").permitAll()  // 로그인, 회원가입은 모두 허용
                .anyRequest().authenticated()  // 그 외의 요청은 인증 필요
            .and()
            .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, authService), UsernamePasswordAuthenticationFilter.class);  // 필터 추가

        return http.build();
    }
    
    // ✅ CORS 설정 추가
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173")); // React 프론트엔드 주소
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true); // ✅ 인증정보 포함 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
