package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.MensajeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MensajeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mensaje.class);
        Mensaje mensaje1 = getMensajeSample1();
        Mensaje mensaje2 = new Mensaje();
        assertThat(mensaje1).isNotEqualTo(mensaje2);

        mensaje2.setId(mensaje1.getId());
        assertThat(mensaje1).isEqualTo(mensaje2);

        mensaje2 = getMensajeSample2();
        assertThat(mensaje1).isNotEqualTo(mensaje2);
    }
}
