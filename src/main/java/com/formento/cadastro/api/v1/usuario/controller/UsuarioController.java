package com.formento.cadastro.api.v1.usuario.controller;

import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/usuarios")
@Validated
@Api(value = "API de Usuários", description = "Cadastro de usuário", basePath = "/v1/usuarios", produces = "application/json")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @ApiOperation(value = "Buscar usuários", notes = "Retorna uma lista de usuarios", response = Usuario.class)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Resources<Usuario>> getUsuarios() {
        return new ResponseEntity<Resources<Usuario>>(new Resources<>(usuarioService.getUsuarios()), HttpStatus.OK);
    }

    @ApiOperation(value = "Criar um usuário", notes = "Cria e retorna um usuário", response = Usuario.class)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Resource<Usuario>> create(@RequestBody Usuario usuario) {
        return new ResponseEntity<Resource<Usuario>>(new Resource<>(usuarioService.create(usuario)), HttpStatus.OK);
    }

    @ApiOperation(value = "Busca usuário por email", notes = "Retorna o usuário da busca", response = Usuario.class)
    @RequestMapping(value = "/{email:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Resource<Usuario>> getUsuarioByEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.getByEmail(email);
        if (usuario.isPresent()) {
            return new ResponseEntity<Resource<Usuario>>(new Resource<>(usuario.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<Resource<Usuario>>(HttpStatus.NOT_FOUND);
        }
    }

}
