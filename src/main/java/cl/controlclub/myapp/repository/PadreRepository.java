package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Padre;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Padre entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PadreRepository extends JpaRepository<Padre, Long> {}
