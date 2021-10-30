package ar.ed.um.programacion2.repository;

import ar.ed.um.programacion2.domain.CarroCompra;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CarroCompra entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarroCompraRepository extends JpaRepository<CarroCompra, Long> {}
