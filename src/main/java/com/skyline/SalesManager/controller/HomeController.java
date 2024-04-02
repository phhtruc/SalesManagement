package com.skyline.SalesManager.controller;

import com.skyline.SalesManager.entity.UserEntity;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/home")
public class HomeController {

    @GetMapping
    @Secured("ADMIN")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("hello");
    }

}
