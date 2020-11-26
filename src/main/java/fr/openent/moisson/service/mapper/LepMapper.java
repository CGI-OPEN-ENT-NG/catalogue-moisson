package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.LepDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lep} and its DTO {@link LepDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LepMapper extends EntityMapper<LepDTO, Lep> {


    @Mapping(target = "conditions", ignore = true)
    @Mapping(target = "removeCondition", ignore = true)
    Lep toEntity(LepDTO lepDTO);

    default Lep fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lep lep = new Lep();
        lep.setId(id);
        return lep;
    }
}
