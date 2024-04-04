package com.skyline.SalesManager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKkey;
    @Value("${application.security.jwt.expriration}")
    private long jwtExpriration;
    @Value("${application.security.jwt.refresh-token.expriration}")
    private long refreshExpriration;

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject); // Get Username
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    } // Lấy ra thông tin cần thiết duy nhat trong chuỗi token được giải mã ở method extractAllClaims

    private Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder() // Khởi tạo 1 builder đẻ xây dưg
                .setSigningKey(getSignInKey()) // Thiết lập khoá
                .build() // xây dựng parser object hoàn chỉnh
                .parseClaimsJws(token) // giaỉ mã token
                .getBody(); // lấy thông tin
    } // Giải mã chuỗi token và chứa thông tin đó vào Claims

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails){
        return buildToken(extractClaims, userDetails, jwtExpriration);
    }

    public String generateRefreshToken(UserDetails userDetails){
        return buildToken(new HashMap<>(), userDetails, refreshExpriration);
    }

    private String buildToken(Map<String, Object> extractClaims, UserDetails userDetails, long expriration){
        return Jwts
                .builder() // Khởi tạo một JWT builder object để xây dựng token mới.
                .setClaims(extractClaims) // Thiết lập các claim bổ sung từ map extraClaims vào token.
                .setSubject(userDetails.getUsername()) // Thiết lập claim "subject" (chủ thể) của token bằng username của người dùng.
                .setIssuedAt(new Date(System.currentTimeMillis())) // thời gian bắt đầu
                .setExpiration(new Date(System.currentTimeMillis() + expriration)) // thời gian kết thúc sau 1 ngày
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // mã hoá token
                .compact(); // Xây dựng token hoàn chỉnh và trả về string
    }

    public boolean isTokenVaild(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date()); // kiểm tra xem ngày hết hạn có trước ngày hiên tại hay không
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    } // lấy ra thời gian hết hạn của token
}
