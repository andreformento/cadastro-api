package com.formento.cadastro.api.v1.usuario.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.service.UsuarioService;
import com.formento.cadastro.service.template.UsuarioTemplate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("com.formento.cadastro.service.template");
    }

    @Before
    public void init() {
        this.usuarioController = new UsuarioController(usuarioService);
        this.mockMvc = standaloneSetup(this.usuarioController).build();
    }

    @Test
    public void deveRetornarUmUsuarioValido() throws Exception {
        // given
        Usuario usuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALID_USUARIO_NOVO);
        assertNotNull(usuario);

        // when
        when(usuarioService.getUsuarioLogado()).thenReturn(Optional.of(usuario));

        ResultActions resultActions = this.mockMvc.perform(get("/v1/usuarios").accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(containsString("andre formento")))
                .andExpect(content().string(containsString("andre@mail.com")))
                .andExpect(content().string(not(containsString("minhaSenha"))))
                .andExpect(content().string(containsString("tok3n")));
    }

}