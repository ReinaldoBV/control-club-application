package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrevisionSaludDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrevisionSaludDTO.class);
        PrevisionSaludDTO previsionSaludDTO1 = new PrevisionSaludDTO();
        previsionSaludDTO1.setId(1L);
        PrevisionSaludDTO previsionSaludDTO2 = new PrevisionSaludDTO();
        assertThat(previsionSaludDTO1).isNotEqualTo(previsionSaludDTO2);
        previsionSaludDTO2.setId(previsionSaludDTO1.getId());
        assertThat(previsionSaludDTO1).isEqualTo(previsionSaludDTO2);
        previsionSaludDTO2.setId(2L);
        assertThat(previsionSaludDTO1).isNotEqualTo(previsionSaludDTO2);
        previsionSaludDTO1.setId(null);
        assertThat(previsionSaludDTO1).isNotEqualTo(previsionSaludDTO2);
    }
}
