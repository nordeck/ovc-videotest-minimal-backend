package net.nordeck.ovc_minimal_backend.configuration;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.nordeck.ovc_minimal_backend.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Configuration
public class JwtConfiguration {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    JwtParser jwtParser() {
        return makeParser(secret);
    }

    @Bean
    Key secretKey() {
        byte[] secretKeyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    @Bean
    JWTUtil jwtUtil(JwtParser jwtParserVideotest) {
        return new JWTUtil(jwtParserVideotest);
    }

    private JwtParser makeParser(String secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .setAllowedClockSkewSeconds(30000)
                .build();
    }
}
