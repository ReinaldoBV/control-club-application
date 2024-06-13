package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.EntrenamientoAsserts.*;
import static cl.controlclub.myapp.domain.EntrenamientoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntrenamientoMapperTest {

    private EntrenamientoMapper entrenamientoMapper;

    @BeforeEach
    void setUp() {
        entrenamientoMapper = new EntrenamientoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEntrenamientoSample1();
        var actual = entrenamientoMapper.toEntity(entrenamientoMapper.toDto(expected));
        assertEntrenamientoAllPropertiesEquals(expected, actual);
    }
}
