package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Mensaje;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Mensaje entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {}
