package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.PartidoAsserts.*;
import static cl.controlclub.myapp.domain.PartidoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PartidoMapperTest {

    private PartidoMapper partidoMapper;

    @BeforeEach
    void setUp() {
        partidoMapper = new PartidoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPartidoSample1();
        var actual = partidoMapper.toEntity(partidoMapper.toDto(expected));
        assertPartidoAllPropertiesEquals(expected, actual);
    }
}
