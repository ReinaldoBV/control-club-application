package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntrenamientoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntrenamientoDTO.class);
        EntrenamientoDTO entrenamientoDTO1 = new EntrenamientoDTO();
        entrenamientoDTO1.setId(1L);
        EntrenamientoDTO entrenamientoDTO2 = new EntrenamientoDTO();
        assertThat(entrenamientoDTO1).isNotEqualTo(entrenamientoDTO2);
        entrenamientoDTO2.setId(entrenamientoDTO1.getId());
        assertThat(entrenamientoDTO1).isEqualTo(entrenamientoDTO2);
        entrenamientoDTO2.setId(2L);
        assertThat(entrenamientoDTO1).isNotEqualTo(entrenamientoDTO2);
        entrenamientoDTO1.setId(null);
        assertThat(entrenamientoDTO1).isNotEqualTo(entrenamientoDTO2);
    }
}
