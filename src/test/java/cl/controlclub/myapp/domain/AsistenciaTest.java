package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.AsistenciaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AsistenciaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asistencia.class);
        Asistencia asistencia1 = getAsistenciaSample1();
        Asistencia asistencia2 = new Asistencia();
        assertThat(asistencia1).isNotEqualTo(asistencia2);

        asistencia2.setId(asistencia1.getId());
        assertThat(asistencia1).isEqualTo(asistencia2);

        asistencia2 = getAsistenciaSample2();
        assertThat(asistencia1).isNotEqualTo(asistencia2);
    }
}
