package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.TorneosParticipaciones;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TorneosParticipaciones entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TorneosParticipacionesRepository extends JpaRepository<TorneosParticipaciones, Long> {}
