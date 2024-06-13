package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.FinanzasEgreso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FinanzasEgreso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinanzasEgresoRepository extends JpaRepository<FinanzasEgreso, Long> {}
