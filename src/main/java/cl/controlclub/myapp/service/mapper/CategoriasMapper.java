package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Categorias;
import cl.controlclub.myapp.service.dto.CategoriasDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categorias} and its DTO {@link CategoriasDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoriasMapper extends EntityMapper<CategoriasDTO, Categorias> {}
