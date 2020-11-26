package fr.openent.moisson.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.openent.moisson.web.rest.TestUtil;

public class ArticlePapierTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticlePapier.class);
        ArticlePapier articlePapier1 = new ArticlePapier();
        articlePapier1.setId(1L);
        ArticlePapier articlePapier2 = new ArticlePapier();
        articlePapier2.setId(articlePapier1.getId());
        assertThat(articlePapier1).isEqualTo(articlePapier2);
        articlePapier2.setId(2L);
        assertThat(articlePapier1).isNotEqualTo(articlePapier2);
        articlePapier1.setId(null);
        assertThat(articlePapier1).isNotEqualTo(articlePapier2);
    }
}
