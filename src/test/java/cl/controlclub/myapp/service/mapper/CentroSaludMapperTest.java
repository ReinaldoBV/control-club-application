package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.CentroSaludAsserts.*;
import static cl.controlclub.myapp.domain.CentroSaludTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CentroSaludMapperTest {

    private CentroSaludMapper centroSaludMapper;

    @BeforeEach
    void setUp() {
        centroSaludMapper = new CentroSaludMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCentroSaludSample1();
        var actual = centroSaludMapper.toEntity(centroSaludMapper.toDto(expected));
        assertCentroSaludAllPropertiesEquals(expected, actual);
    }
}
