package com.formento.cadastro.service.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CodificadorComponentProvider implements CodificadorComponent {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public CodificadorComponentProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String codificar(String text) {
        return passwordEncoder.encode(text);
    }

}
