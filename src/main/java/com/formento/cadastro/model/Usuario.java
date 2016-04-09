package com.formento.cadastro.model;

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

    private String nome;

    @NotNull
    @Column(unique = true)
    private String email;

    private String senha;

    private LocalDate dataCriacao;

    private LocalDate dataAtualizacao;

    private LocalDate ultimoLogin;

    private String token;

    @ElementCollection
    private Collection<Telefone> telefones;

    public Usuario() {

    }

    public Usuario(Long id, String nome, String email, String senha, LocalDate dataCriacao, LocalDate dataAtualizacao, LocalDate ultimoLogin, String token, Collection<Telefone> telefones) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.ultimoLogin = ultimoLogin;
        this.token = token;
        this.telefones = telefones;
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
