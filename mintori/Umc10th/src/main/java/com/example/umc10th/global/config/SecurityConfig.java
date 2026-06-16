package com.example.umc10th.global.config;

import com.example.umc10th.global.jwt.JwtAuthenticationFilter;
import com.example.umc10th.global.jwt.JwtTokenProvider;
import com.example.umc10th.global.security.CustomAccessDeniedHandler;
import com.example.umc10th.global.security.CustomAuthenticationEntryPoint;
import com.example.umc10th.global.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    private static final String[] PUBLIC_ENDPOINTS = {
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/error",
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCrypt: 해시할 때마다 salt 를 자동 생성해 해시 문자열에 함께 저장
        return new BCryptPasswordEncoder();
    }

    /**
     * 로그인(이메일/비밀번호) 인증을 처리하는 AuthenticationManager.
     * - CustomUserDetailsService 로 사용자를 로드하고 PasswordEncoder 로 비밀번호를 비교
     */
    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // REST API 이므로 CSRF / 폼 로그인 / HTTP Basic 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                // JWT 기반이므로 세션을 사용하지 않는 STATELESS 정책
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 인가 설정: 회원가입 / 로그인만 Public, 그 외 모든 API 는 Private
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        // 회원가입 API (Public)
                        .requestMatchers(HttpMethod.POST, "/api/users/signup").permitAll()
                        // 로그인 API (Public)
                        .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                        // 그 외 모든 요청은 인증 필요 (Private)
                        .anyRequest().authenticated()
                )
                // 인증/인가 실패 시 통일된 응답 형식으로 반환
                .exceptionHandling(handler -> handler
                        // 인증 실패 (미인증 사용자) -> 401
                        .authenticationEntryPoint(authenticationEntryPoint)
                        // 인가 실패 (권한 없음) -> 403
                        .accessDeniedHandler(accessDeniedHandler)
                )
                // UsernamePasswordAuthenticationFilter 앞에 JWT 인증 필터 추가
                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService),
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}
