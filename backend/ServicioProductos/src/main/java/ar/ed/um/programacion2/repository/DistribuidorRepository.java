package ar.ed.um.programacion2.repository;

import ar.ed.um.programacion2.domain.Distribuidor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Distribuidor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DistribuidorRepository extends JpaRepository<Distribuidor, Long> {}
