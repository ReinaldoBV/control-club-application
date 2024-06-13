package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.FinanzasEgresoAsserts.*;
import static cl.controlclub.myapp.domain.FinanzasEgresoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FinanzasEgresoMapperTest {

    private FinanzasEgresoMapper finanzasEgresoMapper;

    @BeforeEach
    void setUp() {
        finanzasEgresoMapper = new FinanzasEgresoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFinanzasEgresoSample1();
        var actual = finanzasEgresoMapper.toEntity(finanzasEgresoMapper.toDto(expected));
        assertFinanzasEgresoAllPropertiesEquals(expected, actual);
    }
}
