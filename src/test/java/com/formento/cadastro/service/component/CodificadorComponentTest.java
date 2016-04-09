package com.formento.cadastro.service.component;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class CodificadorComponentTest {

    @InjectMocks
    private CodificadorComponent codificadorComponent = new CodificadorComponentProvider();

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
