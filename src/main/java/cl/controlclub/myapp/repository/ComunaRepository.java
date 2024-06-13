package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Comuna;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Comuna entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Long> {}
