package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Categorias;
import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.service.dto.CategoriasDTO;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Jugador} and its DTO {@link JugadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface JugadorMapper extends EntityMapper<JugadorDTO, Jugador> {
    @Mapping(target = "categoria", source = "categoria", qualifiedByName = "categoriasId")
    JugadorDTO toDto(Jugador s);

    @Named("categoriasId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoriasDTO toDtoCategoriasId(Categorias categorias);
}
