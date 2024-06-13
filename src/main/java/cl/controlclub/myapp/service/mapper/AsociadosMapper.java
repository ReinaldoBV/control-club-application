package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Asociados;
import cl.controlclub.myapp.service.dto.AsociadosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Asociados} and its DTO {@link AsociadosDTO}.
 */
@Mapper(componentModel = "spring")
public interface AsociadosMapper extends EntityMapper<AsociadosDTO, Asociados> {}
