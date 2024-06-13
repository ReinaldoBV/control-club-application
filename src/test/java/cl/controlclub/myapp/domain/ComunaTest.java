package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.ComunaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
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
}
