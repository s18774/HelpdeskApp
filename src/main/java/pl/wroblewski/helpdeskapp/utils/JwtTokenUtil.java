package pl.wroblewski.helpdeskapp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.wroblewski.helpdeskapp.models.User;

import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user) {
        return Jwts.builder()
                .setClaims(Map.of(
                        "id", user.getUserId(),
                        "role", user.getRole().getRoleId(),
                        "username", user.getLogin()))
                //.setSubject("test")
                // .setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public int getUserIdFromToken(String token) {
        return getTokenClaims(token)
                .get("id", Integer.class);
    }

    public String getUsernameFromToken(String token) {
        return getTokenClaims(token)
                .get("username", String.class);
    }

    public Date getExpirationFromToken(String token) {
        return getTokenClaims(token)
                .getExpiration();
    }

    public boolean isValidToken(String token) {
        return getExpirationFromToken(token).after(new Date());
    }

    private Claims getTokenClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}