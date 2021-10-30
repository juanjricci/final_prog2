package ar.ed.um.programacion2.repository;

import ar.ed.um.programacion2.domain.ProductoVendido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductoVendido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoVendidoRepository extends JpaRepository<ProductoVendido, Long> {}
