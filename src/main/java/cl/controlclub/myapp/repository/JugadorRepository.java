package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Jugador;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Jugador entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {}
