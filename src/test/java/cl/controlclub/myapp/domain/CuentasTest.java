package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.CuentasTestSamples.*;
import static cl.controlclub.myapp.domain.FinanzasEgresoTestSamples.*;
import static cl.controlclub.myapp.domain.JugadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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

    @Test
    void jugadorTest() {
        Cuentas cuentas = getCuentasRandomSampleGenerator();
        Jugador jugadorBack = getJugadorRandomSampleGenerator();

        cuentas.setJugador(jugadorBack);
        assertThat(cuentas.getJugador()).isEqualTo(jugadorBack);

        cuentas.jugador(null);
        assertThat(cuentas.getJugador()).isNull();
    }

    @Test
    void finanzasEgresoTest() {
        Cuentas cuentas = getCuentasRandomSampleGenerator();
        FinanzasEgreso finanzasEgresoBack = getFinanzasEgresoRandomSampleGenerator();

        cuentas.addFinanzasEgreso(finanzasEgresoBack);
        assertThat(cuentas.getFinanzasEgresos()).containsOnly(finanzasEgresoBack);
        assertThat(finanzasEgresoBack.getCuentas()).isEqualTo(cuentas);

        cuentas.removeFinanzasEgreso(finanzasEgresoBack);
        assertThat(cuentas.getFinanzasEgresos()).doesNotContain(finanzasEgresoBack);
        assertThat(finanzasEgresoBack.getCuentas()).isNull();

        cuentas.finanzasEgresos(new HashSet<>(Set.of(finanzasEgresoBack)));
        assertThat(cuentas.getFinanzasEgresos()).containsOnly(finanzasEgresoBack);
        assertThat(finanzasEgresoBack.getCuentas()).isEqualTo(cuentas);

        cuentas.setFinanzasEgresos(new HashSet<>());
        assertThat(cuentas.getFinanzasEgresos()).doesNotContain(finanzasEgresoBack);
        assertThat(finanzasEgresoBack.getCuentas()).isNull();
    }
}
