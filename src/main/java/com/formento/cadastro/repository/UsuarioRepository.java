package com.formento.cadastro.repository;

import com.formento.cadastro.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;

@RepositoryRestResource
public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {

    @Query(" select usuario " +
            " from Usuario usuario " +
            " where usuario.email = ?1")
    Usuario findByEmail(@Param("email") String email);

    @Query("select usuario " +
            "from Usuario usuario ")
    Collection<Usuario> getUsuarios();

}
