package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.CentroEducativoTestSamples.*;
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
}
