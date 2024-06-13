package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.FinanzasIngresoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FinanzasIngresoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanzasIngreso.class);
        FinanzasIngreso finanzasIngreso1 = getFinanzasIngresoSample1();
        FinanzasIngreso finanzasIngreso2 = new FinanzasIngreso();
        assertThat(finanzasIngreso1).isNotEqualTo(finanzasIngreso2);

        finanzasIngreso2.setId(finanzasIngreso1.getId());
        assertThat(finanzasIngreso1).isEqualTo(finanzasIngreso2);

        finanzasIngreso2 = getFinanzasIngresoSample2();
        assertThat(finanzasIngreso1).isNotEqualTo(finanzasIngreso2);
    }
}
