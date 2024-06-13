package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CuentasDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CuentasDTO.class);
        CuentasDTO cuentasDTO1 = new CuentasDTO();
        cuentasDTO1.setId(1L);
        CuentasDTO cuentasDTO2 = new CuentasDTO();
        assertThat(cuentasDTO1).isNotEqualTo(cuentasDTO2);
        cuentasDTO2.setId(cuentasDTO1.getId());
        assertThat(cuentasDTO1).isEqualTo(cuentasDTO2);
        cuentasDTO2.setId(2L);
        assertThat(cuentasDTO1).isNotEqualTo(cuentasDTO2);
        cuentasDTO1.setId(null);
        assertThat(cuentasDTO1).isNotEqualTo(cuentasDTO2);
    }
}
