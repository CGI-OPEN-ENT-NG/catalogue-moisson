package fr.openent.moisson.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ArticlePapierMapperTest {

    private ArticlePapierMapper articlePapierMapper;

    @BeforeEach
    public void setUp() {
        articlePapierMapper = new ArticlePapierMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(articlePapierMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(articlePapierMapper.fromId(null)).isNull();
    }
}
