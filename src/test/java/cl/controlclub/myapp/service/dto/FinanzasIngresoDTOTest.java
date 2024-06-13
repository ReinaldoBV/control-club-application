package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FinanzasIngresoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FinanzasIngresoDTO.class);
        FinanzasIngresoDTO finanzasIngresoDTO1 = new FinanzasIngresoDTO();
        finanzasIngresoDTO1.setId(1L);
        FinanzasIngresoDTO finanzasIngresoDTO2 = new FinanzasIngresoDTO();
        assertThat(finanzasIngresoDTO1).isNotEqualTo(finanzasIngresoDTO2);
        finanzasIngresoDTO2.setId(finanzasIngresoDTO1.getId());
        assertThat(finanzasIngresoDTO1).isEqualTo(finanzasIngresoDTO2);
        finanzasIngresoDTO2.setId(2L);
        assertThat(finanzasIngresoDTO1).isNotEqualTo(finanzasIngresoDTO2);
        finanzasIngresoDTO1.setId(null);
        assertThat(finanzasIngresoDTO1).isNotEqualTo(finanzasIngresoDTO2);
    }
}
