package com.marioborrego.gestordocumentalbackend.services.implemetation;

import com.marioborrego.gestordocumentalbackend.models.Empleado;
import com.marioborrego.gestordocumentalbackend.presentation.dto.EmpleadoDTO;
import com.marioborrego.gestordocumentalbackend.repositories.EmpleadoRepository;
import com.marioborrego.gestordocumentalbackend.services.EmpleadoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpleadosServiceImpl implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public EmpleadosServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public List<Empleado> getEmpleadosPorRol() {
        return null;
    }

    @Override
    public List<EmpleadoDTO> obtenerTodosLosEmpleados() {
        List<Empleado> empleados = empleadoRepository.findAll();
        return empleados.stream()
                .map(empleado -> EmpleadoDTO.builder()
                        .idEmpleado(empleado.getIdEmpleado())
                        .nombre(empleado.getNombre())
                        .email(empleado.getEmail())
                        .telefono(empleado.getTelefono())
                        .rol(empleado.getRol().getRol())
                        .build())
                .collect(Collectors.toList());
    }
}
