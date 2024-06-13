package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.CuerpoTecnicoAsserts.*;
import static cl.controlclub.myapp.domain.CuerpoTecnicoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CuerpoTecnicoMapperTest {

    private CuerpoTecnicoMapper cuerpoTecnicoMapper;

    @BeforeEach
    void setUp() {
        cuerpoTecnicoMapper = new CuerpoTecnicoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCuerpoTecnicoSample1();
        var actual = cuerpoTecnicoMapper.toEntity(cuerpoTecnicoMapper.toDto(expected));
        assertCuerpoTecnicoAllPropertiesEquals(expected, actual);
    }
}
