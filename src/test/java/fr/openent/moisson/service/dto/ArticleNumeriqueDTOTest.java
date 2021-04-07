package fr.openent.moisson.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.openent.moisson.web.rest.TestUtil;

public class ArticleNumeriqueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ArticleNumeriqueDTO.class);
        ArticleNumeriqueDTO articleNumeriqueDTO1 = new ArticleNumeriqueDTO();
        articleNumeriqueDTO1.setId(1L);
        ArticleNumeriqueDTO articleNumeriqueDTO2 = new ArticleNumeriqueDTO();
        assertThat(articleNumeriqueDTO1).isNotEqualTo(articleNumeriqueDTO2);
        articleNumeriqueDTO2.setId(articleNumeriqueDTO1.getId());
        assertThat(articleNumeriqueDTO1).isEqualTo(articleNumeriqueDTO2);
        articleNumeriqueDTO2.setId(2L);
        assertThat(articleNumeriqueDTO1).isNotEqualTo(articleNumeriqueDTO2);
        articleNumeriqueDTO1.setId(null);
        assertThat(articleNumeriqueDTO1).isNotEqualTo(articleNumeriqueDTO2);
    }
}
