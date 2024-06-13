package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.CategoriasAsserts.*;
import static cl.controlclub.myapp.domain.CategoriasTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CategoriasMapperTest {

    private CategoriasMapper categoriasMapper;

    @BeforeEach
    void setUp() {
        categoriasMapper = new CategoriasMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCategoriasSample1();
        var actual = categoriasMapper.toEntity(categoriasMapper.toDto(expected));
        assertCategoriasAllPropertiesEquals(expected, actual);
    }
}
