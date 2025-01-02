package com.marioborrego.gestordocumentalbackend.presentation.dto.rolDTO;

import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoRol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolesDTO {
    private String rol;
    private TipoRol tipoRol;
}
