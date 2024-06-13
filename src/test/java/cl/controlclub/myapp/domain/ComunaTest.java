package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.CentroEducativoTestSamples.*;
import static cl.controlclub.myapp.domain.CentroSaludTestSamples.*;
import static cl.controlclub.myapp.domain.ComunaTestSamples.*;
import static cl.controlclub.myapp.domain.JugadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ComunaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comuna.class);
        Comuna comuna1 = getComunaSample1();
        Comuna comuna2 = new Comuna();
        assertThat(comuna1).isNotEqualTo(comuna2);

        comuna2.setId(comuna1.getId());
        assertThat(comuna1).isEqualTo(comuna2);

        comuna2 = getComunaSample2();
        assertThat(comuna1).isNotEqualTo(comuna2);
    }

    @Test
    void jugadorTest() {
        Comuna comuna = getComunaRandomSampleGenerator();
        Jugador jugadorBack = getJugadorRandomSampleGenerator();

        comuna.setJugador(jugadorBack);
        assertThat(comuna.getJugador()).isEqualTo(jugadorBack);
        assertThat(jugadorBack.getComuna()).isEqualTo(comuna);

        comuna.jugador(null);
        assertThat(comuna.getJugador()).isNull();
        assertThat(jugadorBack.getComuna()).isNull();
    }

    @Test
    void centroSaludTest() {
        Comuna comuna = getComunaRandomSampleGenerator();
        CentroSalud centroSaludBack = getCentroSaludRandomSampleGenerator();

        comuna.addCentroSalud(centroSaludBack);
        assertThat(comuna.getCentroSaluds()).containsOnly(centroSaludBack);
        assertThat(centroSaludBack.getComuna()).isEqualTo(comuna);

        comuna.removeCentroSalud(centroSaludBack);
        assertThat(comuna.getCentroSaluds()).doesNotContain(centroSaludBack);
        assertThat(centroSaludBack.getComuna()).isNull();

        comuna.centroSaluds(new HashSet<>(Set.of(centroSaludBack)));
        assertThat(comuna.getCentroSaluds()).containsOnly(centroSaludBack);
        assertThat(centroSaludBack.getComuna()).isEqualTo(comuna);

        comuna.setCentroSaluds(new HashSet<>());
        assertThat(comuna.getCentroSaluds()).doesNotContain(centroSaludBack);
        assertThat(centroSaludBack.getComuna()).isNull();
    }

    @Test
    void centroEducativoTest() {
        Comuna comuna = getComunaRandomSampleGenerator();
        CentroEducativo centroEducativoBack = getCentroEducativoRandomSampleGenerator();

        comuna.addCentroEducativo(centroEducativoBack);
        assertThat(comuna.getCentroEducativos()).containsOnly(centroEducativoBack);
        assertThat(centroEducativoBack.getComuna()).isEqualTo(comuna);

        comuna.removeCentroEducativo(centroEducativoBack);
        assertThat(comuna.getCentroEducativos()).doesNotContain(centroEducativoBack);
        assertThat(centroEducativoBack.getComuna()).isNull();

        comuna.centroEducativos(new HashSet<>(Set.of(centroEducativoBack)));
        assertThat(comuna.getCentroEducativos()).containsOnly(centroEducativoBack);
        assertThat(centroEducativoBack.getComuna()).isEqualTo(comuna);

        comuna.setCentroEducativos(new HashSet<>());
        assertThat(comuna.getCentroEducativos()).doesNotContain(centroEducativoBack);
        assertThat(centroEducativoBack.getComuna()).isNull();
    }
}
