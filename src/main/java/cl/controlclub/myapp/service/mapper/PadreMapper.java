package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.domain.Padre;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import cl.controlclub.myapp.service.dto.PadreDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Padre} and its DTO {@link PadreDTO}.
 */
@Mapper(componentModel = "spring")
public interface PadreMapper extends EntityMapper<PadreDTO, Padre> {
    @Mapping(target = "jugador", source = "jugador", qualifiedByName = "jugadorId")
    PadreDTO toDto(Padre s);

    @Named("jugadorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JugadorDTO toDtoJugadorId(Jugador jugador);
}
