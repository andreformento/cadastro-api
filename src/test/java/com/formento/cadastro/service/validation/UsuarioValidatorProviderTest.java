package com.formento.cadastro.service.validation;

import com.formento.cadastro.exception.BusinessCadastroExceptionDefault;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.service.UsuarioService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsuarioValidatorProviderTest {

    @Mock
    private UsuarioService usuarioService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private UsuarioValidator usuarioValidator;

    @Before
    public void init() {
        this.usuarioValidator = new UsuarioValidatorProvider(usuarioService);
    }

    @Test
    public void devePermitirGravarQuandoNaoHouverUsuarioComEmailDuplicado() {
        // given
        Usuario usuario = new Usuario("andre formento", "andreformento@mail.com", "minhaSenha", LocalDate.now(), LocalDate.now(), LocalDateTime.now(), null, new ArrayList<>());

        // when...then
        when(usuarioService.countByEmail(usuario.getEmail())).thenReturn(0);
        usuarioValidator.beforeCreate(usuario);
    }

    @Test
    public void naoDevePermitirGravarQuandoHouverUsuarioComEmailDuplicado() {
        // given
        Usuario usuario = new Usuario("andre formento", "andreformento@mail.com", "minhaSenha", LocalDate.now(), LocalDate.now(), LocalDateTime.now(), null, new ArrayList<>());

        // then
        expectedException.expect(BusinessCadastroExceptionDefault.class);
        expectedException.expectMessage("E-mail já existente");

        // when
        when(usuarioService.countByEmail(usuario.getEmail())).thenReturn(1);
        usuarioValidator.beforeCreate(usuario);
    }

}