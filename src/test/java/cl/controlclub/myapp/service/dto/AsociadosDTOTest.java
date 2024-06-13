package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AsociadosDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AsociadosDTO.class);
        AsociadosDTO asociadosDTO1 = new AsociadosDTO();
        asociadosDTO1.setId(1L);
        AsociadosDTO asociadosDTO2 = new AsociadosDTO();
        assertThat(asociadosDTO1).isNotEqualTo(asociadosDTO2);
        asociadosDTO2.setId(asociadosDTO1.getId());
        assertThat(asociadosDTO1).isEqualTo(asociadosDTO2);
        asociadosDTO2.setId(2L);
        assertThat(asociadosDTO1).isNotEqualTo(asociadosDTO2);
        asociadosDTO1.setId(null);
        assertThat(asociadosDTO1).isNotEqualTo(asociadosDTO2);
    }
}
