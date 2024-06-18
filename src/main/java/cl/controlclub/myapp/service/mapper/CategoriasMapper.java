package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Categorias;
import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.service.dto.CategoriasDTO;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Categorias} and its DTO {@link CategoriasDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoriasMapper extends EntityMapper<CategoriasDTO, Categorias> {
    @Mapping(target = "jugador", source = "jugador", qualifiedByName = "jugadorId")
    CategoriasDTO toDto(Categorias s);

    @Named("jugadorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JugadorDTO toDtoJugadorId(Jugador jugador);
}
