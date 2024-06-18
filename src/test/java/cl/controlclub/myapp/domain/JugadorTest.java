package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.CategoriasTestSamples.*;
import static cl.controlclub.myapp.domain.JugadorTestSamples.*;
import static cl.controlclub.myapp.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
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
    void categoriaTest() {
        Jugador jugador = getJugadorRandomSampleGenerator();
        Categorias categoriasBack = getCategoriasRandomSampleGenerator();

        jugador.setCategoria(categoriasBack);
        assertThat(jugador.getCategoria()).isEqualTo(categoriasBack);

        jugador.categoria(null);
        assertThat(jugador.getCategoria()).isNull();
    }
}
