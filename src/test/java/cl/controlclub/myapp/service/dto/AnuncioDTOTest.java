package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnuncioDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnuncioDTO.class);
        AnuncioDTO anuncioDTO1 = new AnuncioDTO();
        anuncioDTO1.setId(1L);
        AnuncioDTO anuncioDTO2 = new AnuncioDTO();
        assertThat(anuncioDTO1).isNotEqualTo(anuncioDTO2);
        anuncioDTO2.setId(anuncioDTO1.getId());
        assertThat(anuncioDTO1).isEqualTo(anuncioDTO2);
        anuncioDTO2.setId(2L);
        assertThat(anuncioDTO1).isNotEqualTo(anuncioDTO2);
        anuncioDTO1.setId(null);
        assertThat(anuncioDTO1).isNotEqualTo(anuncioDTO2);
    }
}
