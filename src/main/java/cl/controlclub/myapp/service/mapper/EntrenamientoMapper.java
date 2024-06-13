package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Entrenamiento;
import cl.controlclub.myapp.service.dto.EntrenamientoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entrenamiento} and its DTO {@link EntrenamientoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EntrenamientoMapper extends EntityMapper<EntrenamientoDTO, Entrenamiento> {}
