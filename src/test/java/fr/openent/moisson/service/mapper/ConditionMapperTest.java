package fr.openent.moisson.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ConditionMapperTest {

    private ConditionMapper conditionMapper;

    @BeforeEach
    public void setUp() {
        conditionMapper = new ConditionMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(conditionMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(conditionMapper.fromId(null)).isNull();
    }
}
