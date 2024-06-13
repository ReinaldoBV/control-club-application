package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.CuerpoTecnico;
import cl.controlclub.myapp.service.dto.CuerpoTecnicoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CuerpoTecnico} and its DTO {@link CuerpoTecnicoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CuerpoTecnicoMapper extends EntityMapper<CuerpoTecnicoDTO, CuerpoTecnico> {}
