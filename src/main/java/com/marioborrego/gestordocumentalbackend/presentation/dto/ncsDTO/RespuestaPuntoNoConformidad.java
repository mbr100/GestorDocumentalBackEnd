package com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RespuestaPuntoNoConformidad {
    @NotBlank
    @NotEmpty
    @NotNull
    private String contenido;
    @NotBlank
    @NotEmpty
    @NotNull
    @Past
    private Date fecha;
    @NonNull
    @Positive
    private Long idNoConformidad;
}
