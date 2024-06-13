package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.PrevisionSaludTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrevisionSaludTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrevisionSalud.class);
        PrevisionSalud previsionSalud1 = getPrevisionSaludSample1();
        PrevisionSalud previsionSalud2 = new PrevisionSalud();
        assertThat(previsionSalud1).isNotEqualTo(previsionSalud2);

        previsionSalud2.setId(previsionSalud1.getId());
        assertThat(previsionSalud1).isEqualTo(previsionSalud2);

        previsionSalud2 = getPrevisionSaludSample2();
        assertThat(previsionSalud1).isNotEqualTo(previsionSalud2);
    }
}
