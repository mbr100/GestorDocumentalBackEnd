package com.marioborrego.gestordocumentalbackend.business.services.implemetation;

import com.marioborrego.gestordocumentalbackend.business.services.interfaces.NoConformidadService;
import com.marioborrego.gestordocumentalbackend.business.utils.CodeProyect;
import com.marioborrego.gestordocumentalbackend.domain.models.NoConformidad;
import com.marioborrego.gestordocumentalbackend.domain.repositories.NoConformidadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoConformidadServiceImpl implements NoConformidadService {
    private static final Logger log = LoggerFactory.getLogger(NoConformidadServiceImpl.class);


    private final NoConformidadRepository noConformidadRepository;

    public NoConformidadServiceImpl(NoConformidadRepository noConformidadRepository) {
        this.noConformidadRepository = noConformidadRepository;
    }

    @Override
    public List<NoConformidad> obtenerNoConformiddadProyecto(String idProyecto) {
        Long id = (long) CodeProyect.codeProyectToId(idProyecto);
        log.info("id proyecto {}",id);
        return this.noConformidadRepository.findNoConformidadByProyecto(id);
    }
}
