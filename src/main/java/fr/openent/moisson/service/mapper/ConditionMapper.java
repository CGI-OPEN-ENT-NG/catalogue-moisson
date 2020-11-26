package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.ConditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Condition} and its DTO {@link ConditionDTO}.
 */
@Mapper(componentModel = "spring", uses = {LepMapper.class})
public interface ConditionMapper extends EntityMapper<ConditionDTO, Condition> {

    @Mapping(source = "lep.id", target = "lepId")
    ConditionDTO toDto(Condition condition);

    @Mapping(source = "lepId", target = "lep")
    Condition toEntity(ConditionDTO conditionDTO);

    default Condition fromId(Long id) {
        if (id == null) {
            return null;
        }
        Condition condition = new Condition();
        condition.setId(id);
        return condition;
    }
}
