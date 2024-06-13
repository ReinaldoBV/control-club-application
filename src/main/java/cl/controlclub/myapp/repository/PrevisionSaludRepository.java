package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.PrevisionSalud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PrevisionSalud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrevisionSaludRepository extends JpaRepository<PrevisionSalud, Long> {}
