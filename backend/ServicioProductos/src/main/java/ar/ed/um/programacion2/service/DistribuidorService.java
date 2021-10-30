package ar.ed.um.programacion2.service;

import ar.ed.um.programacion2.domain.Distribuidor;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Distribuidor}.
 */
public interface DistribuidorService {
    /**
     * Save a distribuidor.
     *
     * @param distribuidor the entity to save.
     * @return the persisted entity.
     */
    Distribuidor save(Distribuidor distribuidor);

    /**
     * Partially updates a distribuidor.
     *
     * @param distribuidor the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Distribuidor> partialUpdate(Distribuidor distribuidor);

    /**
     * Get all the distribuidors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Distribuidor> findAll(Pageable pageable);

    /**
     * Get the "id" distribuidor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Distribuidor> findOne(Long id);

    /**
     * Delete the "id" distribuidor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
