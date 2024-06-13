package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.CuentasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CuentasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cuentas.class);
        Cuentas cuentas1 = getCuentasSample1();
        Cuentas cuentas2 = new Cuentas();
        assertThat(cuentas1).isNotEqualTo(cuentas2);

        cuentas2.setId(cuentas1.getId());
        assertThat(cuentas1).isEqualTo(cuentas2);

        cuentas2 = getCuentasSample2();
        assertThat(cuentas1).isNotEqualTo(cuentas2);
    }
}
