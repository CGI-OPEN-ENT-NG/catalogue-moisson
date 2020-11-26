package fr.openent.moisson.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TvaMapperTest {

    private TvaMapper tvaMapper;

    @BeforeEach
    public void setUp() {
        tvaMapper = new TvaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(tvaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(tvaMapper.fromId(null)).isNull();
    }
}
