package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.CuerpoTecnico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CuerpoTecnico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuerpoTecnicoRepository extends JpaRepository<CuerpoTecnico, Long> {}
