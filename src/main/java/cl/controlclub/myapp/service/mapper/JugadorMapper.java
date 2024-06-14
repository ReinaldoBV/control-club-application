package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Categorias;
import cl.controlclub.myapp.domain.CentroEducativo;
import cl.controlclub.myapp.domain.CentroSalud;
import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.domain.PrevisionSalud;
import cl.controlclub.myapp.service.dto.CategoriasDTO;
import cl.controlclub.myapp.service.dto.CentroEducativoDTO;
import cl.controlclub.myapp.service.dto.CentroSaludDTO;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import cl.controlclub.myapp.service.dto.PrevisionSaludDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Jugador} and its DTO {@link JugadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface JugadorMapper extends EntityMapper<JugadorDTO, Jugador> {
    @Mapping(target = "centroSalud", source = "centroSalud", qualifiedByName = "centroSaludId")
    @Mapping(target = "previsionSalud", source = "previsionSalud", qualifiedByName = "previsionSaludId")
    @Mapping(target = "centroEducativo", source = "centroEducativo", qualifiedByName = "centroEducativoId")
    @Mapping(target = "categorias", source = "categorias", qualifiedByName = "categoriasId")
    JugadorDTO toDto(Jugador s);

    @Named("centroSaludId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CentroSaludDTO toDtoCentroSaludId(CentroSalud centroSalud);

    @Named("previsionSaludId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PrevisionSaludDTO toDtoPrevisionSaludId(PrevisionSalud previsionSalud);

    @Named("centroEducativoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CentroEducativoDTO toDtoCentroEducativoId(CentroEducativo centroEducativo);

    @Named("categoriasId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoriasDTO toDtoCategoriasId(Categorias categorias);
}
