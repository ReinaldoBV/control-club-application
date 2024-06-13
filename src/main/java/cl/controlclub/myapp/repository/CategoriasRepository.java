package cl.controlclub.myapp.repository;

import cl.controlclub.myapp.domain.Categorias;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Categorias entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategoriasRepository extends JpaRepository<Categorias, Long> {}
