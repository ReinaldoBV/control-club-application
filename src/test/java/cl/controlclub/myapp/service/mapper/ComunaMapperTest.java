package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.ComunaAsserts.*;
import static cl.controlclub.myapp.domain.ComunaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComunaMapperTest {

    private ComunaMapper comunaMapper;

    @BeforeEach
    void setUp() {
        comunaMapper = new ComunaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getComunaSample1();
        var actual = comunaMapper.toEntity(comunaMapper.toDto(expected));
        assertComunaAllPropertiesEquals(expected, actual);
    }
}
