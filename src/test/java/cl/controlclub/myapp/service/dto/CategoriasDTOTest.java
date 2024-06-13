package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoriasDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategoriasDTO.class);
        CategoriasDTO categoriasDTO1 = new CategoriasDTO();
        categoriasDTO1.setId(1L);
        CategoriasDTO categoriasDTO2 = new CategoriasDTO();
        assertThat(categoriasDTO1).isNotEqualTo(categoriasDTO2);
        categoriasDTO2.setId(categoriasDTO1.getId());
        assertThat(categoriasDTO1).isEqualTo(categoriasDTO2);
        categoriasDTO2.setId(2L);
        assertThat(categoriasDTO1).isNotEqualTo(categoriasDTO2);
        categoriasDTO1.setId(null);
        assertThat(categoriasDTO1).isNotEqualTo(categoriasDTO2);
    }
}
