package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.EntrenamientoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntrenamientoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entrenamiento.class);
        Entrenamiento entrenamiento1 = getEntrenamientoSample1();
        Entrenamiento entrenamiento2 = new Entrenamiento();
        assertThat(entrenamiento1).isNotEqualTo(entrenamiento2);

        entrenamiento2.setId(entrenamiento1.getId());
        assertThat(entrenamiento1).isEqualTo(entrenamiento2);

        entrenamiento2 = getEntrenamientoSample2();
        assertThat(entrenamiento1).isNotEqualTo(entrenamiento2);
    }
}
