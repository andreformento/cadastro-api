package com.formento.cadastro.service;

import com.formento.cadastro.model.Usuario;

import java.util.Collection;

public interface UsuarioService {

    Usuario create(Usuario usuario);

    Usuario getByEmail(String email);

    Collection<Usuario> getUsuarios();
}
