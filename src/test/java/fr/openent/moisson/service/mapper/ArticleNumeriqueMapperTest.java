package fr.openent.moisson.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleNumeriqueMapperTest {

    private ArticleNumeriqueMapper articleNumeriqueMapper;

    @BeforeEach
    public void setUp() {
        articleNumeriqueMapper = new ArticleNumeriqueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(articleNumeriqueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(articleNumeriqueMapper.fromId(null)).isNull();
    }
}
