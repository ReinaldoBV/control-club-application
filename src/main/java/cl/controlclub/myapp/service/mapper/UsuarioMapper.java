package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Usuario;
import cl.controlclub.myapp.service.dto.UsuarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Usuario} and its DTO {@link UsuarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface UsuarioMapper extends EntityMapper<UsuarioDTO, Usuario> {}
