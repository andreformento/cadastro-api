package com.formento.cadastro.service.validation;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.formento.cadastro.exception.BusinessCadastroExceptionDefault;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.service.UsuarioService;
import com.formento.cadastro.service.template.UsuarioTemplate;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioValidatorProviderTest {

    @Mock
    private UsuarioService usuarioService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private UsuarioValidator usuarioValidator;

    @BeforeClass
    public static void initClass() {
        FixtureFactoryLoader.loadTemplates("com.formento.cadastro.service.template");
    }

    @Before
    public void init() {
        this.usuarioValidator = new UsuarioValidatorProvider(usuarioService);
    }

    @Test
    public void devePermitirGravarQuandoNaoHouverUsuarioComEmailDuplicado() {
        // given
        Usuario usuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALID_USUARIO_NOVO);
        assertNotNull(usuario);

        // when...then
        when(usuarioService.countByEmail(usuario.getEmail())).thenReturn(0);
        usuarioValidator.beforeCreate(usuario);
    }

    @Test
    public void naoDevePermitirGravarQuandoHouverUsuarioComEmailDuplicado() {
        // given
        Usuario usuario = Fixture.from(Usuario.class).gimme(UsuarioTemplate.VALID_USUARIO_NOVO);
        assertNotNull(usuario);

        // then
        expectedException.expect(BusinessCadastroExceptionDefault.class);
        expectedException.expectMessage("E-mail já existente");

        // when
        when(usuarioService.countByEmail(usuario.getEmail())).thenReturn(1);
        usuarioValidator.beforeCreate(usuario);
    }

}
