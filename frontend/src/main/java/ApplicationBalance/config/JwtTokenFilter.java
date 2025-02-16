// JwtTokenFilter.java
package ApplicationBalance.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtTokenFilter implements Filter {

    private final JwtService jwtService;

    public JwtTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;


        String path = ((HttpServletRequest) request).getRequestURI();
        String[] theallows = {"/auth/login", "auth/register", "/auth/test"};


        // Se for login ou registro, permite o acesso sem token
        if (Arrays.stream(theallows).anyMatch(path::startsWith)) {
            // Permite o acesso sem token
            chain.doFilter(request, response);
            return;
        }

        String authorizationHeader = ((HttpServletRequest) request).getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // Set response status
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            // Set response content type
            httpResponse.setContentType("application/json");

            // Create JSON error message
            String jsonResponse = "{\"error\": \"Missing or invalid Authorization header.\"}";

            // Write JSON to response body
            httpResponse.getWriter().write(jsonResponse);
            return;
        }

        String token = authorizationHeader.replace("Bearer ", "").trim();  // Remove "Bearer " and trim spaces


        try {
            // Parse the token and get the claims object
            Claims claims = jwtService.parseClaimsJws(token);

            // Extract the username and role from the claims
            String username = claims.getSubject(); // Get the username (subject)
            String role = (String) claims.get("role"); // Get the role

            // Create authorities for the user based on the role
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role)); // Add role as authority

            // Create authentication object
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

            // Set the authentication in the SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Continue the filter chain if everything is good
            chain.doFilter(request, response);

        } catch (JwtException e) {
            // If token is invalid, send error response
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token. Please provide a valid token.");
            return;
        }
    }

}
