package ar.ed.um.programacion2.service.impl;

import ar.ed.um.programacion2.domain.Distribuidor;
import ar.ed.um.programacion2.repository.DistribuidorRepository;
import ar.ed.um.programacion2.service.DistribuidorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Distribuidor}.
 */
@Service
@Transactional
public class DistribuidorServiceImpl implements DistribuidorService {

    private final Logger log = LoggerFactory.getLogger(DistribuidorServiceImpl.class);

    private final DistribuidorRepository distribuidorRepository;

    public DistribuidorServiceImpl(DistribuidorRepository distribuidorRepository) {
        this.distribuidorRepository = distribuidorRepository;
    }

    @Override
    public Distribuidor save(Distribuidor distribuidor) {
        log.debug("Request to save Distribuidor : {}", distribuidor);
        return distribuidorRepository.save(distribuidor);
    }

    @Override
    public Optional<Distribuidor> partialUpdate(Distribuidor distribuidor) {
        log.debug("Request to partially update Distribuidor : {}", distribuidor);

        return distribuidorRepository
            .findById(distribuidor.getId())
            .map(existingDistribuidor -> {
                if (distribuidor.getNombre() != null) {
                    existingDistribuidor.setNombre(distribuidor.getNombre());
                }
                if (distribuidor.getDescripcion() != null) {
                    existingDistribuidor.setDescripcion(distribuidor.getDescripcion());
                }

                return existingDistribuidor;
            })
            .map(distribuidorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Distribuidor> findAll(Pageable pageable) {
        log.debug("Request to get all Distribuidors");
        return distribuidorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Distribuidor> findOne(Long id) {
        log.debug("Request to get Distribuidor : {}", id);
        return distribuidorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Distribuidor : {}", id);
        distribuidorRepository.deleteById(id);
    }
}
