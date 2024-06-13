package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.CentroSalud;
import cl.controlclub.myapp.service.dto.CentroSaludDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CentroSalud} and its DTO {@link CentroSaludDTO}.
 */
@Mapper(componentModel = "spring")
public interface CentroSaludMapper extends EntityMapper<CentroSaludDTO, CentroSalud> {}
