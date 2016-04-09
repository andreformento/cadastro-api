package com.formento.cadastro.service;

import com.formento.cadastro.exception.BusinessCadastroExceptionDefault;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class UsuarioServiceProvider implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario create(Usuario usuario) {
        Optional<Usuario> byEmail = getByEmail(usuario.getEmail());

        if (byEmail.isPresent()) {
            throw new BusinessCadastroExceptionDefault("E-mail já existente");
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> getByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Collection<Usuario> getUsuarios() {
        return usuarioRepository.getUsuarios();
    }

}
