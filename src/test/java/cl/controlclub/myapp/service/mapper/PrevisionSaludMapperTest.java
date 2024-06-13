package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.PrevisionSaludAsserts.*;
import static cl.controlclub.myapp.domain.PrevisionSaludTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrevisionSaludMapperTest {

    private PrevisionSaludMapper previsionSaludMapper;

    @BeforeEach
    void setUp() {
        previsionSaludMapper = new PrevisionSaludMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPrevisionSaludSample1();
        var actual = previsionSaludMapper.toEntity(previsionSaludMapper.toDto(expected));
        assertPrevisionSaludAllPropertiesEquals(expected, actual);
    }
}
