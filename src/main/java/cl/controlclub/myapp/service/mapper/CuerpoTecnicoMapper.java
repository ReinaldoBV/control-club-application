package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Asociados;
import cl.controlclub.myapp.domain.CuerpoTecnico;
import cl.controlclub.myapp.service.dto.AsociadosDTO;
import cl.controlclub.myapp.service.dto.CuerpoTecnicoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CuerpoTecnico} and its DTO {@link CuerpoTecnicoDTO}.
 */
@Mapper(componentModel = "spring")
public interface CuerpoTecnicoMapper extends EntityMapper<CuerpoTecnicoDTO, CuerpoTecnico> {
    @Mapping(target = "asociados", source = "asociados", qualifiedByName = "asociadosId")
    CuerpoTecnicoDTO toDto(CuerpoTecnico s);

    @Named("asociadosId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AsociadosDTO toDtoAsociadosId(Asociados asociados);
}
