package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CentroSaludDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroSaludDTO.class);
        CentroSaludDTO centroSaludDTO1 = new CentroSaludDTO();
        centroSaludDTO1.setId(1L);
        CentroSaludDTO centroSaludDTO2 = new CentroSaludDTO();
        assertThat(centroSaludDTO1).isNotEqualTo(centroSaludDTO2);
        centroSaludDTO2.setId(centroSaludDTO1.getId());
        assertThat(centroSaludDTO1).isEqualTo(centroSaludDTO2);
        centroSaludDTO2.setId(2L);
        assertThat(centroSaludDTO1).isNotEqualTo(centroSaludDTO2);
        centroSaludDTO1.setId(null);
        assertThat(centroSaludDTO1).isNotEqualTo(centroSaludDTO2);
    }
}
