package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Cuentas;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Cuentas entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuentasRepository extends JpaRepository<Cuentas, Long> {}
