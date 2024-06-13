package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Directivos;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Directivos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DirectivosRepository extends JpaRepository<Directivos, Long> {}
