package ar.ed.um.programacion2.repository;

import ar.ed.um.programacion2.domain.ProductoCarro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductoCarro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductoCarroRepository extends JpaRepository<ProductoCarro, Long> {}
