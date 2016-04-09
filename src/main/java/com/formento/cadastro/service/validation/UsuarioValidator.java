package com.formento.cadastro.service.validation;

import com.formento.cadastro.model.Usuario;

public interface UsuarioValidator {

    void beforeCreate(Usuario usuario);

}
