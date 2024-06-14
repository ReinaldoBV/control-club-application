package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Asociados;
import cl.controlclub.myapp.domain.CuerpoTecnico;
import cl.controlclub.myapp.domain.Directivos;
import cl.controlclub.myapp.service.dto.AsociadosDTO;
import cl.controlclub.myapp.service.dto.CuerpoTecnicoDTO;
import cl.controlclub.myapp.service.dto.DirectivosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Asociados} and its DTO {@link AsociadosDTO}.
 */
@Mapper(componentModel = "spring")
public interface AsociadosMapper extends EntityMapper<AsociadosDTO, Asociados> {
    @Mapping(target = "directivos", source = "directivos", qualifiedByName = "directivosId")
    @Mapping(target = "cuerpoTecnico", source = "cuerpoTecnico", qualifiedByName = "cuerpoTecnicoId")
    AsociadosDTO toDto(Asociados s);

    @Named("directivosId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DirectivosDTO toDtoDirectivosId(Directivos directivos);

    @Named("cuerpoTecnicoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuerpoTecnicoDTO toDtoCuerpoTecnicoId(CuerpoTecnico cuerpoTecnico);
}
