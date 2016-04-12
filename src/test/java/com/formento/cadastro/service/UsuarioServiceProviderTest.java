package com.formento.cadastro.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.repository.UsuarioRepository;
import com.formento.cadastro.security.component.JwtTokenUtil;
import com.formento.cadastro.security.service.AuthenticationRestService;
import com.formento.cadastro.service.component.CodificadorComponent;
import com.formento.cadastro.service.template.UsuarioTemplate;
import com.formento.cadastro.service.validation.UsuarioValidator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("com.formento.cadastro.service.template");
    }

    @Before
    public void init() {
        this.usuarioService = new UsuarioServiceProvider(usuarioRepository, usuarioValidator, codificadorComponent, jwtTokenUtil, authenticationRestService);
    }

    @Test
    public void deveGravarUmNovoUsuario() {
        // given
        Usuario usuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALID_USUARIO_NOVO);
        assertNotNull(usuario);

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