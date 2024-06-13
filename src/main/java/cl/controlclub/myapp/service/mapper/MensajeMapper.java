package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Mensaje;
import cl.controlclub.myapp.service.dto.MensajeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mensaje} and its DTO {@link MensajeDTO}.
 */
@Mapper(componentModel = "spring")
public interface MensajeMapper extends EntityMapper<MensajeDTO, Mensaje> {}
