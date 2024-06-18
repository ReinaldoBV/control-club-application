package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.CategoriasTestSamples.*;
import static cl.controlclub.myapp.domain.JugadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CategoriasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categorias.class);
        Categorias categorias1 = getCategoriasSample1();
        Categorias categorias2 = new Categorias();
        assertThat(categorias1).isNotEqualTo(categorias2);

        categorias2.setId(categorias1.getId());
        assertThat(categorias1).isEqualTo(categorias2);

        categorias2 = getCategoriasSample2();
        assertThat(categorias1).isNotEqualTo(categorias2);
    }

    @Test
    void jugadorTest() {
        Categorias categorias = getCategoriasRandomSampleGenerator();
        Jugador jugadorBack = getJugadorRandomSampleGenerator();

        categorias.addJugador(jugadorBack);
        assertThat(categorias.getJugadors()).containsOnly(jugadorBack);
        assertThat(jugadorBack.getCategoria()).isEqualTo(categorias);

        categorias.removeJugador(jugadorBack);
        assertThat(categorias.getJugadors()).doesNotContain(jugadorBack);
        assertThat(jugadorBack.getCategoria()).isNull();

        categorias.jugadors(new HashSet<>(Set.of(jugadorBack)));
        assertThat(categorias.getJugadors()).containsOnly(jugadorBack);
        assertThat(jugadorBack.getCategoria()).isEqualTo(categorias);

        categorias.setJugadors(new HashSet<>());
        assertThat(categorias.getJugadors()).doesNotContain(jugadorBack);
        assertThat(jugadorBack.getCategoria()).isNull();
    }
}
