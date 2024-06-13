package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CentroEducativoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CentroEducativoDTO.class);
        CentroEducativoDTO centroEducativoDTO1 = new CentroEducativoDTO();
        centroEducativoDTO1.setId(1L);
        CentroEducativoDTO centroEducativoDTO2 = new CentroEducativoDTO();
        assertThat(centroEducativoDTO1).isNotEqualTo(centroEducativoDTO2);
        centroEducativoDTO2.setId(centroEducativoDTO1.getId());
        assertThat(centroEducativoDTO1).isEqualTo(centroEducativoDTO2);
        centroEducativoDTO2.setId(2L);
        assertThat(centroEducativoDTO1).isNotEqualTo(centroEducativoDTO2);
        centroEducativoDTO1.setId(null);
        assertThat(centroEducativoDTO1).isNotEqualTo(centroEducativoDTO2);
    }
}
