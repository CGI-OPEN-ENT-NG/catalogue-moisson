package fr.openent.moisson.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TechnoMapperTest {

    private TechnoMapper technoMapper;

    @BeforeEach
    public void setUp() {
        technoMapper = new TechnoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(technoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(technoMapper.fromId(null)).isNull();
    }
}
