package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.AsociadosAsserts.*;
import static cl.controlclub.myapp.domain.AsociadosTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AsociadosMapperTest {

    private AsociadosMapper asociadosMapper;

    @BeforeEach
    void setUp() {
        asociadosMapper = new AsociadosMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAsociadosSample1();
        var actual = asociadosMapper.toEntity(asociadosMapper.toDto(expected));
        assertAsociadosAllPropertiesEquals(expected, actual);
    }
}
