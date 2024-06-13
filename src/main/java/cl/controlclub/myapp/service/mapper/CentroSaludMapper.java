package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.CentroSalud;
import cl.controlclub.myapp.domain.Comuna;
import cl.controlclub.myapp.service.dto.CentroSaludDTO;
import cl.controlclub.myapp.service.dto.ComunaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CentroSalud} and its DTO {@link CentroSaludDTO}.
 */
@Mapper(componentModel = "spring")
public interface CentroSaludMapper extends EntityMapper<CentroSaludDTO, CentroSalud> {
    @Mapping(target = "comuna", source = "comuna", qualifiedByName = "comunaId")
    CentroSaludDTO toDto(CentroSalud s);

    @Named("comunaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ComunaDTO toDtoComunaId(Comuna comuna);
}
