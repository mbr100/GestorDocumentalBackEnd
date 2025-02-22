package com.marioborrego.gestordocumentalbackend.business.services.auth;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.UsuarioService;
import com.marioborrego.gestordocumentalbackend.domain.models.JwtToken;
import com.marioborrego.gestordocumentalbackend.domain.models.Usuario;
import com.marioborrego.gestordocumentalbackend.domain.repositories.JwtTokenRepository;
import com.marioborrego.gestordocumentalbackend.presentation.dto.auth.AuthenticationRequest;
import com.marioborrego.gestordocumentalbackend.presentation.dto.auth.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticateService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticateService.class);
    private final JwtTokenRepository jwtRepository;

    public AuthenticateService(UsuarioService usuarioService, JwtService jwtService, AuthenticationManager authenticationManager, JwtTokenRepository jwtRepository) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.jwtRepository = jwtRepository;
    }

    private Map<String, Object> generateExtraClaims(Usuario user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("nombre", user.getNombre());
        extraClaims.put("email",user.getEmail());
        extraClaims.put("rol",user.getRol().getRol());
        extraClaims.put("idUsuario",user.getIdUsuario());
        return extraClaims;
    }

    public AuthenticationResponse login(@Valid AuthenticationRequest auth) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(auth.getNombre(), auth.getPassword());
        authenticationManager.authenticate(authentication);
        Usuario user = usuarioService.getUsuarioByNombre(auth.getNombre());
        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        saveUserToken(user, jwt);


        AuthenticationResponse response = new AuthenticationResponse();
        response.setJwt(jwt);
        return response;
    }

    private void saveUserToken(Usuario user, String jwt) {
        JwtToken token = new JwtToken();
        token.setToken(jwt);
        token.setUser(user);
        token.setExpiration(jwtService.extractExpiration(jwt));
        token.setValid(true);

        jwtRepository.save(token);
    }

    public boolean validateToken(String jwt) {
        try{
            jwtService.extractUsername(jwt);
            return true;
        }catch (Exception e){
            logger.error("Error validating token", e);
            return false;
        }
    }

    public void logout(HttpServletRequest request) {

        String jwt = jwtService.extractJwtFromRequest(request);
        if(!StringUtils.hasText(jwt)) return;

        Optional<JwtToken> token = jwtRepository.findByToken(jwt);

        if(token.isPresent()  && token.get().isValid()){
            token.get().setValid(false);
            jwtRepository.save(token.get());
        }
    }
}
