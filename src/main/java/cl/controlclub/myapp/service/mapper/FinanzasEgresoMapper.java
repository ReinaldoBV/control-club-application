package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Cuentas;
import cl.controlclub.myapp.domain.FinanzasEgreso;
import cl.controlclub.myapp.service.dto.CuentasDTO;
import cl.controlclub.myapp.service.dto.FinanzasEgresoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FinanzasEgreso} and its DTO {@link FinanzasEgresoDTO}.
 */
@Mapper(componentModel = "spring")
public interface FinanzasEgresoMapper extends EntityMapper<FinanzasEgresoDTO, FinanzasEgreso> {
    @Mapping(target = "cuentas", source = "cuentas", qualifiedByName = "cuentasId")
    FinanzasEgresoDTO toDto(FinanzasEgreso s);

    @Named("cuentasId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CuentasDTO toDtoCuentasId(Cuentas cuentas);
}
