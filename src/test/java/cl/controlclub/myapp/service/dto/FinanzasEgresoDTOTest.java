package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FinanzasEgresoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanzasEgresoDTO.class);
        FinanzasEgresoDTO finanzasEgresoDTO1 = new FinanzasEgresoDTO();
        finanzasEgresoDTO1.setId(1L);
        FinanzasEgresoDTO finanzasEgresoDTO2 = new FinanzasEgresoDTO();
        assertThat(finanzasEgresoDTO1).isNotEqualTo(finanzasEgresoDTO2);
        finanzasEgresoDTO2.setId(finanzasEgresoDTO1.getId());
        assertThat(finanzasEgresoDTO1).isEqualTo(finanzasEgresoDTO2);
        finanzasEgresoDTO2.setId(2L);
        assertThat(finanzasEgresoDTO1).isNotEqualTo(finanzasEgresoDTO2);
        finanzasEgresoDTO1.setId(null);
        assertThat(finanzasEgresoDTO1).isNotEqualTo(finanzasEgresoDTO2);
    }
}
