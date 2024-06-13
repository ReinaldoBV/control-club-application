package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.CentroEducativoAsserts.*;
import static cl.controlclub.myapp.domain.CentroEducativoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CentroEducativoMapperTest {

    private CentroEducativoMapper centroEducativoMapper;

    @BeforeEach
    void setUp() {
        centroEducativoMapper = new CentroEducativoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCentroEducativoSample1();
        var actual = centroEducativoMapper.toEntity(centroEducativoMapper.toDto(expected));
        assertCentroEducativoAllPropertiesEquals(expected, actual);
    }
}
