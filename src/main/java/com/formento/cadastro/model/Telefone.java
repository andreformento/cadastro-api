package com.formento.cadastro.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Telefone implements Serializable {

    private String ddd;

    @NotNull
    @NotEmpty
    private String numero;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Telefone telefone = (Telefone) o;

        if (ddd != null ? !ddd.equals(telefone.ddd) : telefone.ddd != null) {
            return false;
        }
        return numero != null ? numero.equals(telefone.numero) : telefone.numero == null;

    }

    @Override
    public int hashCode() {
        int result = ddd != null ? ddd.hashCode() : 0;
        result = 31 * result + (numero != null ? numero.hashCode() : 0);
        return result;
    }

}
