package ar.ed.um.programacion2.service;

import ar.ed.um.programacion2.domain.ProductoCarro;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProductoCarro}.
 */
public interface ProductoCarroService {
    /**
     * Save a productoCarro.
     *
     * @param productoCarro the entity to save.
     * @return the persisted entity.
     */
    ProductoCarro save(ProductoCarro productoCarro);

    /**
     * Partially updates a productoCarro.
     *
     * @param productoCarro the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductoCarro> partialUpdate(ProductoCarro productoCarro);

    /**
     * Get all the productoCarros.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoCarro> findAll(Pageable pageable);

    /**
     * Get the "id" productoCarro.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoCarro> findOne(Long id);

    /**
     * Delete the "id" productoCarro.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
