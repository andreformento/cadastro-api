package com.formento.cadastro.service.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CodificadorComponentProvider implements CodificadorComponent {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String codificar(String text) {
//        Md5PasswordEncoder encoder = new Md5PasswordEncoder();
//        return encoder.encodePassword(text, null);
        return passwordEncoder.encode(text);
    }

}
