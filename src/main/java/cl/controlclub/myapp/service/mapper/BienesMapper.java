package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Bienes;
import cl.controlclub.myapp.service.dto.BienesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bienes} and its DTO {@link BienesDTO}.
 */
@Mapper(componentModel = "spring")
public interface BienesMapper extends EntityMapper<BienesDTO, Bienes> {}
