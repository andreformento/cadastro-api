package com.formento.cadastro.security;

import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.util.LocalDateUtil;

import java.util.ArrayList;
import java.util.Date;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Usuario usuario) {

        return new JwtUser(
                usuario.getId(),
                usuario.getEmail(),
                usuario.getNome(),
                usuario.getToken(),
                usuario.getSenha(),
                new ArrayList<>(),
                true,
                usuario.getUltimoLogin()
        );
    }

}
