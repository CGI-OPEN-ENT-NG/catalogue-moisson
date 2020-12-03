package fr.openent.moisson.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DisponibiliteMapperTest {

    private DisponibiliteMapper disponibiliteMapper;

    @BeforeEach
    public void setUp() {
        disponibiliteMapper = new DisponibiliteMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(disponibiliteMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(disponibiliteMapper.fromId(null)).isNull();
    }
}
