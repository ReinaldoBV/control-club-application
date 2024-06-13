package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Asociados;
import cl.controlclub.myapp.domain.CuerpoTecnico;
import cl.controlclub.myapp.domain.Directivos;
import cl.controlclub.myapp.domain.Jugador;
import cl.controlclub.myapp.domain.Usuario;
import cl.controlclub.myapp.service.dto.AsociadosDTO;
import cl.controlclub.myapp.service.dto.CuerpoTecnicoDTO;
import cl.controlclub.myapp.service.dto.DirectivosDTO;
import cl.controlclub.myapp.service.dto.JugadorDTO;
import cl.controlclub.myapp.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Usuario} and its DTO {@link UsuarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper extends EntityMapper<UsuarioDTO, Usuario> {
    @Mapping(target = "jugador", source = "jugador", qualifiedByName = "jugadorId")
    @Mapping(target = "asociados", source = "asociados", qualifiedByName = "asociadosId")
    @Mapping(target = "directivos", source = "directivos", qualifiedByName = "directivosId")
    @Mapping(target = "cuerpoTecnico", source = "cuerpoTecnico", qualifiedByName = "cuerpoTecnicoId")
    UsuarioDTO toDto(Usuario s);

    @Named("jugadorId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JugadorDTO toDtoJugadorId(Jugador jugador);

    @Named("asociadosId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AsociadosDTO toDtoAsociadosId(Asociados asociados);

    @Named("directivosId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DirectivosDTO toDtoDirectivosId(Directivos directivos);

    @Named("cuerpoTecnicoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuerpoTecnicoDTO toDtoCuerpoTecnicoId(CuerpoTecnico cuerpoTecnico);
}
