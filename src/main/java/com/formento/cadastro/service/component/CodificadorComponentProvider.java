package com.formento.cadastro.service.component;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CodificadorComponentProvider implements CodificadorComponent {

    @Override
    public String codificar(String text) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(text);
    }

}
