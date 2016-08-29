package com.formento.cadastro.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.formento.cadastro.model.converter.*;
import com.formento.cadastro.security.UsuarioAuthentication;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

// Immutable
@Entity
public class Usuario implements Serializable, UsuarioAuthentication {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @NotNull
    @NotEmpty
    private String nome;

    @Email
    @NotNull
    @Column(unique = true)
    @NotEmpty
    @Size(min = 5, max = 30)
    private String email;

    @NotNull
    @NotEmpty
    @JsonSerialize(using = EmptyFieldSerializer.class)
    private String senha;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataCriacao;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dataAtualizacao;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime ultimoLogin;

    @Size(max = 1024)
    private String token;

    @ElementCollection
    private Collection<Telefone> telefones;

    public Usuario() {

    }

    public Usuario(String nome, String email, String senha, LocalDate dataCriacao, LocalDate dataAtualizacao, LocalDateTime ultimoLogin, String token, Collection<Telefone> telefones) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
        this.ultimoLogin = ultimoLogin;
        this.token = token;
        this.telefones = telefones;
    }

    public Usuario(UUID uuid, String nome, String email, String senha, LocalDate dataCriacao, LocalDate dataAtualizacao, LocalDateTime ultimoLogin, String token, Collection<Telefone> telefones) {
        this(nome, email, senha, dataCriacao, dataAtualizacao, ultimoLogin, token, telefones);
        this.uuid = uuid;

    }

    public UUID getId() {
        return uuid;
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

    public LocalDateTime getUltimoLogin() {
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

        return uuid != null ? uuid.equals(usuario.uuid) : usuario.uuid == null;

    }

    @Override
    public int hashCode() {
        return uuid != null ? uuid.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "email='" + email + '\'' +
                ", uuid=" + uuid +
                ", nome='" + nome + '\'' +
                '}';
    }

}
