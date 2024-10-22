package com.marioborrego.gestordocumentalbackend.presentation.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class EliminarObjetoDTO {
    private int statusCode;
    private String message;
}
