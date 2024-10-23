package com.marioborrego.gestordocumentalbackend.presentation.dto.rolDTO;

import com.marioborrego.gestordocumentalbackend.presentation.dto.empleadoDTO.ListarEmpleadoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class RolDTO {
    private String rol;
    private List<ListarEmpleadoDTO> empleados;
}
