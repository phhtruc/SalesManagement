package com.skyline.SalesManager.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String Secret_key = "89dd3a965fddb1460b64c074c928ee19f6697a168bc5ec72c336fb6df64289c4";
    public String extractUserName(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractClaim(token, Claims::getSubject); // Get Username
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    } // Lấy ra thông tin cần thiết duy nhat trong chuỗi token được giải mã ở method extractAllClaims

    private Claims extractAllClaims(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts
                .parserBuilder() // Khởi tạo 1 builder đẻ xây dưg
                .setSigningKey(getSignInKey()) // Thiết lập khoá
                .build() // xây dựng parser object hoàn chỉnh
                .parseClaimsJws(token) // giaỉ mã token
                .getBody(); // lấy thông tin
    } // Giải mã chuỗi token và chứa thông tin đó vào Claims

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(Secret_key);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extractClaims, UserDetails userDetails) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return Jwts
                .builder() // Khởi tạo một JWT builder object để xây dựng token mới.
                .setClaims(extractClaims) // Thiết lập các claim bổ sung từ map extraClaims vào token.
                .setSubject(userDetails.getUsername()) // Thiết lập claim "subject" (chủ thể) của token bằng username của người dùng.
                .setIssuedAt(new Date(System.currentTimeMillis())) // thời gian bắt đầu
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // thời gian kết thúc sau 1 ngày
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // mã hoá token
                .compact(); // Xây dựng token hoàn chỉnh và trả về string
    }

    public boolean isTokenVaild(String token, UserDetails userDetails) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractExpiration(token).before(new Date()); // kiểm tra xem ngày hết hạn có trước ngày hiên tại hay không
    }

    private Date extractExpiration(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return extractClaim(token, Claims::getExpiration);
    } // lấy ra thời gian hết hạn của token
}
