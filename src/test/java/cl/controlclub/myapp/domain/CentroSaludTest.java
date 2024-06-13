package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.CentroSaludTestSamples.*;
import static cl.controlclub.myapp.domain.ComunaTestSamples.*;
import static cl.controlclub.myapp.domain.JugadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CentroSaludTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroSalud.class);
        CentroSalud centroSalud1 = getCentroSaludSample1();
        CentroSalud centroSalud2 = new CentroSalud();
        assertThat(centroSalud1).isNotEqualTo(centroSalud2);

        centroSalud2.setId(centroSalud1.getId());
        assertThat(centroSalud1).isEqualTo(centroSalud2);

        centroSalud2 = getCentroSaludSample2();
        assertThat(centroSalud1).isNotEqualTo(centroSalud2);
    }

    @Test
    void comunaTest() {
        CentroSalud centroSalud = getCentroSaludRandomSampleGenerator();
        Comuna comunaBack = getComunaRandomSampleGenerator();

        centroSalud.setComuna(comunaBack);
        assertThat(centroSalud.getComuna()).isEqualTo(comunaBack);

        centroSalud.comuna(null);
        assertThat(centroSalud.getComuna()).isNull();
    }

    @Test
    void jugadorTest() {
        CentroSalud centroSalud = getCentroSaludRandomSampleGenerator();
        Jugador jugadorBack = getJugadorRandomSampleGenerator();

        centroSalud.setJugador(jugadorBack);
        assertThat(centroSalud.getJugador()).isEqualTo(jugadorBack);
        assertThat(jugadorBack.getCentroSalud()).isEqualTo(centroSalud);

        centroSalud.jugador(null);
        assertThat(centroSalud.getJugador()).isNull();
        assertThat(jugadorBack.getCentroSalud()).isNull();
    }
}
