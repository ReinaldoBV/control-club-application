package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.CuentasAsserts.*;
import static cl.controlclub.myapp.domain.CuentasTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CuentasMapperTest {

    private CuentasMapper cuentasMapper;

    @BeforeEach
    void setUp() {
        cuentasMapper = new CuentasMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCuentasSample1();
        var actual = cuentasMapper.toEntity(cuentasMapper.toDto(expected));
        assertCuentasAllPropertiesEquals(expected, actual);
    }
}
