package com.formento.cadastro.security.service;

import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.security.UsuarioAuthentication;
import org.springframework.security.core.AuthenticationException;

public interface AuthenticationRestService {

    void createAuthentication(UsuarioAuthentication usuario) throws AuthenticationException;

    Usuario createAuthenticationToken(UsuarioAuthentication usuarioAuthentication) throws AuthenticationException;

}
