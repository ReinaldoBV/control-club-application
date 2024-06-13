package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartidoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PartidoDTO.class);
        PartidoDTO partidoDTO1 = new PartidoDTO();
        partidoDTO1.setId(1L);
        PartidoDTO partidoDTO2 = new PartidoDTO();
        assertThat(partidoDTO1).isNotEqualTo(partidoDTO2);
        partidoDTO2.setId(partidoDTO1.getId());
        assertThat(partidoDTO1).isEqualTo(partidoDTO2);
        partidoDTO2.setId(2L);
        assertThat(partidoDTO1).isNotEqualTo(partidoDTO2);
        partidoDTO1.setId(null);
        assertThat(partidoDTO1).isNotEqualTo(partidoDTO2);
    }
}
