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
import java.util.Optional;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public Optional<String> getEmailFromToken(String token) {
        try {
            final Optional<Claims> claims = getClaimsFromToken(token);
            return claims.isPresent() ? Optional.ofNullable(claims.get().getSubject()) : Optional.empty();
        } catch (AccessDeniedCadastroExceptionDefault e) {
            throw e;
        } catch (Exception e) {
            return null;
        }
    }

    public Optional<LocalDateTime> getCreatedDateFromToken(String token) {
        try {
            final Optional<Claims> claims = getClaimsFromToken(token);
            return claims.isPresent() ? Optional.ofNullable(LocalDateTimeUtil.fromLong((Long) claims.get().get(CLAIM_KEY_CREATED))) : Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<LocalDateTime> getExpirationDateFromToken(String token) {
        try {
            final Optional<Claims> claims = getClaimsFromToken(token);
            return claims.isPresent() ? Optional.ofNullable(LocalDateTimeUtil.fromDate(claims.get().getExpiration())) : Optional.empty();
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Optional<Claims> getClaimsFromToken(String token) {
        try {
            return Optional.ofNullable(Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody());
        } catch (ExpiredJwtException e) {
            throw new AccessDeniedCadastroExceptionDefault("Sessão inválida");
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + (this.expiration * 1000));
    }

    private Boolean isTokenExpired(String token) {
        final Optional<LocalDateTime> expiration = getExpirationDateFromToken(token);
        return expiration.isPresent() ? expiration.get().isBefore(LocalDateTime.now()) : true;
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
        final Optional<String> email = getEmailFromToken(token);

        if ((!email.isPresent()) || !email.get().equals(user.getUsername())) {
            return false;
        }

        if (isTokenExpired(token)) {
            throw new UnauthorizedCadastroExceptionDefault("Sessão inválida");
        }

        return true;
    }

}
