package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.ConditionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Condition} and its DTO {@link ConditionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ConditionMapper extends EntityMapper<ConditionDTO, Condition> {



    default Condition fromId(Long id) {
        if (id == null) {
            return null;
        }
        Condition condition = new Condition();
        condition.setId(id);
        return condition;
    }
}
