package com.formento.cadastro.service;

import com.formento.cadastro.model.Usuario;

import java.util.Collection;
import java.util.Optional;

public interface UsuarioService {

    Usuario create(Usuario usuario);

    Optional<Usuario> getByEmail(String email);

    Collection<Usuario> getUsuarios();
}
