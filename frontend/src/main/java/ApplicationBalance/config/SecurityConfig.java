package ApplicationBalance.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {


    private final JwtTokenFilter jwtTokenFilter;


    public SecurityConfig(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF for simplicity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("auth/register").permitAll()  // Public routes
                        .requestMatchers("auth/login").permitAll()
                        .requestMatchers("auth/test").permitAll()
                        .requestMatchers("auth/listUser").hasRole("ADMIN")  // Only ADMIN can access /admin/**
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Use JWT for stateless sessions
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);  // Add JwtTokenFilter before UsernamePasswordAuthenticationFilter
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
