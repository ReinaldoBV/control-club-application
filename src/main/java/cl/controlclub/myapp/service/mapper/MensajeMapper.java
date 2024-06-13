package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.domain.Mensaje;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import cl.controlclub.myapp.service.dto.MensajeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Mensaje} and its DTO {@link MensajeDTO}.
 */
@Mapper(componentModel = "spring")
public interface MensajeMapper extends EntityMapper<MensajeDTO, Mensaje> {
    @Mapping(target = "remitente", source = "remitente", qualifiedByName = "jugadorId")
    @Mapping(target = "destinatario", source = "destinatario", qualifiedByName = "jugadorId")
    MensajeDTO toDto(Mensaje s);

    @Named("jugadorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JugadorDTO toDtoJugadorId(Jugador jugador);
}
