package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.FinanzasIngreso;
import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.service.dto.FinanzasIngresoDTO;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FinanzasIngreso} and its DTO {@link FinanzasIngresoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FinanzasIngresoMapper extends EntityMapper<FinanzasIngresoDTO, FinanzasIngreso> {
    @Mapping(target = "jugador", source = "jugador", qualifiedByName = "jugadorId")
    FinanzasIngresoDTO toDto(FinanzasIngreso s);

    @Named("jugadorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JugadorDTO toDtoJugadorId(Jugador jugador);
}
