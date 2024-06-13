package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.AsociadosTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AsociadosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asociados.class);
        Asociados asociados1 = getAsociadosSample1();
        Asociados asociados2 = new Asociados();
        assertThat(asociados1).isNotEqualTo(asociados2);

        asociados2.setId(asociados1.getId());
        assertThat(asociados1).isEqualTo(asociados2);

        asociados2 = getAsociadosSample2();
        assertThat(asociados1).isNotEqualTo(asociados2);
    }
}
