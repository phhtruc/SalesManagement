package com.skyline.SalesManager.config;

import com.skyline.SalesManager.service.JwtService;
import com.skyline.SalesManager.repository.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final TokenRepository tokenRepository;
    @SneakyThrows
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader(AUTHORIZATION); // Nhận vào chuỗi token để xác thực
        final String jwt;
        final String userEmail;

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authorizationHeader.substring(7); // Lấy sau khoảng cách của Bearer

        userEmail = jwtService.extractUserName(jwt); // lấy ra được email(username) trong chuỗi token

        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){ // kiểm tra user != null và chưa xác thực

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            var isTokenVaild = tokenRepository.findByToken(jwt)
                    .map(t -> !t.isExpored() && !t.isRevoked())
                    .orElse(false);

            if(jwtService.isTokenVaild(jwt, userDetails) && isTokenVaild){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, // lấy tất cả thông tin của người dùng
                        null, // xác thực bằng jwt nên không cần pass nên để null
                        userDetails.getAuthorities() // lấy danh sách roles của user
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken); // đã được xác thực và lưu vào context holder
            }
        }
        filterChain.doFilter(request, response); // sau khi xác thực thành công thì cho đến các request khác nhau
    }
}
