package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.FinanzasIngreso;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FinanzasIngreso entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinanzasIngresoRepository extends JpaRepository<FinanzasIngreso, Long> {}
