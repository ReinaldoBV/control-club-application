package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.BienesAsserts.*;
import static cl.controlclub.myapp.domain.BienesTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BienesMapperTest {

    private BienesMapper bienesMapper;

    @BeforeEach
    void setUp() {
        bienesMapper = new BienesMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBienesSample1();
        var actual = bienesMapper.toEntity(bienesMapper.toDto(expected));
        assertBienesAllPropertiesEquals(expected, actual);
    }
}
