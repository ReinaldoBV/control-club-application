package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.PadreAsserts.*;
import static cl.controlclub.myapp.domain.PadreTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PadreMapperTest {

    private PadreMapper padreMapper;

    @BeforeEach
    void setUp() {
        padreMapper = new PadreMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPadreSample1();
        var actual = padreMapper.toEntity(padreMapper.toDto(expected));
        assertPadreAllPropertiesEquals(expected, actual);
    }
}
