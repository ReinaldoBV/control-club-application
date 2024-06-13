package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.TorneosParticipacionesAsserts.*;
import static cl.controlclub.myapp.domain.TorneosParticipacionesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TorneosParticipacionesMapperTest {

    private TorneosParticipacionesMapper torneosParticipacionesMapper;

    @BeforeEach
    void setUp() {
        torneosParticipacionesMapper = new TorneosParticipacionesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTorneosParticipacionesSample1();
        var actual = torneosParticipacionesMapper.toEntity(torneosParticipacionesMapper.toDto(expected));
        assertTorneosParticipacionesAllPropertiesEquals(expected, actual);
    }
}
