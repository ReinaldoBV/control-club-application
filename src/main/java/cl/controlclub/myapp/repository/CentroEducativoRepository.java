package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.CentroEducativo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CentroEducativo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CentroEducativoRepository extends JpaRepository<CentroEducativo, Long> {}
