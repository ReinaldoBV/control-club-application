package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.CentroEducativo;
import cl.controlclub.myapp.domain.Comuna;
import cl.controlclub.myapp.service.dto.CentroEducativoDTO;
import cl.controlclub.myapp.service.dto.ComunaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CentroEducativo} and its DTO {@link CentroEducativoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CentroEducativoMapper extends EntityMapper<CentroEducativoDTO, CentroEducativo> {
    @Mapping(target = "comuna", source = "comuna", qualifiedByName = "comunaId")
    CentroEducativoDTO toDto(CentroEducativo s);

    @Named("comunaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ComunaDTO toDtoComunaId(Comuna comuna);
}
