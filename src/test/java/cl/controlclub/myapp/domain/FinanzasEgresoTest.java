package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.CuentasTestSamples.*;
import static cl.controlclub.myapp.domain.FinanzasEgresoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FinanzasEgresoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanzasEgreso.class);
        FinanzasEgreso finanzasEgreso1 = getFinanzasEgresoSample1();
        FinanzasEgreso finanzasEgreso2 = new FinanzasEgreso();
        assertThat(finanzasEgreso1).isNotEqualTo(finanzasEgreso2);

        finanzasEgreso2.setId(finanzasEgreso1.getId());
        assertThat(finanzasEgreso1).isEqualTo(finanzasEgreso2);

        finanzasEgreso2 = getFinanzasEgresoSample2();
        assertThat(finanzasEgreso1).isNotEqualTo(finanzasEgreso2);
    }

    @Test
    void cuentasTest() {
        FinanzasEgreso finanzasEgreso = getFinanzasEgresoRandomSampleGenerator();
        Cuentas cuentasBack = getCuentasRandomSampleGenerator();

        finanzasEgreso.setCuentas(cuentasBack);
        assertThat(finanzasEgreso.getCuentas()).isEqualTo(cuentasBack);

        finanzasEgreso.cuentas(null);
        assertThat(finanzasEgreso.getCuentas()).isNull();
    }
}
