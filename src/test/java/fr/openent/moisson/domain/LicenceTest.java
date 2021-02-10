package fr.openent.moisson.domain;

import fr.openent.moisson.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LicenceTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Licence.class);
        Licence licence1 = new Licence();
        licence1.setId(1L);
        Licence licence2 = new Licence();
        licence2.setId(licence1.getId());
        assertThat(licence1).isEqualTo(licence2);
        licence2.setId(2L);
        assertThat(licence1).isNotEqualTo(licence2);
        licence1.setId(null);
        assertThat(licence1).isNotEqualTo(licence2);
    }
}
