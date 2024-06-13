package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.FinanzasIngresoAsserts.*;
import static cl.controlclub.myapp.domain.FinanzasIngresoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FinanzasIngresoMapperTest {

    private FinanzasIngresoMapper finanzasIngresoMapper;

    @BeforeEach
    void setUp() {
        finanzasIngresoMapper = new FinanzasIngresoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFinanzasIngresoSample1();
        var actual = finanzasIngresoMapper.toEntity(finanzasIngresoMapper.toDto(expected));
        assertFinanzasIngresoAllPropertiesEquals(expected, actual);
    }
}
