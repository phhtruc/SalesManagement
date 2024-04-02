package com.skyline.SalesManager.service;

import com.skyline.SalesManager.auth.AuthenticationRequest;
import com.skyline.SalesManager.auth.AuthenticationResponse;
import com.skyline.SalesManager.entity.RoleEntity;
import com.skyline.SalesManager.entity.UserEntity;
import com.skyline.SalesManager.repository.RoleRepository;
import com.skyline.SalesManager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
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
        userRepository.save(user);
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
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
        //var authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toList());
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
