package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CuerpoTecnicoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuerpoTecnicoDTO.class);
        CuerpoTecnicoDTO cuerpoTecnicoDTO1 = new CuerpoTecnicoDTO();
        cuerpoTecnicoDTO1.setId(1L);
        CuerpoTecnicoDTO cuerpoTecnicoDTO2 = new CuerpoTecnicoDTO();
        assertThat(cuerpoTecnicoDTO1).isNotEqualTo(cuerpoTecnicoDTO2);
        cuerpoTecnicoDTO2.setId(cuerpoTecnicoDTO1.getId());
        assertThat(cuerpoTecnicoDTO1).isEqualTo(cuerpoTecnicoDTO2);
        cuerpoTecnicoDTO2.setId(2L);
        assertThat(cuerpoTecnicoDTO1).isNotEqualTo(cuerpoTecnicoDTO2);
        cuerpoTecnicoDTO1.setId(null);
        assertThat(cuerpoTecnicoDTO1).isNotEqualTo(cuerpoTecnicoDTO2);
    }
}
