package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.CentroSalud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CentroSalud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentroSaludRepository extends JpaRepository<CentroSalud, Long> {}
