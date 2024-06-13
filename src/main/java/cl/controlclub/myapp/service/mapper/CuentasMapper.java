package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Cuentas;
import cl.controlclub.myapp.service.dto.CuentasDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cuentas} and its DTO {@link CuentasDTO}.
 */
@Mapper(componentModel = "spring")
public interface CuentasMapper extends EntityMapper<CuentasDTO, Cuentas> {}
