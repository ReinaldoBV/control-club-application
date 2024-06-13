package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.FinanzasIngreso;
import cl.controlclub.myapp.service.dto.FinanzasIngresoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FinanzasIngreso} and its DTO {@link FinanzasIngresoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FinanzasIngresoMapper extends EntityMapper<FinanzasIngresoDTO, FinanzasIngreso> {}
