package com.formento.cadastro.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(HomeController.class)
public class HomeControllerIntegrationTest {

    @InjectMocks
    private HomeController homeController;

    private MockMvc mockMvc;

    @Before
    public void init() {
        this.mockMvc = standaloneSetup(this.homeController).build();
    }

    @Test
    public void deveRetornarUmUsuarioValido() throws Exception {
        // when
        ResultActions resultActions = this.mockMvc.perform(get("/").accept(MediaType.APPLICATION_JSON));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString("Cadastro - https://github.com/andreformento/cadastro-api")));
    }

}
