package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.EstadisticasBaloncesto;
import cl.controlclub.myapp.service.dto.EstadisticasBaloncestoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstadisticasBaloncesto} and its DTO {@link EstadisticasBaloncestoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstadisticasBaloncestoMapper extends EntityMapper<EstadisticasBaloncestoDTO, EstadisticasBaloncesto> {}
