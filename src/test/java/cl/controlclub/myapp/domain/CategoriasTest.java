package cl.controlclub.myapp.domain;

import static cl.controlclub.myapp.domain.CategoriasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
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
}
