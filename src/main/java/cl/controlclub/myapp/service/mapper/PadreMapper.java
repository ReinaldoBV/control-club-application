package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Padre;
import cl.controlclub.myapp.service.dto.PadreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Padre} and its DTO {@link PadreDTO}.
 */
@Mapper(componentModel = "spring")
public interface PadreMapper extends EntityMapper<PadreDTO, Padre> {}
