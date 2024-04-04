package com.skyline.SalesManager.service;

import com.skyline.SalesManager.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authorizationHeader = request.getHeader(AUTHORIZATION); // Nhận vào chuỗi token để xác thực
        final String jwt;

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            return;
        }

        jwt = authorizationHeader.substring(7); // Lấy sau khoảng cách của Bearer

        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        if(storedToken != null){
            storedToken.setExpored(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }
    }
}
