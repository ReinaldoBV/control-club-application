package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ComunaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ComunaDTO.class);
        ComunaDTO comunaDTO1 = new ComunaDTO();
        comunaDTO1.setId(1L);
        ComunaDTO comunaDTO2 = new ComunaDTO();
        assertThat(comunaDTO1).isNotEqualTo(comunaDTO2);
        comunaDTO2.setId(comunaDTO1.getId());
        assertThat(comunaDTO1).isEqualTo(comunaDTO2);
        comunaDTO2.setId(2L);
        assertThat(comunaDTO1).isNotEqualTo(comunaDTO2);
        comunaDTO1.setId(null);
        assertThat(comunaDTO1).isNotEqualTo(comunaDTO2);
    }
}
