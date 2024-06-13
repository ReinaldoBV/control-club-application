package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Bienes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Bienes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BienesRepository extends JpaRepository<Bienes, Long> {}
