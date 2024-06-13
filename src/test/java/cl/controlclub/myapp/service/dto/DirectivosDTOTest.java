package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DirectivosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DirectivosDTO.class);
        DirectivosDTO directivosDTO1 = new DirectivosDTO();
        directivosDTO1.setId(1L);
        DirectivosDTO directivosDTO2 = new DirectivosDTO();
        assertThat(directivosDTO1).isNotEqualTo(directivosDTO2);
        directivosDTO2.setId(directivosDTO1.getId());
        assertThat(directivosDTO1).isEqualTo(directivosDTO2);
        directivosDTO2.setId(2L);
        assertThat(directivosDTO1).isNotEqualTo(directivosDTO2);
        directivosDTO1.setId(null);
        assertThat(directivosDTO1).isNotEqualTo(directivosDTO2);
    }
}
