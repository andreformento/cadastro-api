package com.formento.cadastro.service.component;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CodificadorComponentProvider implements CodificadorComponent {

    @Override
    public String codificar(String text) {
        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
        return encoder.encodePassword(text, null);
    }

}
