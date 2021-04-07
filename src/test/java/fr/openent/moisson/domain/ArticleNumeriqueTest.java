package fr.openent.moisson.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.openent.moisson.web.rest.TestUtil;

public class ArticleNumeriqueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleNumerique.class);
        ArticleNumerique articleNumerique1 = new ArticleNumerique();
        articleNumerique1.setId(1L);
        ArticleNumerique articleNumerique2 = new ArticleNumerique();
        articleNumerique2.setId(articleNumerique1.getId());
        assertThat(articleNumerique1).isEqualTo(articleNumerique2);
        articleNumerique2.setId(2L);
        assertThat(articleNumerique1).isNotEqualTo(articleNumerique2);
        articleNumerique1.setId(null);
        assertThat(articleNumerique1).isNotEqualTo(articleNumerique2);
    }
}
