package com.skyline.SalesManager.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationProvider authenticationProvider;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable) // Tắt chức năng bảo vệ CSRF
                .authorizeHttpRequests(authorize -> authorize  // cấu hình ủy quyền cho các request HTTP
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .anyRequest().authenticated()) // Adjust code style settings
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // không lưu trữ  session  trên server
                .authenticationProvider(authenticationProvider) // xử lý xác thực người dùng
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // jwtAuthenticationFilter sẽ được thực hiện trước để xử lý việc xác thực token JWT trước khi đến các filter xử lý login/password mặc định
                .build();
    }
}
