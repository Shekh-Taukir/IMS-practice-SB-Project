package com.practiceProject.Ims_Project.security;

import com.practiceProject.Ims_Project.entity.auth.SystemUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

////////////////////////////////////////////////
//
// Name: AuthUtil class
//
// Description:
//
// Version history:
// v1.1 || type : Change || Mar 29, 2026 || TaukirS (ER 1104 - Jwt Authentication integration changes)
////////////////////////////////////////////////

@Component
@Slf4j
public class AuthUtil {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    /// function is used , Security Chain filter process, when we get username based on the token provided, to check further that its registered in
    /// Security Context manager or not
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        /// returns the username that is added at time of creating this jwt token
        return claims.getSubject();
    }

    /// generates Access token, because its used when user is authenticated at time of login, so once authenticated, will return the jwt token, which will
    /// be checked further for accessing authenticated endpoints
    public String generateAccessToken(SystemUser user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId",user.getId().toString())
                .issuedAt(new Date())
                ///expiration added is of 10 mins, because as it takes milliseconds as input
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getSecretKey())
                .compact();
    }
}
