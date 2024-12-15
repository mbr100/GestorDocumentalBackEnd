package com.marioborrego.gestordocumentalbackend.presentation.controller;

import com.marioborrego.gestordocumentalbackend.business.services.auth.AuthenticateService;
import com.marioborrego.gestordocumentalbackend.presentation.dto.auth.AuthenticationRequest;
import com.marioborrego.gestordocumentalbackend.presentation.dto.auth.AuthenticationResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
