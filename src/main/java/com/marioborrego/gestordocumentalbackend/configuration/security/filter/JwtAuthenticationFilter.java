package com.marioborrego.gestordocumentalbackend.configuration.security.filter;

import com.marioborrego.gestordocumentalbackend.business.services.auth.JwtService;
import com.marioborrego.gestordocumentalbackend.business.services.interfaces.UsuarioService;
import com.marioborrego.gestordocumentalbackend.domain.models.JwtToken;
import com.marioborrego.gestordocumentalbackend.domain.models.Usuario;
import com.marioborrego.gestordocumentalbackend.domain.repositories.JwtTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.ObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UsuarioService usuarioService;
    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenRepository jwtRepository;

    public JwtAuthenticationFilter(JwtService jwtService, UsuarioService usuarioService, JwtTokenRepository jwtRepository) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
        this.jwtRepository = jwtRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = jwtService.extractJwtFromRequest(request);
        if(!StringUtils.hasText(jwt)){
            filterChain.doFilter(request, response);
            return;
        }

        Optional<JwtToken> token = jwtRepository.findByToken(jwt);
        boolean isValid = validateToken(token);

        if(!isValid){
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = usuarioService.getUsuarioByNombreONulo(username)
                    .orElseThrow(() -> new ObjectNotFoundException("Usuario", new Usuario()));
             if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                logger.info("Usuario autenticado: {}", authToken.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean validateToken(Optional<JwtToken> optionalJwtToken) {
        if(optionalJwtToken.isEmpty()){
            System.out.println("Token no existe o no fue generado en nuestro sistema");
            return false;
        }
        JwtToken token = optionalJwtToken.get();
        Date now = new Date(System.currentTimeMillis());
        boolean isValid = token.isValid() && token.getExpiration().after(now);

        if(!isValid){
            System.out.println("Token inválido");
            updateTokenStatus(token);
        }
        return isValid;
    }

    private void updateTokenStatus(JwtToken token) {
        token.setValid(false);
        jwtRepository.save(token);
    }
}
