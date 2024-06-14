package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.AsociadosTestSamples.*;
import static cl.controlclub.myapp.domain.CuerpoTecnicoTestSamples.*;
import static cl.controlclub.myapp.domain.DirectivosTestSamples.*;
import static cl.controlclub.myapp.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AsociadosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asociados.class);
        Asociados asociados1 = getAsociadosSample1();
        Asociados asociados2 = new Asociados();
        assertThat(asociados1).isNotEqualTo(asociados2);

        asociados2.setId(asociados1.getId());
        assertThat(asociados1).isEqualTo(asociados2);

        asociados2 = getAsociadosSample2();
        assertThat(asociados1).isNotEqualTo(asociados2);
    }

    @Test
    void directivosTest() {
        Asociados asociados = getAsociadosRandomSampleGenerator();
        Directivos directivosBack = getDirectivosRandomSampleGenerator();

        asociados.setDirectivos(directivosBack);
        assertThat(asociados.getDirectivos()).isEqualTo(directivosBack);

        asociados.directivos(null);
        assertThat(asociados.getDirectivos()).isNull();
    }

    @Test
    void cuerpoTecnicoTest() {
        Asociados asociados = getAsociadosRandomSampleGenerator();
        CuerpoTecnico cuerpoTecnicoBack = getCuerpoTecnicoRandomSampleGenerator();

        asociados.setCuerpoTecnico(cuerpoTecnicoBack);
        assertThat(asociados.getCuerpoTecnico()).isEqualTo(cuerpoTecnicoBack);

        asociados.cuerpoTecnico(null);
        assertThat(asociados.getCuerpoTecnico()).isNull();
    }

    @Test
    void usuarioTest() {
        Asociados asociados = getAsociadosRandomSampleGenerator();
        Usuario usuarioBack = getUsuarioRandomSampleGenerator();

        asociados.setUsuario(usuarioBack);
        assertThat(asociados.getUsuario()).isEqualTo(usuarioBack);
        assertThat(usuarioBack.getAsociados()).isEqualTo(asociados);

        asociados.usuario(null);
        assertThat(asociados.getUsuario()).isNull();
        assertThat(usuarioBack.getAsociados()).isNull();
    }
}
