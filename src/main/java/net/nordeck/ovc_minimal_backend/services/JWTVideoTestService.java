package net.nordeck.ovc_minimal_backend.services;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import net.nordeck.ovc_minimal_backend.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JWTVideoTestService {

    @Value("${jwt.expirationMinutes}")
    private Long expirationMinutes;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.audience}")
    private String audience;

    @Value("${jwt.subject}")
    private String subject;

    @Value("${jwt.defaultUserName}")
    private  String defaultUsername;

    private final Key secretKey;

    public JWTVideoTestService(Key secretKey) {
        this.secretKey = secretKey;
    }

    public String makeJWT(String room) {
        return makeJWT(room, defaultUsername);
    }

    public String makeJWT(String room, String userName) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date exp = new Date(nowMillis + 1000*60*expirationMinutes);

        Map<String, Object> context = new HashMap<>();
        Map<String, Object> user = new HashMap<>();
        user.put("name", userName);
        user.put("affiliation", "owner");
        user.put("lobby_bypass", true);
        context.put("user", user);

        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setId(null)
                .setIssuedAt(now)
                .setExpiration(exp)
                .setNotBefore(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .setAudience(audience)
                .claim("context", context)
                .claim("room", room)
                .signWith(secretKey, signatureAlgorithm);

        return builder.compact();
    }
}
