package com.formento.cadastro.security;

import java.io.Serializable;

public class JwtAuthenticationRequest implements Serializable, UsuarioAuthentication {

    private static final long serialVersionUID = -8445943548965154778L;

    private String email;
    private String senha;

    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String email, String senha) {
        this.setEmail(email);
        this.setSenha(senha);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
