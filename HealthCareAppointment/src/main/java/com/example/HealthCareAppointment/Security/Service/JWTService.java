package com.example.HealthCareAppointment.Security.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.HealthCareAppointment.Security.User.UserPrincipal;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Component
public class JWTService {
    private final String jwtSecret;
    private final int expiration = 3600000;
    
    public JWTService() throws NoSuchAlgorithmException {
        KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
        SecretKey sk = keyGen.generateKey();
        this.jwtSecret = Base64.getEncoder().encodeToString(sk.getEncoded());
    }

    // tạo token cho mỗi lần đăng nhập cho patient 
    public String generateToken(Authentication authentication) {
        // token sẽ dựa vào thông tin của user
        

        Map<String, Object> claims = new HashMap<>();

        // get user information from authentication
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        // it have subject, issued at, expiration,
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + expiration))
                .and()
                .signWith(key(), Jwts.SIG.HS256)  // Thay SignatureAlgorithm bằng Jwts.SIG
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from token: lấy username từ token đã tạo
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith( key() ).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }

    // validate token 
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
            return true;
        }
        catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | io.jsonwebtoken.security.SecurityException | IllegalArgumentException e) {
            throw new JwtException(e.getMessage());

        }
    }

}
