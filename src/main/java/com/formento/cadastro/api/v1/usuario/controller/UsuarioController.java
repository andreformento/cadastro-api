package com.formento.cadastro.api.v1.usuario.controller;

import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/v1/usuarios")
@Validated
@Api(value = "API de Usuários", description = "Cadastro de usuário", basePath = "/v1/usuarios", produces = "application/json")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @ApiOperation(value = "Carregar o usuário logado", notes = "Retorna o usuario logado", response = Usuario.class)
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Resource<Usuario>> getUsuario() {
        return usuarioService
                .getUsuarioLogado()
                .map(usuarioLogado -> new ResponseEntity<>(new Resource<>(usuarioLogado, linkTo(UsuarioController.class).withSelfRel()), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @ApiOperation(value = "Criar um usuário", notes = "Cria e retorna um usuário", response = Usuario.class)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Resource<Usuario>> create(@RequestBody Usuario usuario) {
        return new ResponseEntity<>(new Resource<>(usuarioService.create(usuario), linkTo(UsuarioController.class).withSelfRel()), HttpStatus.CREATED);
    }

}
