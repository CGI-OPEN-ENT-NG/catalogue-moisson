package fr.openent.moisson.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.openent.moisson.web.rest.TestUtil;

public class ArticlePapierDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticlePapierDTO.class);
        ArticlePapierDTO articlePapierDTO1 = new ArticlePapierDTO();
        articlePapierDTO1.setId(1L);
        ArticlePapierDTO articlePapierDTO2 = new ArticlePapierDTO();
        assertThat(articlePapierDTO1).isNotEqualTo(articlePapierDTO2);
        articlePapierDTO2.setId(articlePapierDTO1.getId());
        assertThat(articlePapierDTO1).isEqualTo(articlePapierDTO2);
        articlePapierDTO2.setId(2L);
        assertThat(articlePapierDTO1).isNotEqualTo(articlePapierDTO2);
        articlePapierDTO1.setId(null);
        assertThat(articlePapierDTO1).isNotEqualTo(articlePapierDTO2);
    }
}
