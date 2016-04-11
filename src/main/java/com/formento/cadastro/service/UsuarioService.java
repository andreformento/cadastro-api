package com.formento.cadastro.service;

import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.security.UsuarioAuthentication;

import java.util.Collection;

public interface UsuarioService {

    Usuario create(Usuario usuario);

    Usuario updateToken(UsuarioAuthentication usuarioAuthentication);

    Usuario getByEmailESenha(String email, String senha);

    Integer countByEmail(String email);

    Collection<Usuario> getUsuarios();
}
