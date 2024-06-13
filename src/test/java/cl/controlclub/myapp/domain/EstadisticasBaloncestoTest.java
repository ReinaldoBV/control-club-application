package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.EstadisticasBaloncestoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadisticasBaloncestoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadisticasBaloncesto.class);
        EstadisticasBaloncesto estadisticasBaloncesto1 = getEstadisticasBaloncestoSample1();
        EstadisticasBaloncesto estadisticasBaloncesto2 = new EstadisticasBaloncesto();
        assertThat(estadisticasBaloncesto1).isNotEqualTo(estadisticasBaloncesto2);

        estadisticasBaloncesto2.setId(estadisticasBaloncesto1.getId());
        assertThat(estadisticasBaloncesto1).isEqualTo(estadisticasBaloncesto2);

        estadisticasBaloncesto2 = getEstadisticasBaloncestoSample2();
        assertThat(estadisticasBaloncesto1).isNotEqualTo(estadisticasBaloncesto2);
    }
}
