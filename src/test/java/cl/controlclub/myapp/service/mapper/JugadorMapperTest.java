package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.JugadorAsserts.*;
import static cl.controlclub.myapp.domain.JugadorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JugadorMapperTest {

    private JugadorMapper jugadorMapper;

    @BeforeEach
    void setUp() {
        jugadorMapper = new JugadorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getJugadorSample1();
        var actual = jugadorMapper.toEntity(jugadorMapper.toDto(expected));
        assertJugadorAllPropertiesEquals(expected, actual);
    }
}
