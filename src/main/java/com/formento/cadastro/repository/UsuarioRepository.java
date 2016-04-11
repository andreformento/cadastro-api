package com.formento.cadastro.repository;

import com.formento.cadastro.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.Optional;

@RestResource(exported = false)
@RepositoryRestController
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

    @Query("SELECT COUNT(usuario) from Usuario usuario WHERE usuario.email = ?1")
    Integer countByEmail(@Param("email") String email);

    @Query(" select usuario " +
            " from Usuario usuario " +
            " where usuario.email = ?1")
    Optional<Usuario> findByEmail(@Param("email") String email);

    @Query(" select usuario " +
            " from Usuario usuario " +
            " where usuario.email = ?1" +
            "   and usuario.senha = ?2")
    Optional<Usuario> findByEmailESenha(@Param("email") String email, @Param("senha") String senha);

}
