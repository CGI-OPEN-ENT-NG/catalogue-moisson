package fr.openent.moisson.domain;

import fr.openent.moisson.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LepTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lep.class);
        Lep lep1 = new Lep();
        lep1.setId(1L);
        Lep lep2 = new Lep();
        lep2.setId(lep1.getId());
        assertThat(lep1).isEqualTo(lep2);
        lep2.setId(2L);
        assertThat(lep1).isNotEqualTo(lep2);
        lep1.setId(null);
        assertThat(lep1).isNotEqualTo(lep2);
    }
}
