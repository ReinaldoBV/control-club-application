package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.EstadisticasBaloncesto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the EstadisticasBaloncesto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadisticasBaloncestoRepository extends JpaRepository<EstadisticasBaloncesto, Long> {}
