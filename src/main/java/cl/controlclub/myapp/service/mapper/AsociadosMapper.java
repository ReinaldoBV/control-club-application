package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Asociados;
import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.service.dto.AsociadosDTO;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Asociados} and its DTO {@link AsociadosDTO}.
 */
@Mapper(componentModel = "spring")
public interface AsociadosMapper extends EntityMapper<AsociadosDTO, Asociados> {
    @Mapping(target = "jugador", source = "jugador", qualifiedByName = "jugadorId")
    AsociadosDTO toDto(Asociados s);

    @Named("jugadorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JugadorDTO toDtoJugadorId(Jugador jugador);
}
