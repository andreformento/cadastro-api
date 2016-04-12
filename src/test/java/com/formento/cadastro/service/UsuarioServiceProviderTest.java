package com.formento.cadastro.service;

import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.repository.UsuarioRepository;
import com.formento.cadastro.security.component.JwtTokenUtil;
import com.formento.cadastro.security.service.AuthenticationRestService;
import com.formento.cadastro.service.component.CodificadorComponent;
import com.formento.cadastro.service.validation.UsuarioValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioServiceProviderTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private UsuarioValidator usuarioValidator;

    @Mock
    private CodificadorComponent codificadorComponent;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private AuthenticationRestService authenticationRestService;

    private UsuarioService usuarioService;

    @Before
    public void init() {
        this.usuarioService = new UsuarioServiceProvider(usuarioRepository, usuarioValidator, codificadorComponent, jwtTokenUtil, authenticationRestService);
    }

    @Test
    public void deveGravarUmNovoUsuario() {
        // given
        Usuario usuario = new Usuario("andre formento", "andreformento@mail.com", "minhaSenha", LocalDate.now(), LocalDate.now(), LocalDateTime.now(), null, new ArrayList<>());

        // when
        when(codificadorComponent.codificar(usuario.getSenha())).thenReturn(usuario.getSenha());
        when(jwtTokenUtil.generateToken(usuario)).thenReturn("token");
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario usuarioGravado = usuarioService.create(usuario);

        // then
        assertNotNull(usuarioGravado);

        verify(usuarioValidator, times(1)).beforeCreate(usuario);
    }

}