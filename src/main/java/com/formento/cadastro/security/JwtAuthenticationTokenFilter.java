package com.formento.cadastro.security;

import com.formento.cadastro.exception.UnauthorizedCadastroExceptionDefault;
import com.formento.cadastro.security.component.JwtTokenUtil;
import com.formento.cadastro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.scheme}")
    private String tokenScheme;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Optional<String> authTokenWithSchemeOptional = Optional.ofNullable(httpRequest.getHeader(this.tokenHeader));

        if (authTokenWithSchemeOptional.isPresent()) {
            final String authTokenWithScheme = authTokenWithSchemeOptional.get();
            final String authScheme = Optional
                    .ofNullable(this.tokenScheme)
                    .orElse("");

            if (!authTokenWithScheme.startsWith(authScheme)) {
                throw new UnauthorizedCadastroExceptionDefault("Scheme do token inválido");
            }

            final String authToken = authTokenWithScheme.substring(authScheme.length()).trim();

            Optional<String> email = jwtTokenUtil.getEmailFromToken(authToken);

            if (email.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(email.get());
                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    usuarioService.validarTokenGravado(email.get(), authToken);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
