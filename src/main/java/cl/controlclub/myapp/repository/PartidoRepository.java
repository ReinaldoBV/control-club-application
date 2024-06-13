package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Partido;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Partido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PartidoRepository extends JpaRepository<Partido, Long> {}
