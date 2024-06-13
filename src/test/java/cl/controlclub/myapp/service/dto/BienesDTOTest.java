package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BienesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BienesDTO.class);
        BienesDTO bienesDTO1 = new BienesDTO();
        bienesDTO1.setId(1L);
        BienesDTO bienesDTO2 = new BienesDTO();
        assertThat(bienesDTO1).isNotEqualTo(bienesDTO2);
        bienesDTO2.setId(bienesDTO1.getId());
        assertThat(bienesDTO1).isEqualTo(bienesDTO2);
        bienesDTO2.setId(2L);
        assertThat(bienesDTO1).isNotEqualTo(bienesDTO2);
        bienesDTO1.setId(null);
        assertThat(bienesDTO1).isNotEqualTo(bienesDTO2);
    }
}
