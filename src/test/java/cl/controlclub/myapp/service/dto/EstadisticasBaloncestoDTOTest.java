package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstadisticasBaloncestoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstadisticasBaloncestoDTO.class);
        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO1 = new EstadisticasBaloncestoDTO();
        estadisticasBaloncestoDTO1.setId(1L);
        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO2 = new EstadisticasBaloncestoDTO();
        assertThat(estadisticasBaloncestoDTO1).isNotEqualTo(estadisticasBaloncestoDTO2);
        estadisticasBaloncestoDTO2.setId(estadisticasBaloncestoDTO1.getId());
        assertThat(estadisticasBaloncestoDTO1).isEqualTo(estadisticasBaloncestoDTO2);
        estadisticasBaloncestoDTO2.setId(2L);
        assertThat(estadisticasBaloncestoDTO1).isNotEqualTo(estadisticasBaloncestoDTO2);
        estadisticasBaloncestoDTO1.setId(null);
        assertThat(estadisticasBaloncestoDTO1).isNotEqualTo(estadisticasBaloncestoDTO2);
    }
}
