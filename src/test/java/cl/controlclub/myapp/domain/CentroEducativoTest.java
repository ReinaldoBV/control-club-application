package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.CentroEducativoTestSamples.*;
import static cl.controlclub.myapp.domain.ComunaTestSamples.*;
import static cl.controlclub.myapp.domain.JugadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CentroEducativoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroEducativo.class);
        CentroEducativo centroEducativo1 = getCentroEducativoSample1();
        CentroEducativo centroEducativo2 = new CentroEducativo();
        assertThat(centroEducativo1).isNotEqualTo(centroEducativo2);

        centroEducativo2.setId(centroEducativo1.getId());
        assertThat(centroEducativo1).isEqualTo(centroEducativo2);

        centroEducativo2 = getCentroEducativoSample2();
        assertThat(centroEducativo1).isNotEqualTo(centroEducativo2);
    }

    @Test
    void comunaTest() {
        CentroEducativo centroEducativo = getCentroEducativoRandomSampleGenerator();
        Comuna comunaBack = getComunaRandomSampleGenerator();

        centroEducativo.setComuna(comunaBack);
        assertThat(centroEducativo.getComuna()).isEqualTo(comunaBack);

        centroEducativo.comuna(null);
        assertThat(centroEducativo.getComuna()).isNull();
    }

    @Test
    void jugadorTest() {
        CentroEducativo centroEducativo = getCentroEducativoRandomSampleGenerator();
        Jugador jugadorBack = getJugadorRandomSampleGenerator();

        centroEducativo.setJugador(jugadorBack);
        assertThat(centroEducativo.getJugador()).isEqualTo(jugadorBack);
        assertThat(jugadorBack.getCentroEducativo()).isEqualTo(centroEducativo);

        centroEducativo.jugador(null);
        assertThat(centroEducativo.getJugador()).isNull();
        assertThat(jugadorBack.getCentroEducativo()).isNull();
    }
}
