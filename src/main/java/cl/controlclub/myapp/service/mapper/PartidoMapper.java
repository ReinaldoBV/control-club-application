package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Partido;
import cl.controlclub.myapp.service.dto.PartidoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Partido} and its DTO {@link PartidoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PartidoMapper extends EntityMapper<PartidoDTO, Partido> {}
