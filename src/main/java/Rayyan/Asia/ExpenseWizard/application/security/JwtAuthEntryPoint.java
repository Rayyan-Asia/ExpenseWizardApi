package Rayyan.Asia.ExpenseWizard.application.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    private final JWTGenerator jwtGenerator;

    public JwtAuthEntryPoint(JWTGenerator jwtGenerator) {
        this.jwtGenerator = jwtGenerator;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if(!jwtGenerator.validateToken(request.getHeader("Authorization").substring(7)))
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
