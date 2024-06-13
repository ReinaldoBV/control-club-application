package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.PadreTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PadreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Padre.class);
        Padre padre1 = getPadreSample1();
        Padre padre2 = new Padre();
        assertThat(padre1).isNotEqualTo(padre2);

        padre2.setId(padre1.getId());
        assertThat(padre1).isEqualTo(padre2);

        padre2 = getPadreSample2();
        assertThat(padre1).isNotEqualTo(padre2);
    }
}
