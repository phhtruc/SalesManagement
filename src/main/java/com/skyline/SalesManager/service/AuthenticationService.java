package com.skyline.SalesManager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyline.SalesManager.auth.AuthenticationRequest;
import com.skyline.SalesManager.auth.AuthenticationResponse;
import com.skyline.SalesManager.entity.RoleEntity;
import com.skyline.SalesManager.entity.UserEntity;
import com.skyline.SalesManager.enums.CodeRole;
import com.skyline.SalesManager.enums.UserStatus;
import com.skyline.SalesManager.repository.RoleRepository;
import com.skyline.SalesManager.repository.UserRepository;
import com.skyline.SalesManager.entity.Token;
import com.skyline.SalesManager.repository.TokenRepository;
import com.skyline.SalesManager.enums.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository  tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;
    public AuthenticationResponse register(UserEntity userEntity) throws NoSuchAlgorithmException, InvalidKeySpecException {

        RoleEntity userRole = roleRepository.findByCodeRole(CodeRole.USER).orElseThrow(() -> new UsernameNotFoundException("role not found"));

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(userRole);

        var user = UserEntity.builder()
                .fullName(userEntity.getFullName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .password(passwordEncoder.encode(userEntity.getPassword()))
                .userStatus(UserStatus.ACTIVE)
                .roles(roles)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var JwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, JwtToken);
        return AuthenticationResponse.builder()
                .tokenType(TokenType.BEARER)
                .id(user.getIdUser())
                .username(user.getUsername())
                .roles(user.getRole())
                .message("Login success")
                .accessToken(JwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        var token = Token.builder()
                .userEntity(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expored(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(UserEntity user){
        var validUserToken = tokenRepository.findAllValidTokenByUser(user.getIdUser());
        if(validUserToken.isEmpty())
            return;
        validUserToken.forEach(t -> {
            t.setRevoked(true);
            t.setExpored(true);
        });
        tokenRepository.saveAll(validUserToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String authorizationHeader = request.getHeader(AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")){
            return;
        }

        refreshToken = authorizationHeader.substring(7);

        userEmail = jwtService.extractUserName(refreshToken);

        if(userEmail != null){
            var userDetails = this.userRepository.findByEmail(userEmail).orElseThrow();

            if(jwtService.isTokenVaild(refreshToken, userDetails)){
                var accessToken = jwtService.generateToken(userDetails);
                revokeAllUserTokens(userDetails);
                saveUserToken(userDetails, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse); // Chuyen thanh chuoi json
            }
        }
    }
}
