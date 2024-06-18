package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.AsociadosTestSamples.*;
import static cl.controlclub.myapp.domain.CuerpoTecnicoTestSamples.*;
import static cl.controlclub.myapp.domain.UsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CuerpoTecnicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuerpoTecnico.class);
        CuerpoTecnico cuerpoTecnico1 = getCuerpoTecnicoSample1();
        CuerpoTecnico cuerpoTecnico2 = new CuerpoTecnico();
        assertThat(cuerpoTecnico1).isNotEqualTo(cuerpoTecnico2);

        cuerpoTecnico2.setId(cuerpoTecnico1.getId());
        assertThat(cuerpoTecnico1).isEqualTo(cuerpoTecnico2);

        cuerpoTecnico2 = getCuerpoTecnicoSample2();
        assertThat(cuerpoTecnico1).isNotEqualTo(cuerpoTecnico2);
    }

    @Test
    void asociadosTest() {
        CuerpoTecnico cuerpoTecnico = getCuerpoTecnicoRandomSampleGenerator();
        Asociados asociadosBack = getAsociadosRandomSampleGenerator();

        cuerpoTecnico.setAsociados(asociadosBack);
        assertThat(cuerpoTecnico.getAsociados()).isEqualTo(asociadosBack);

        cuerpoTecnico.asociados(null);
        assertThat(cuerpoTecnico.getAsociados()).isNull();
    }

    @Test
    void usuarioTest() {
        CuerpoTecnico cuerpoTecnico = getCuerpoTecnicoRandomSampleGenerator();
        Usuario usuarioBack = getUsuarioRandomSampleGenerator();

        cuerpoTecnico.setUsuario(usuarioBack);
        assertThat(cuerpoTecnico.getUsuario()).isEqualTo(usuarioBack);
        assertThat(usuarioBack.getCuerpoTecnico()).isEqualTo(cuerpoTecnico);

        cuerpoTecnico.usuario(null);
        assertThat(cuerpoTecnico.getUsuario()).isNull();
        assertThat(usuarioBack.getCuerpoTecnico()).isNull();
    }
}
