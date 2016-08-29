package com.formento.cadastro.api.v1.usuario.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.service.UsuarioService;
import com.formento.cadastro.service.template.UsuarioTemplate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private MockMvc mockMvc;

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("com.formento.cadastro.service.template");
    }

    @Before
    public void init() {
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
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("andre formento")))
                .andExpect(content().string(containsString("andre@mail.com")))
                .andExpect(content().string(not(containsString("minhaSenha"))))
                .andExpect(content().string(containsString("tok3n")));
    }

    @Test
    public void naoDeveRetornarUmUsuarioValido() throws Exception {
        // given
        Optional<Usuario> emptyUsuario = Optional.empty();

        // when
        when(usuarioService.getUsuarioLogado()).thenReturn(emptyUsuario);

        ResultActions resultActions = this.mockMvc.perform(get("/v1/usuarios").accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isNotFound());
    }

    @Test
    public void deveCriarUmUsuario() throws Exception {
        // given
        final Usuario usuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALID_USUARIO_NOVO);
        assertNotNull(usuario);
        final String jsonContent = new ObjectMapper().writeValueAsString(usuario);

        // when
        when(usuarioService.create(usuario)).thenReturn(usuario);

        ResultActions resultActions = this.mockMvc
                .perform(post("/v1/usuarios")
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonContent)
                        .accept(MediaType.APPLICATION_JSON_UTF8)
                );

        // then
        resultActions
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("andre formento")))
                .andExpect(content().string(containsString("andre@mail.com")))
                .andExpect(content().string(not(containsString("minhaSenha"))))
                .andExpect(content().string(containsString("tok3n")));
    }

}
