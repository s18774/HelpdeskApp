package pl.wroblewski.helpdeskapp.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.wroblewski.helpdeskapp.models.User;

import java.util.Map;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(User user) {
        return Jwts.builder()
                .setClaims(Map.of("id", user.getUserId(), "role", user.getRole().getRoleId()))
                //.setSubject("test")
                // .setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public int getUserIdFromToken(String token) {
        return Integer.parseInt(Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getId());
    }
}
