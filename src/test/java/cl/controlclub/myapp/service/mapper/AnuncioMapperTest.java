package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.AnuncioAsserts.*;
import static cl.controlclub.myapp.domain.AnuncioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnuncioMapperTest {

    private AnuncioMapper anuncioMapper;

    @BeforeEach
    void setUp() {
        anuncioMapper = new AnuncioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAnuncioSample1();
        var actual = anuncioMapper.toEntity(anuncioMapper.toDto(expected));
        assertAnuncioAllPropertiesEquals(expected, actual);
    }
}
