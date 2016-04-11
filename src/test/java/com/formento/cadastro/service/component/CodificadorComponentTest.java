package com.formento.cadastro.service.component;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        assertNotNull(senhaCodificada);
        assertEquals(60, senhaCodificada.length());
    }

}
