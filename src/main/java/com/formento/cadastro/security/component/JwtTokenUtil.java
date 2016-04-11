package com.formento.cadastro.security.component;

import com.formento.cadastro.exception.AccessDeniedCadastroExceptionDefault;
import com.formento.cadastro.exception.UnauthorizedCadastroExceptionDefault;
import com.formento.cadastro.security.JwtUser;
import com.formento.cadastro.security.UsuarioAuthentication;
import com.formento.cadastro.util.LocalDateTimeUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_AUDIENCE = "audience";
    private static final String CLAIM_KEY_CREATED = "created";

    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String getEmailFromToken(String token) {
        String email;
        try {
            final Claims claims = getClaimsFromToken(token);
            email = claims.getSubject();
        } catch (AccessDeniedCadastroExceptionDefault e) {
            email = null;
            throw e;
        } catch (Exception e) {
            email = null;
        }
        return email;
    }

    public LocalDateTime getCreatedDateFromToken(String token) {
        LocalDateTime created;
        try {
            final Claims claims = getClaimsFromToken(token);
            created = LocalDateTimeUtil.fromLong((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public LocalDateTime getExpirationDateFromToken(String token) {
        LocalDateTime expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = LocalDateTimeUtil.fromDate(claims.getExpiration());
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = getClaimsFromToken(token);
            audience = (String) claims.get(CLAIM_KEY_AUDIENCE);
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = null;
            throw new AccessDeniedCadastroExceptionDefault("Sessão inválida");
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + (expiration * 1000));
    }

    private Boolean isTokenExpired(String token) {
        final LocalDateTime expiration = getExpirationDateFromToken(token);
        return expiration.isBefore(LocalDateTime.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(LocalDateTime created, LocalDateTime ultimoLogin) {
        return (ultimoLogin != null && created.isBefore(ultimoLogin.minusSeconds(1)));
    }

    public String generateToken(UsuarioAuthentication usuarioAuthentication) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, usuarioAuthentication.getEmail());
        claims.put(CLAIM_KEY_CREATED, LocalDateTimeUtil.toLong(LocalDateTime.now()));
        return generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUser user = (JwtUser) userDetails;
        final String email = getEmailFromToken(token);
        final LocalDateTime created = getCreatedDateFromToken(token);
//        final LocalDateTime expiration = getExpirationDateFromToken(token);

        if (!email.equals(user.getUsername())) {
            return false;
        }

        if (isTokenExpired(token)) {
            throw new UnauthorizedCadastroExceptionDefault("Sessão inválida");
        }

        return true;

//        return email.equals(user.getUsername()) &&
//                !isTokenExpired(token) //&&
//                //!isCreatedBeforeLastPasswordReset(created, user.getUltimoLogin())
//                ;
    }

}
