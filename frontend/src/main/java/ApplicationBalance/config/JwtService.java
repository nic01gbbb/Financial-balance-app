// JwtService.java
package ApplicationBalance.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {


    @Value("${jwt.secret-key}")
    private String getSecretKey;


    public SecretKey getSigningKey() {
        byte[] keyBytes = getSecretKey.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("Secret key must be at least 32 bytes long for HS256.");
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims parseClaimsJws(String token) {
        try {
            // Parse the JWT token to get claims
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            // Token expired
            throw new JwtException("Token expirado: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            // Unsupported token
            throw new JwtException("Token não suportado: " + e.getMessage());
        } catch (MalformedJwtException e) {
            // Malformed token
            throw new JwtException("Token malformado: " + e.getMessage());
        } catch (SignatureException e) {
            // Signature error (wrong signing key)
            throw new JwtException("Erro na assinatura: " + e.getMessage());
        } catch (Exception e) {
            // Generic error when processing the token
            throw new JwtException("Erro ao processar o token: " + e.getMessage());
        }
    }




}



