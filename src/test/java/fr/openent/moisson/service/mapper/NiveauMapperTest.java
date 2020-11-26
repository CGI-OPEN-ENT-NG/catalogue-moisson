package fr.openent.moisson.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class NiveauMapperTest {

    private NiveauMapper niveauMapper;

    @BeforeEach
    public void setUp() {
        niveauMapper = new NiveauMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(niveauMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(niveauMapper.fromId(null)).isNull();
    }
}
