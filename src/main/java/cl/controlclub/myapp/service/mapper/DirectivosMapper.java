package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Asociados;
import cl.controlclub.myapp.domain.Directivos;
import cl.controlclub.myapp.service.dto.AsociadosDTO;
import cl.controlclub.myapp.service.dto.DirectivosDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Directivos} and its DTO {@link DirectivosDTO}.
 */
@Mapper(componentModel = "spring")
public interface DirectivosMapper extends EntityMapper<DirectivosDTO, Directivos> {
    @Mapping(target = "asociados", source = "asociados", qualifiedByName = "asociadosId")
    DirectivosDTO toDto(Directivos s);

    @Named("asociadosId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AsociadosDTO toDtoAsociadosId(Asociados asociados);
}
