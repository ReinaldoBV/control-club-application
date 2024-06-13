package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Comuna;
import cl.controlclub.myapp.service.dto.ComunaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comuna} and its DTO {@link ComunaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ComunaMapper extends EntityMapper<ComunaDTO, Comuna> {}
