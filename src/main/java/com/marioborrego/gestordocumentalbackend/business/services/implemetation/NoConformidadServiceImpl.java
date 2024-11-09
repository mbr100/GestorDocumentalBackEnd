package com.marioborrego.gestordocumentalbackend.business.services.implemetation;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.NoConformidadService;
import com.marioborrego.gestordocumentalbackend.business.utils.CodeProyect;
import com.marioborrego.gestordocumentalbackend.domain.models.NoConformidad;
import com.marioborrego.gestordocumentalbackend.domain.repositories.NoConformidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoConformidadServiceImpl implements NoConformidadService {
    private final NoConformidadRepository noConformidadRepository;

    public NoConformidadServiceImpl(NoConformidadRepository noConformidadRepository) {
        this.noConformidadRepository = noConformidadRepository;
    }

    @Override
    public List<NoConformidad> noConformidadesPorProyecto(String idProyecto) {
        if (idProyecto == null) {
            return List.of();
        }
        Long id = CodeProyect.decode(idProyecto);
        return noConformidadRepository.noConformidadesPorProyecto(id);
    }
}
