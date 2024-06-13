package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Asistencia;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Asistencia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {}
