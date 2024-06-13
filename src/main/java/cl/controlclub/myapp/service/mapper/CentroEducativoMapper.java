package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.CentroEducativo;
import cl.controlclub.myapp.service.dto.CentroEducativoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CentroEducativo} and its DTO {@link CentroEducativoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CentroEducativoMapper extends EntityMapper<CentroEducativoDTO, CentroEducativo> {}
