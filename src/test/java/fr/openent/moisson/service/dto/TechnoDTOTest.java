package fr.openent.moisson.service.dto;

import fr.openent.moisson.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TechnoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechnoDTO.class);
        TechnoDTO technoDTO1 = new TechnoDTO();
        technoDTO1.setId(1L);
        TechnoDTO technoDTO2 = new TechnoDTO();
        assertThat(technoDTO1).isNotEqualTo(technoDTO2);
        technoDTO2.setId(technoDTO1.getId());
        assertThat(technoDTO1).isEqualTo(technoDTO2);
        technoDTO2.setId(2L);
        assertThat(technoDTO1).isNotEqualTo(technoDTO2);
        technoDTO1.setId(null);
        assertThat(technoDTO1).isNotEqualTo(technoDTO2);
    }
}
