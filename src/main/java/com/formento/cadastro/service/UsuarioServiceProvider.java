package com.formento.cadastro.service;

import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.repository.UsuarioRepository;
import com.formento.cadastro.security.JwtTokenUtil;
import com.formento.cadastro.service.component.CodificadorComponent;
import com.formento.cadastro.service.validation.UsuarioValidator;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Usuario create(Usuario usuario) {
        Preconditions.checkNotNull(usuario, "Usuário não informado");
        Preconditions.checkNotNull(usuario.getSenha(), "senha não informada");
        Preconditions.checkNotNull(usuario.getEmail(), "email não informado");

        String senha = codificadorComponent.codificar(usuario.getSenha());
        String token = jwtTokenUtil.generateToken(usuario.getEmail());
        Usuario novo = new Usuario(usuario.getNome(), usuario.getEmail(), senha, LocalDate.now(), LocalDate.now(), LocalDateTime.now(), token, usuario.getTelefones());

        usuarioValidator.beforeCreate(novo);
        return usuarioRepository.save(novo);
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
