package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.EstadisticasBaloncestoAsserts.*;
import static cl.controlclub.myapp.domain.EstadisticasBaloncestoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadisticasBaloncestoMapperTest {

    private EstadisticasBaloncestoMapper estadisticasBaloncestoMapper;

    @BeforeEach
    void setUp() {
        estadisticasBaloncestoMapper = new EstadisticasBaloncestoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEstadisticasBaloncestoSample1();
        var actual = estadisticasBaloncestoMapper.toEntity(estadisticasBaloncestoMapper.toDto(expected));
        assertEstadisticasBaloncestoAllPropertiesEquals(expected, actual);
    }
}
