package com.marioborrego.gestordocumentalbackend.presentation.dto.ncsDTO;

import lombok.*;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class NuevoPuntoNcDTO {
    public String idProyecto;
    public String idNoConformidad;
    public String nuevoPuntoNC;
}
