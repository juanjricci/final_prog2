package ar.ed.um.programacion2.service;

import ar.ed.um.programacion2.domain.ProductoVendido;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link ProductoVendido}.
 */
public interface ProductoVendidoService {
    /**
     * Save a productoVendido.
     *
     * @param productoVendido the entity to save.
     * @return the persisted entity.
     */
    ProductoVendido save(ProductoVendido productoVendido);

    /**
     * Partially updates a productoVendido.
     *
     * @param productoVendido the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductoVendido> partialUpdate(ProductoVendido productoVendido);

    /**
     * Get all the productoVendidos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProductoVendido> findAll(Pageable pageable);

    /**
     * Get the "id" productoVendido.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductoVendido> findOne(Long id);

    /**
     * Delete the "id" productoVendido.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
