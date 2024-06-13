package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.CuerpoTecnico;
import cl.controlclub.myapp.domain.Entrenamiento;
import cl.controlclub.myapp.service.dto.CuerpoTecnicoDTO;
import cl.controlclub.myapp.service.dto.EntrenamientoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Entrenamiento} and its DTO {@link EntrenamientoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EntrenamientoMapper extends EntityMapper<EntrenamientoDTO, Entrenamiento> {
    @Mapping(target = "cuerpoTecnico", source = "cuerpoTecnico", qualifiedByName = "cuerpoTecnicoId")
    EntrenamientoDTO toDto(Entrenamiento s);

    @Named("cuerpoTecnicoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuerpoTecnicoDTO toDtoCuerpoTecnicoId(CuerpoTecnico cuerpoTecnico);
}
