package com.formento.cadastro.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.formento.cadastro.model.representation.EmptyFieldSerializer;
import com.formento.cadastro.model.representation.LocalDateSerializer;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

// Immutable
@Entity
public class Usuario implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    private String nome;

    @Email
    @NotNull
    @Column(unique = true)
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    @JsonSerialize(using = EmptyFieldSerializer.class)
    private String senha;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dataCriacao;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dataAtualizacao;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate ultimoLogin;

    private String token;

    @ElementCollection
    private Collection<Telefone> telefones;

    public Usuario() {

    }

    public Usuario(String nome, String email, String senha, LocalDate dataCriacao, LocalDate dataAtualizacao, LocalDate ultimoLogin, String token, Collection<Telefone> telefones) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.ultimoLogin = ultimoLogin;
        this.token = token;
        this.telefones = telefones;
    }

    public Usuario(Long id, String nome, String email, String senha, LocalDate dataCriacao, LocalDate dataAtualizacao, LocalDate ultimoLogin, String token, Collection<Telefone> telefones) {
        this(nome, email, senha, dataCriacao, dataAtualizacao, ultimoLogin, token, telefones);
        this.id = id;

    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public LocalDate getUltimoLogin() {
        return ultimoLogin;
    }

    public String getToken() {
        return token;
    }

    public Collection<Telefone> getTelefones() {
        return telefones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        return id != null ? id.equals(usuario.id) : usuario.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

}
