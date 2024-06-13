package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Cuentas;
import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.service.dto.CuentasDTO;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cuentas} and its DTO {@link CuentasDTO}.
 */
@Mapper(componentModel = "spring")
public interface CuentasMapper extends EntityMapper<CuentasDTO, Cuentas> {
    @Mapping(target = "jugador", source = "jugador", qualifiedByName = "jugadorId")
    CuentasDTO toDto(Cuentas s);

    @Named("jugadorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JugadorDTO toDtoJugadorId(Jugador jugador);
}
