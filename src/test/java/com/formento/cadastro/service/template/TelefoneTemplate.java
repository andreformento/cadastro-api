package com.formento.cadastro.service.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.formento.cadastro.model.Telefone;

public class TelefoneTemplate implements TemplateLoader {

    public static String VALID_TELEFONE11 = "validTelefone11";
    public static String VALID_TELEFONE47 = "validTelefone47";

    @Override
    public void load() {
        Fixture.of(Telefone.class).addTemplate(VALID_TELEFONE11, new Rule() {{
            add("ddd", "11");
            add("numero", "123456789");
        }}).addTemplate(VALID_TELEFONE47, new Rule() {{
            add("ddd", "47");
            add("numero", "987654321");
        }});
    }

}
