package com.formento.cadastro.service;

import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UsuarioServiceProvider implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario create(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario getByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Collection<Usuario> getUsuarios() {
        return usuarioRepository.getUsuarios();
    }

}
