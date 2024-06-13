package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.AsistenciaAsserts.*;
import static cl.controlclub.myapp.domain.AsistenciaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AsistenciaMapperTest {

    private AsistenciaMapper asistenciaMapper;

    @BeforeEach
    void setUp() {
        asistenciaMapper = new AsistenciaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAsistenciaSample1();
        var actual = asistenciaMapper.toEntity(asistenciaMapper.toDto(expected));
        assertAsistenciaAllPropertiesEquals(expected, actual);
    }
}
