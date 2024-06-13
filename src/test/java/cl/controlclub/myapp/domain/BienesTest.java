package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.BienesTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BienesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bienes.class);
        Bienes bienes1 = getBienesSample1();
        Bienes bienes2 = new Bienes();
        assertThat(bienes1).isNotEqualTo(bienes2);

        bienes2.setId(bienes1.getId());
        assertThat(bienes1).isEqualTo(bienes2);

        bienes2 = getBienesSample2();
        assertThat(bienes1).isNotEqualTo(bienes2);
    }
}
