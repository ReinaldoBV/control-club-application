package cl.controlclub.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import cl.controlclub.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TorneosParticipacionesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TorneosParticipacionesDTO.class);
        TorneosParticipacionesDTO torneosParticipacionesDTO1 = new TorneosParticipacionesDTO();
        torneosParticipacionesDTO1.setId(1L);
        TorneosParticipacionesDTO torneosParticipacionesDTO2 = new TorneosParticipacionesDTO();
        assertThat(torneosParticipacionesDTO1).isNotEqualTo(torneosParticipacionesDTO2);
        torneosParticipacionesDTO2.setId(torneosParticipacionesDTO1.getId());
        assertThat(torneosParticipacionesDTO1).isEqualTo(torneosParticipacionesDTO2);
        torneosParticipacionesDTO2.setId(2L);
        assertThat(torneosParticipacionesDTO1).isNotEqualTo(torneosParticipacionesDTO2);
        torneosParticipacionesDTO1.setId(null);
        assertThat(torneosParticipacionesDTO1).isNotEqualTo(torneosParticipacionesDTO2);
    }
}
