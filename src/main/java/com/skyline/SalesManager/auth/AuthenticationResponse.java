package com.skyline.SalesManager.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skyline.SalesManager.enum_token.TokenType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private long id;

    private String username;

    private List<String> roles;

    private String message;

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refreshToken")
    private String refreshToken;



    //@JsonProperty de chuyen doi ten cua no tu java sang json
}