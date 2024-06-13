package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.PartidoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartidoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partido.class);
        Partido partido1 = getPartidoSample1();
        Partido partido2 = new Partido();
        assertThat(partido1).isNotEqualTo(partido2);

        partido2.setId(partido1.getId());
        assertThat(partido1).isEqualTo(partido2);

        partido2 = getPartidoSample2();
        assertThat(partido1).isNotEqualTo(partido2);
    }
}
