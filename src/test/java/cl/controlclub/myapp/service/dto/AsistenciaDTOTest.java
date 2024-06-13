package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AsistenciaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AsistenciaDTO.class);
        AsistenciaDTO asistenciaDTO1 = new AsistenciaDTO();
        asistenciaDTO1.setId(1L);
        AsistenciaDTO asistenciaDTO2 = new AsistenciaDTO();
        assertThat(asistenciaDTO1).isNotEqualTo(asistenciaDTO2);
        asistenciaDTO2.setId(asistenciaDTO1.getId());
        assertThat(asistenciaDTO1).isEqualTo(asistenciaDTO2);
        asistenciaDTO2.setId(2L);
        assertThat(asistenciaDTO1).isNotEqualTo(asistenciaDTO2);
        asistenciaDTO1.setId(null);
        assertThat(asistenciaDTO1).isNotEqualTo(asistenciaDTO2);
    }
}
