package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.TorneosParticipaciones;
import cl.controlclub.myapp.service.dto.TorneosParticipacionesDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TorneosParticipaciones} and its DTO {@link TorneosParticipacionesDTO}.
 */
@Mapper(componentModel = "spring")
public interface TorneosParticipacionesMapper extends EntityMapper<TorneosParticipacionesDTO, TorneosParticipaciones> {}
