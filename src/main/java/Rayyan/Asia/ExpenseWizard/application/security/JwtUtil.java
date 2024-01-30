package Rayyan.Asia.ExpenseWizard.application.security;
import io.jsonwebtoken.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "RayyanIsTheGoat";
    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    public static String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);

        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public static String extractUsername(String token) {
        return extractClaims(token).getBody().getSubject();
    }

    public static boolean isTokenExpired(String token) {
        Date expirationDate = extractClaims(token).getBody().getExpiration();
        return expirationDate.before(new Date());
    }

    private static Jws<Claims> extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
    }
}
