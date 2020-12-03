package fr.openent.moisson.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.openent.moisson.web.rest.TestUtil;

public class LicenceDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LicenceDTO.class);
        LicenceDTO licenceDTO1 = new LicenceDTO();
        licenceDTO1.setId(1L);
        LicenceDTO licenceDTO2 = new LicenceDTO();
        assertThat(licenceDTO1).isNotEqualTo(licenceDTO2);
        licenceDTO2.setId(licenceDTO1.getId());
        assertThat(licenceDTO1).isEqualTo(licenceDTO2);
        licenceDTO2.setId(2L);
        assertThat(licenceDTO1).isNotEqualTo(licenceDTO2);
        licenceDTO1.setId(null);
        assertThat(licenceDTO1).isNotEqualTo(licenceDTO2);
    }
}
