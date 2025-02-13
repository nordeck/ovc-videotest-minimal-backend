package net.nordeck.ovc_minimal_backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class JWTUtil {

    private final JwtParser jwtParser;

    public JWTUtil(JwtParser jwtParser) {
        this.jwtParser = jwtParser;
    }

    public Claims getAllClaimsFromToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public static Map<String, Object> userClaimResolver(Claims claims) {
        return Optional.ofNullable(claims.get("context", Map.class) )
                .map(c -> (Map<String, Object>)(c.get("user")))
                .orElse(Collections.emptyMap());
    }
}
