package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.DirectivosTestSamples.*;
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
}
