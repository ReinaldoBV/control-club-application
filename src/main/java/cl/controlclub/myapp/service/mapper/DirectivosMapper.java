package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Directivos;
import cl.controlclub.myapp.service.dto.DirectivosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Directivos} and its DTO {@link DirectivosDTO}.
 */
@Mapper(componentModel = "spring")
public interface DirectivosMapper extends EntityMapper<DirectivosDTO, Directivos> {}
