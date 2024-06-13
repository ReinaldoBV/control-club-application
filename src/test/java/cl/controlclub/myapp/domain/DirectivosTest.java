package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.AsociadosTestSamples.*;
import static cl.controlclub.myapp.domain.DirectivosTestSamples.*;
import static cl.controlclub.myapp.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DirectivosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Directivos.class);
        Directivos directivos1 = getDirectivosSample1();
        Directivos directivos2 = new Directivos();
        assertThat(directivos1).isNotEqualTo(directivos2);

        directivos2.setId(directivos1.getId());
        assertThat(directivos1).isEqualTo(directivos2);

        directivos2 = getDirectivosSample2();
        assertThat(directivos1).isNotEqualTo(directivos2);
    }

    @Test
    void asociadosTest() {
        Directivos directivos = getDirectivosRandomSampleGenerator();
        Asociados asociadosBack = getAsociadosRandomSampleGenerator();

        directivos.setAsociados(asociadosBack);
        assertThat(directivos.getAsociados()).isEqualTo(asociadosBack);

        directivos.asociados(null);
        assertThat(directivos.getAsociados()).isNull();
    }

    @Test
    void usuarioTest() {
        Directivos directivos = getDirectivosRandomSampleGenerator();
        Usuario usuarioBack = getUsuarioRandomSampleGenerator();

        directivos.setUsuario(usuarioBack);
        assertThat(directivos.getUsuario()).isEqualTo(usuarioBack);
        assertThat(usuarioBack.getDirectivos()).isEqualTo(directivos);

        directivos.usuario(null);
        assertThat(directivos.getUsuario()).isNull();
        assertThat(usuarioBack.getDirectivos()).isNull();
    }
}
