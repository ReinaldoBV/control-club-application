package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Entrenamiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Entrenamiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntrenamientoRepository extends JpaRepository<Entrenamiento, Long> {}
