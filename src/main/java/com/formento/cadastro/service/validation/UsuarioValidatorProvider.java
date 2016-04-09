package com.formento.cadastro.service.validation;

import com.formento.cadastro.exception.BusinessCadastroExceptionDefault;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioValidatorProvider implements UsuarioValidator {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void beforeCreate(Usuario usuario) {
        Optional<Usuario> byEmail = usuarioService.getByEmail(usuario.getEmail());
        if (byEmail.isPresent()) {
            throw new BusinessCadastroExceptionDefault("E-mail já existente");
        }
    }

}
