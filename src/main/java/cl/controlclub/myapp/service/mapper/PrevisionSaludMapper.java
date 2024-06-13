package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.PrevisionSalud;
import cl.controlclub.myapp.service.dto.PrevisionSaludDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PrevisionSalud} and its DTO {@link PrevisionSaludDTO}.
 */
@Mapper(componentModel = "spring")
public interface PrevisionSaludMapper extends EntityMapper<PrevisionSaludDTO, PrevisionSalud> {}
