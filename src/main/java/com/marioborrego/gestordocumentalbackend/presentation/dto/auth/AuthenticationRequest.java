package com.marioborrego.gestordocumentalbackend.presentation.dto.auth;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest implements Serializable {
    private String nombre;
    private String password;

}
