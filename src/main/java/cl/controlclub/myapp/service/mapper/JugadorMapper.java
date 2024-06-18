package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Jugador} and its DTO {@link JugadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface JugadorMapper extends EntityMapper<JugadorDTO, Jugador> {}
