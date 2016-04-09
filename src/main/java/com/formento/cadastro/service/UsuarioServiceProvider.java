package com.formento.cadastro.service;

import com.formento.cadastro.exception.BusinessCadastroExceptionDefault;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.repository.UsuarioRepository;
import com.formento.cadastro.service.component.CodificadorComponent;
import com.formento.cadastro.service.validation.UsuarioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

@Service
public class UsuarioServiceProvider implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioValidator usuarioValidator;

    @Autowired
    private CodificadorComponent codificadorComponent;

    @Override
    public Usuario create(Usuario usuario) {
        String senha = codificadorComponent.codificar(usuario.getSenha());
        Usuario novo = new Usuario(usuario.getNome(), usuario.getEmail(), senha, LocalDate.now(), LocalDate.now(), LocalDate.now(), null, usuario.getTelefones());

        usuarioValidator.beforeCreate(usuario);
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
