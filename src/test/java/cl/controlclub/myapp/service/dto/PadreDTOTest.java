package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PadreDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PadreDTO.class);
        PadreDTO padreDTO1 = new PadreDTO();
        padreDTO1.setId(1L);
        PadreDTO padreDTO2 = new PadreDTO();
        assertThat(padreDTO1).isNotEqualTo(padreDTO2);
        padreDTO2.setId(padreDTO1.getId());
        assertThat(padreDTO1).isEqualTo(padreDTO2);
        padreDTO2.setId(2L);
        assertThat(padreDTO1).isNotEqualTo(padreDTO2);
        padreDTO1.setId(null);
        assertThat(padreDTO1).isNotEqualTo(padreDTO2);
    }
}
