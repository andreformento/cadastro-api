package com.formento.cadastro.security.service;

import com.formento.cadastro.exception.UnauthorizedCadastroExceptionDefault;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.security.JwtUserFactory;
import com.formento.cadastro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtUsuarioServiceServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = usuarioService
                .getByEmail(email)
                .orElseThrow(() -> {
                    return new UnauthorizedCadastroExceptionDefault("Usuário e/ou senha inválidos");
                });

        return JwtUserFactory.create(usuario);
    }

}
