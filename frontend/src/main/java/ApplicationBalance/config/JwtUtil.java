package ApplicationBalance.config;
import ApplicationBalance.entities.User;
import ApplicationBalance.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JwtUtil {


    @Value("${jwt.secret-key}")
    private String getSecretKey;

    @Autowired
    private UserRepository userRepository;


    // Método que cria o token com base no nome de usuário e role
    public String generateToken(String name) {
        // Defina a data de expiração do token
        long expirationTime = 1000 * 60 * 60 * 10;  // 10 horas

        // Verifique se o usuário existe
        User user = userRepository.findByname(name);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Obtém o role do usuário
        String role = user.getRole().getName();

        // Gera o token JWT

        return
                Jwts.builder()
                .setSubject(name)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .claim("role", role) // ✅ Adds role to the token
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public SecretKey getSigningKey() {
        byte[] keyBytes = getSecretKey.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("Secret key must be at least 32 bytes long for HS256.");
        }

        return Keys.hmacShaKeyFor(keyBytes);
    }


}