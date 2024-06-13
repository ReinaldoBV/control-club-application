package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JugadorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JugadorDTO.class);
        JugadorDTO jugadorDTO1 = new JugadorDTO();
        jugadorDTO1.setId(1L);
        JugadorDTO jugadorDTO2 = new JugadorDTO();
        assertThat(jugadorDTO1).isNotEqualTo(jugadorDTO2);
        jugadorDTO2.setId(jugadorDTO1.getId());
        assertThat(jugadorDTO1).isEqualTo(jugadorDTO2);
        jugadorDTO2.setId(2L);
        assertThat(jugadorDTO1).isNotEqualTo(jugadorDTO2);
        jugadorDTO1.setId(null);
        assertThat(jugadorDTO1).isNotEqualTo(jugadorDTO2);
    }
}
