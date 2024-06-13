package cl.controlclub.myapp.service.mapper;

import static cl.controlclub.myapp.domain.MensajeAsserts.*;
import static cl.controlclub.myapp.domain.MensajeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MensajeMapperTest {

    private MensajeMapper mensajeMapper;

    @BeforeEach
    void setUp() {
        mensajeMapper = new MensajeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMensajeSample1();
        var actual = mensajeMapper.toEntity(mensajeMapper.toDto(expected));
        assertMensajeAllPropertiesEquals(expected, actual);
    }
}
