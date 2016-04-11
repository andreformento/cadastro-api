package com.formento.cadastro.security;

import com.formento.cadastro.exception.UnauthorizedCadastroExceptionDefault;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(() -> {
                    return new UnauthorizedCadastroExceptionDefault("Usuário e/ou senha inválidos");
                });

        return JwtUserFactory.create(usuario);
    }
}
