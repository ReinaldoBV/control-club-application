package cl.controlclub.myapp.service.mapper;

import cl.controlclub.myapp.domain.Club;
import cl.controlclub.myapp.service.dto.ClubDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Club} and its DTO {@link ClubDTO}.
 */
@Mapper(componentModel = "spring")
public interface ClubMapper extends EntityMapper<ClubDTO, Club> {}
