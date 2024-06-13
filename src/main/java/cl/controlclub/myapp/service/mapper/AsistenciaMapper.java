package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Asistencia;
import cl.controlclub.myapp.service.dto.AsistenciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Asistencia} and its DTO {@link AsistenciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AsistenciaMapper extends EntityMapper<AsistenciaDTO, Asistencia> {}
