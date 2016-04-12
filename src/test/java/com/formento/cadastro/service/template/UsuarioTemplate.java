package com.formento.cadastro.service.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.formento.cadastro.model.Telefone;
import com.formento.cadastro.model.Usuario;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsuarioTemplate implements TemplateLoader {

    public static String VALID_USUARIO_NOVO = "validUsuarioNovo";

    @Override
    public void load() {
        Fixture.of(Usuario.class).addTemplate(VALID_USUARIO_NOVO, new Rule() {{
            add("nome", "andre formento");
            add("email", "andre@mail.com");
            add("senha", "minhaSenha");
            add("dataCriacao", LocalDate.now());
            add("dataAtualizacao", LocalDate.now());
            add("ultimoLogin", LocalDateTime.now());
            add("token", "tok3n");
            add("telefones", has(2).of(Telefone.class, TelefoneTemplate.VALID_TELEFONE11, TelefoneTemplate.VALID_TELEFONE47));
        }});
    }

}
