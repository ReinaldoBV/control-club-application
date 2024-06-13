package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.FinanzasEgreso;
import cl.controlclub.myapp.service.dto.FinanzasEgresoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FinanzasEgreso} and its DTO {@link FinanzasEgresoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FinanzasEgresoMapper extends EntityMapper<FinanzasEgresoDTO, FinanzasEgreso> {}
