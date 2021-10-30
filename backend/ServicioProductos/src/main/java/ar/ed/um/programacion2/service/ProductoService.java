package ar.ed.um.programacion2.service;

import ar.ed.um.programacion2.domain.Producto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Producto}.
 */
public interface ProductoService {
    /**
     * Save a producto.
     *
     * @param producto the entity to save.
     * @return the persisted entity.
     */
    Producto save(Producto producto);

    /**
     * Partially updates a producto.
     *
     * @param producto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Producto> partialUpdate(Producto producto);

    /**
     * Get all the productos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Producto> findAll(Pageable pageable);

    /**
     * Get the "id" producto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Producto> findOne(Long id);

    /**
     * Delete the "id" producto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
