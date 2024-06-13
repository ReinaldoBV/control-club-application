package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.DirectivosAsserts.*;
import static cl.controlclub.myapp.domain.DirectivosTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DirectivosMapperTest {

    private DirectivosMapper directivosMapper;

    @BeforeEach
    void setUp() {
        directivosMapper = new DirectivosMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDirectivosSample1();
        var actual = directivosMapper.toEntity(directivosMapper.toDto(expected));
        assertDirectivosAllPropertiesEquals(expected, actual);
    }
}
