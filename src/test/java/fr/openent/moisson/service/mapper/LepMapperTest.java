package fr.openent.moisson.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LepMapperTest {

    private LepMapper lepMapper;

    @BeforeEach
    public void setUp() {
        lepMapper = new LepMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(lepMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(lepMapper.fromId(null)).isNull();
    }
}
