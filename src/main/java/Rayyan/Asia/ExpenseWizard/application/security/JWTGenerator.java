package Rayyan.Asia.ExpenseWizard.application.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class JWTGenerator {
    private final String JWT_SECRET = "RAYYAN IS THE GOAT";
    public String generateToken(Authentication authentication){
        var email = authentication.getName();
        var token = Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        return token;
    }

    public String getEmailFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token);
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
