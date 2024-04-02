package com.skyline.SalesManager.controller;

import com.skyline.SalesManager.auth.AuthenticationRequest;
import com.skyline.SalesManager.auth.AuthenticationResponse;
import com.skyline.SalesManager.entity.UserEntity;
import com.skyline.SalesManager.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserEntity userEntity) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return ResponseEntity.ok(authenticationService.register(userEntity));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}
