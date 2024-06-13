package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Anuncio;
import cl.controlclub.myapp.service.dto.AnuncioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Anuncio} and its DTO {@link AnuncioDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnuncioMapper extends EntityMapper<AnuncioDTO, Anuncio> {}
