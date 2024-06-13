package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.AsociadosTestSamples.*;
import static cl.controlclub.myapp.domain.CategoriasTestSamples.*;
import static cl.controlclub.myapp.domain.CentroEducativoTestSamples.*;
import static cl.controlclub.myapp.domain.CentroSaludTestSamples.*;
import static cl.controlclub.myapp.domain.ComunaTestSamples.*;
import static cl.controlclub.myapp.domain.CuentasTestSamples.*;
import static cl.controlclub.myapp.domain.FinanzasIngresoTestSamples.*;
import static cl.controlclub.myapp.domain.JugadorTestSamples.*;
import static cl.controlclub.myapp.domain.PadreTestSamples.*;
import static cl.controlclub.myapp.domain.PrevisionSaludTestSamples.*;
import static cl.controlclub.myapp.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class JugadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Jugador.class);
        Jugador jugador1 = getJugadorSample1();
        Jugador jugador2 = new Jugador();
        assertThat(jugador1).isNotEqualTo(jugador2);

        jugador2.setId(jugador1.getId());
        assertThat(jugador1).isEqualTo(jugador2);

        jugador2 = getJugadorSample2();
        assertThat(jugador1).isNotEqualTo(jugador2);
    }

    @Test
    void centroSaludTest() {
        Jugador jugador = getJugadorRandomSampleGenerator();
        CentroSalud centroSaludBack = getCentroSaludRandomSampleGenerator();

        jugador.setCentroSalud(centroSaludBack);
        assertThat(jugador.getCentroSalud()).isEqualTo(centroSaludBack);

        jugador.centroSalud(null);
        assertThat(jugador.getCentroSalud()).isNull();
    }

    @Test
    void previsionSaludTest() {
        Jugador jugador = getJugadorRandomSampleGenerator();
        PrevisionSalud previsionSaludBack = getPrevisionSaludRandomSampleGenerator();

        jugador.setPrevisionSalud(previsionSaludBack);
        assertThat(jugador.getPrevisionSalud()).isEqualTo(previsionSaludBack);

        jugador.previsionSalud(null);
        assertThat(jugador.getPrevisionSalud()).isNull();
    }

    @Test
    void comunaTest() {
        Jugador jugador = getJugadorRandomSampleGenerator();
        Comuna comunaBack = getComunaRandomSampleGenerator();

        jugador.setComuna(comunaBack);
        assertThat(jugador.getComuna()).isEqualTo(comunaBack);

        jugador.comuna(null);
        assertThat(jugador.getComuna()).isNull();
    }

    @Test
    void centroEducativoTest() {
        Jugador jugador = getJugadorRandomSampleGenerator();
        CentroEducativo centroEducativoBack = getCentroEducativoRandomSampleGenerator();

        jugador.setCentroEducativo(centroEducativoBack);
        assertThat(jugador.getCentroEducativo()).isEqualTo(centroEducativoBack);

        jugador.centroEducativo(null);
        assertThat(jugador.getCentroEducativo()).isNull();
    }

    @Test
    void categoriasTest() {
        Jugador jugador = getJugadorRandomSampleGenerator();
        Categorias categoriasBack = getCategoriasRandomSampleGenerator();

        jugador.setCategorias(categoriasBack);
        assertThat(jugador.getCategorias()).isEqualTo(categoriasBack);

        jugador.categorias(null);
        assertThat(jugador.getCategorias()).isNull();
    }

    @Test
    void usuarioTest() {
        Jugador jugador = getJugadorRandomSampleGenerator();
        Usuario usuarioBack = getUsuarioRandomSampleGenerator();

        jugador.setUsuario(usuarioBack);
        assertThat(jugador.getUsuario()).isEqualTo(usuarioBack);
        assertThat(usuarioBack.getJugador()).isEqualTo(jugador);

        jugador.usuario(null);
        assertThat(jugador.getUsuario()).isNull();
        assertThat(usuarioBack.getJugador()).isNull();
    }

    @Test
    void finanzasIngresoTest() {
        Jugador jugador = getJugadorRandomSampleGenerator();
        FinanzasIngreso finanzasIngresoBack = getFinanzasIngresoRandomSampleGenerator();

        jugador.addFinanzasIngreso(finanzasIngresoBack);
        assertThat(jugador.getFinanzasIngresos()).containsOnly(finanzasIngresoBack);
        assertThat(finanzasIngresoBack.getJugador()).isEqualTo(jugador);

        jugador.removeFinanzasIngreso(finanzasIngresoBack);
        assertThat(jugador.getFinanzasIngresos()).doesNotContain(finanzasIngresoBack);
        assertThat(finanzasIngresoBack.getJugador()).isNull();

        jugador.finanzasIngresos(new HashSet<>(Set.of(finanzasIngresoBack)));
        assertThat(jugador.getFinanzasIngresos()).containsOnly(finanzasIngresoBack);
        assertThat(finanzasIngresoBack.getJugador()).isEqualTo(jugador);

        jugador.setFinanzasIngresos(new HashSet<>());
        assertThat(jugador.getFinanzasIngresos()).doesNotContain(finanzasIngresoBack);
        assertThat(finanzasIngresoBack.getJugador()).isNull();
    }

    @Test
    void cuentasTest() {
        Jugador jugador = getJugadorRandomSampleGenerator();
        Cuentas cuentasBack = getCuentasRandomSampleGenerator();

        jugador.addCuentas(cuentasBack);
        assertThat(jugador.getCuentas()).containsOnly(cuentasBack);
        assertThat(cuentasBack.getJugador()).isEqualTo(jugador);

        jugador.removeCuentas(cuentasBack);
        assertThat(jugador.getCuentas()).doesNotContain(cuentasBack);
        assertThat(cuentasBack.getJugador()).isNull();

        jugador.cuentas(new HashSet<>(Set.of(cuentasBack)));
        assertThat(jugador.getCuentas()).containsOnly(cuentasBack);
        assertThat(cuentasBack.getJugador()).isEqualTo(jugador);

        jugador.setCuentas(new HashSet<>());
        assertThat(jugador.getCuentas()).doesNotContain(cuentasBack);
        assertThat(cuentasBack.getJugador()).isNull();
    }

    @Test
    void padreTest() {
        Jugador jugador = getJugadorRandomSampleGenerator();
        Padre padreBack = getPadreRandomSampleGenerator();

        jugador.addPadre(padreBack);
        assertThat(jugador.getPadres()).containsOnly(padreBack);
        assertThat(padreBack.getJugador()).isEqualTo(jugador);

        jugador.removePadre(padreBack);
        assertThat(jugador.getPadres()).doesNotContain(padreBack);
        assertThat(padreBack.getJugador()).isNull();

        jugador.padres(new HashSet<>(Set.of(padreBack)));
        assertThat(jugador.getPadres()).containsOnly(padreBack);
        assertThat(padreBack.getJugador()).isEqualTo(jugador);

        jugador.setPadres(new HashSet<>());
        assertThat(jugador.getPadres()).doesNotContain(padreBack);
        assertThat(padreBack.getJugador()).isNull();
    }

    @Test
    void asociadosTest() {
        Jugador jugador = getJugadorRandomSampleGenerator();
        Asociados asociadosBack = getAsociadosRandomSampleGenerator();

        jugador.addAsociados(asociadosBack);
        assertThat(jugador.getAsociados()).containsOnly(asociadosBack);
        assertThat(asociadosBack.getJugador()).isEqualTo(jugador);

        jugador.removeAsociados(asociadosBack);
        assertThat(jugador.getAsociados()).doesNotContain(asociadosBack);
        assertThat(asociadosBack.getJugador()).isNull();

        jugador.asociados(new HashSet<>(Set.of(asociadosBack)));
        assertThat(jugador.getAsociados()).containsOnly(asociadosBack);
        assertThat(asociadosBack.getJugador()).isEqualTo(jugador);

        jugador.setAsociados(new HashSet<>());
        assertThat(jugador.getAsociados()).doesNotContain(asociadosBack);
        assertThat(asociadosBack.getJugador()).isNull();
    }
}
