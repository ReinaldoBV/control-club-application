package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.TorneosParticipacionesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TorneosParticipacionesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TorneosParticipaciones.class);
        TorneosParticipaciones torneosParticipaciones1 = getTorneosParticipacionesSample1();
        TorneosParticipaciones torneosParticipaciones2 = new TorneosParticipaciones();
        assertThat(torneosParticipaciones1).isNotEqualTo(torneosParticipaciones2);

        torneosParticipaciones2.setId(torneosParticipaciones1.getId());
        assertThat(torneosParticipaciones1).isEqualTo(torneosParticipaciones2);

        torneosParticipaciones2 = getTorneosParticipacionesSample2();
        assertThat(torneosParticipaciones1).isNotEqualTo(torneosParticipaciones2);
    }
}
