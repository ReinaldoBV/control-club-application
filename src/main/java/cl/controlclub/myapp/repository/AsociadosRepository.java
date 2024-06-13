package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Asociados;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Asociados entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AsociadosRepository extends JpaRepository<Asociados, Long> {}
