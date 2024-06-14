package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.AsociadosTestSamples.*;
import static cl.controlclub.myapp.domain.CuerpoTecnicoTestSamples.*;
import static cl.controlclub.myapp.domain.DirectivosTestSamples.*;
import static cl.controlclub.myapp.domain.JugadorTestSamples.*;
import static cl.controlclub.myapp.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Usuario.class);
        Usuario usuario1 = getUsuarioSample1();
        Usuario usuario2 = new Usuario();
        assertThat(usuario1).isNotEqualTo(usuario2);

        usuario2.setId(usuario1.getId());
        assertThat(usuario1).isEqualTo(usuario2);

        usuario2 = getUsuarioSample2();
        assertThat(usuario1).isNotEqualTo(usuario2);
    }

    @Test
    void jugadorTest() {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        Jugador jugadorBack = getJugadorRandomSampleGenerator();

        usuario.setJugador(jugadorBack);
        assertThat(usuario.getJugador()).isEqualTo(jugadorBack);

        usuario.jugador(null);
        assertThat(usuario.getJugador()).isNull();
    }

    @Test
    void asociadosTest() {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        Asociados asociadosBack = getAsociadosRandomSampleGenerator();

        usuario.setAsociados(asociadosBack);
        assertThat(usuario.getAsociados()).isEqualTo(asociadosBack);

        usuario.asociados(null);
        assertThat(usuario.getAsociados()).isNull();
    }

    @Test
    void directivosTest() {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        Directivos directivosBack = getDirectivosRandomSampleGenerator();

        usuario.setDirectivos(directivosBack);
        assertThat(usuario.getDirectivos()).isEqualTo(directivosBack);

        usuario.directivos(null);
        assertThat(usuario.getDirectivos()).isNull();
    }

    @Test
    void cuerpoTecnicoTest() {
        Usuario usuario = getUsuarioRandomSampleGenerator();
        CuerpoTecnico cuerpoTecnicoBack = getCuerpoTecnicoRandomSampleGenerator();

        usuario.setCuerpoTecnico(cuerpoTecnicoBack);
        assertThat(usuario.getCuerpoTecnico()).isEqualTo(cuerpoTecnicoBack);

        usuario.cuerpoTecnico(null);
        assertThat(usuario.getCuerpoTecnico()).isNull();
    }
}
