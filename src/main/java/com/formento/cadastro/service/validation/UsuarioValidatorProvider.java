package com.formento.cadastro.service.validation;

import com.formento.cadastro.exception.BusinessCadastroExceptionDefault;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioValidatorProvider implements UsuarioValidator {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioValidatorProvider() {

    }

    public UsuarioValidatorProvider(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Override
    public void beforeCreate(Usuario usuario) {
        Integer count = usuarioService.countByEmail(usuario.getEmail());
        if (count > 0) {
            throw new BusinessCadastroExceptionDefault("E-mail já existente");
        }
    }

}
