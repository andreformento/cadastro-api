package com.formento.cadastro.service.component;

import com.formento.cadastro.CadastroApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CadastroApplication.class)
@WebAppConfiguration
public class CodificadorComponentTest {

    @Autowired
    private CodificadorComponent codificadorComponent;

    @Test
    public void deveCodificarASenha() {
        // given
        String senha = "senha";

        // when
        String senhaCodificada = codificadorComponent.codificar(senha);

        // then
        assertEquals("e8d95a51f3af4a3b134bf6bb680a213a", senhaCodificada);
    }

}
