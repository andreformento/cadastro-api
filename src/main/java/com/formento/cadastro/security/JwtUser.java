package com.formento.cadastro.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public class JwtUser implements UserDetails, UsuarioAuthentication {

    private final UUID uuid;
    private final String email;
    private final String nome;
    private final String token;
    private final String senha;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final LocalDateTime ultimoLogin;

    public JwtUser(UUID id, String email, String nome, String token, String senha, Collection<? extends GrantedAuthority> authorities, boolean enabled, LocalDateTime ultimoLogin) {
        this.uuid = id;
        this.email = email;
        this.nome = nome;
        this.token = token;
        this.senha = senha;
        this.authorities = authorities;
        this.enabled = enabled;
        this.ultimoLogin = ultimoLogin;
    }

    public UUID getId() {
        return uuid;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getNome() {
        return nome;
    }

    public String getToken() {
        return token;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public LocalDateTime getUltimoLogin() {
        return ultimoLogin;
    }

    @JsonIgnore
    @Override
    public String getEmail() {
        return email;
    }

    @Override
    @JsonIgnore
    public String getSenha() {
        return senha;
    }

}
