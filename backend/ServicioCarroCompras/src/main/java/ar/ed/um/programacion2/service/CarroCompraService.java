package ar.ed.um.programacion2.service;

import ar.ed.um.programacion2.domain.CarroCompra;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link CarroCompra}.
 */
public interface CarroCompraService {
    /**
     * Save a carroCompra.
     *
     * @param carroCompra the entity to save.
     * @return the persisted entity.
     */
    CarroCompra save(CarroCompra carroCompra);

    /**
     * Partially updates a carroCompra.
     *
     * @param carroCompra the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CarroCompra> partialUpdate(CarroCompra carroCompra);

    /**
     * Get all the carroCompras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CarroCompra> findAll(Pageable pageable);

    /**
     * Get the "id" carroCompra.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CarroCompra> findOne(Long id);

    /**
     * Delete the "id" carroCompra.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
