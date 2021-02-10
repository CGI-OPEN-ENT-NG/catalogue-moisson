package fr.openent.moisson.service.dto;

import fr.openent.moisson.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LepDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LepDTO.class);
        LepDTO lepDTO1 = new LepDTO();
        lepDTO1.setId(1L);
        LepDTO lepDTO2 = new LepDTO();
        assertThat(lepDTO1).isNotEqualTo(lepDTO2);
        lepDTO2.setId(lepDTO1.getId());
        assertThat(lepDTO1).isEqualTo(lepDTO2);
        lepDTO2.setId(2L);
        assertThat(lepDTO1).isNotEqualTo(lepDTO2);
        lepDTO1.setId(null);
        assertThat(lepDTO1).isNotEqualTo(lepDTO2);
    }
}
