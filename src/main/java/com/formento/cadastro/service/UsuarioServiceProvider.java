package com.formento.cadastro.service;

import com.formento.cadastro.exception.UnauthorizedCadastroExceptionDefault;
import com.formento.cadastro.model.Usuario;
import com.formento.cadastro.repository.UsuarioRepository;
import com.formento.cadastro.security.UsuarioAuthentication;
import com.formento.cadastro.security.component.JwtTokenUtil;
import com.formento.cadastro.security.service.AuthenticationRestService;
import com.formento.cadastro.service.component.CodificadorComponent;
import com.formento.cadastro.service.validation.UsuarioValidator;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UsuarioServiceProvider implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioValidator usuarioValidator;

    @Autowired
    private CodificadorComponent codificadorComponent;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationRestService authenticationRestService;

    public UsuarioServiceProvider() {
    }

    public UsuarioServiceProvider(UsuarioRepository usuarioRepository, UsuarioValidator usuarioValidator, CodificadorComponent codificadorComponent, JwtTokenUtil jwtTokenUtil, AuthenticationRestService authenticationRestService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioValidator = usuarioValidator;
        this.codificadorComponent = codificadorComponent;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationRestService = authenticationRestService;
    }

    private String getSenhaCodificada(String senhaNaoCodificada) {
        return codificadorComponent.codificar(senhaNaoCodificada);
    }

    private String getToken(Usuario usuario) {
        return jwtTokenUtil.generateToken(usuario);
    }

    @Override
    public Usuario create(Usuario usuario) {
        Preconditions.checkNotNull(usuario, "Usuário não informado");
        Preconditions.checkNotNull(usuario.getSenha(), "senha não informada");
        Preconditions.checkNotNull(usuario.getEmail(), "email não informado");

        String senha = getSenhaCodificada(usuario.getSenha());

        Usuario novo = new Usuario(usuario.getNome(), usuario.getEmail(), senha, LocalDate.now(), LocalDate.now(), LocalDateTime.now(), getToken(usuario), usuario.getTelefones());

        usuarioValidator.beforeCreate(novo);
        Usuario save = usuarioRepository.save(novo);
        authenticationRestService.createAuthentication(usuario);
        return save;
    }

    @Override
    public Usuario updateToken(UsuarioAuthentication usuarioAuthentication) {
        Usuario byEmailESenha = getByEmail(usuarioAuthentication.getEmail()).orElseThrow(() -> {
            return new UnauthorizedCadastroExceptionDefault("Usuário e/ou senha inválidos");
        });

        String token = getToken(byEmailESenha);

        Usuario usuarioAtualizarToken = new Usuario(
                byEmailESenha.getId(),
                byEmailESenha.getNome(),
                byEmailESenha.getEmail(),
                byEmailESenha.getSenha(),
                byEmailESenha.getDataCriacao(),
                byEmailESenha.getDataAtualizacao(),
                LocalDateTime.now(),
                token,
                byEmailESenha.getTelefones());

        Usuario save = usuarioRepository.save(usuarioAtualizarToken);
        authenticationRestService.createAuthentication(usuarioAuthentication);
        return save;
    }

    @Override
    public Usuario getByEmailESenha(String email, String senha) {
        return usuarioRepository.findByEmailESenha(email, senha).orElseThrow(() -> {
            return new UnauthorizedCadastroExceptionDefault("Usuário e/ou senha inválidos");
        });
    }

    @Override
    public Integer countByEmail(String email) {
        return usuarioRepository.countByEmail(email);
    }

    @Override
    public Optional<Usuario> getByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Optional<Usuario> getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UsuarioAuthentication) {
            UsuarioAuthentication usuarioAuthentication = (UsuarioAuthentication) authentication.getPrincipal();
            return usuarioRepository.findByEmail(usuarioAuthentication.getEmail());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void validarTokenGravado(String email, String authToken) {
        Usuario usuario = getByEmail(email).orElseThrow(() -> new UnauthorizedCadastroExceptionDefault("Usuário não encontrado: " + email));
        if (!usuario.getToken().equals(authToken)) {
            throw new UnauthorizedCadastroExceptionDefault("Token inválido - sessão inválida");
        }
    }

}
