package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Bienes;
import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.service.dto.BienesDTO;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bienes} and its DTO {@link BienesDTO}.
 */
@Mapper(componentModel = "spring")
public interface BienesMapper extends EntityMapper<BienesDTO, Bienes> {
    @Mapping(target = "asignadoA", source = "asignadoA", qualifiedByName = "jugadorId")
    BienesDTO toDto(Bienes s);

    @Named("jugadorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JugadorDTO toDtoJugadorId(Jugador jugador);
}
