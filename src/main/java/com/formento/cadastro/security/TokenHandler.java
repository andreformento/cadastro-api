package com.formento.cadastro.security;

import com.google.common.base.Preconditions;
import org.springframework.security.core.userdetails.User;

public final class TokenHandler {

    private final String secret;
    private final UsuarioDetailsService userService;

    public TokenHandler(String secret, UsuarioDetailsService userService) {
        this.secret = secret;
        this.userService = Preconditions.checkNotNull(userService);
    }

    public User parseUserFromToken(String token) {
//        String username = Jwts.parser()
//                .setSigningKey(secret)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
        String username = "b";
        return userService.loadUserByUsername(username);
    }

    public String createTokenForUser(User user) {
//        return Jwts.builder()
//                .setSubject(user.getUsername())
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
        return "a";
    }
}