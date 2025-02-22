package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.business.services.auth.AuthenticateService;
import com.marioborrego.gestordocumentalbackend.presentation.dto.auth.AuthenticationRequest;
import com.marioborrego.gestordocumentalbackend.presentation.dto.auth.AuthenticationResponse;
import com.marioborrego.gestordocumentalbackend.presentation.dto.auth.LogoutResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/auth")
public class AuthenticationController {
    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticateService authenticateService;

    public AuthenticationController(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest requestAuthentication) {
        log.info("Request to authenticate user: {}", requestAuthentication.getNombre());
        AuthenticationResponse response = authenticateService.login(requestAuthentication);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){
        boolean isTokenValid = authenticateService.validateToken(jwt);
        return ResponseEntity.status(isTokenValid ? HttpStatus.OK :HttpStatus.UNAUTHORIZED).body(isTokenValid);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request){
        authenticateService.logout(request);
        return ResponseEntity.ok(new LogoutResponse("Logout exitoso"));
    }

}
