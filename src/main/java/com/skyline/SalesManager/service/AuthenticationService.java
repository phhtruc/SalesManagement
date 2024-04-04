package com.skyline.SalesManager.service;

import com.skyline.SalesManager.auth.AuthenticationRequest;
import com.skyline.SalesManager.auth.AuthenticationResponse;
import com.skyline.SalesManager.entity.RoleEntity;
import com.skyline.SalesManager.entity.UserEntity;
import com.skyline.SalesManager.repository.RoleRepository;
import com.skyline.SalesManager.repository.UserRepository;
import com.skyline.SalesManager.token.Token;
import com.skyline.SalesManager.token.TokenRepository;
import com.skyline.SalesManager.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

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

        RoleEntity userRole = roleRepository.findByCode("USER").orElseThrow(() -> new UsernameNotFoundException("role not found"));

        List<RoleEntity> roles = new ArrayList<>();
        roles.add(userRole);

        var user = UserEntity.builder()
                .fullName(userEntity.getFullName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .password(passwordEncoder.encode(userEntity.getPassword()))
                .status(1)
                .roles(roles)
                .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
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
        revokeAllUserTokens(user);
        saveUserToken(user, JwtToken);
        return AuthenticationResponse.builder()
                .token(JwtToken)
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
        var validUserToken = tokenRepository.findAllValidTokenByUser(user.getId_user());
        if(validUserToken.isEmpty())
            return;
        validUserToken.forEach(t -> {
            t.setRevoked(true);
            t.setExpored(true);
        });
        tokenRepository.saveAll(validUserToken);
    }
}
