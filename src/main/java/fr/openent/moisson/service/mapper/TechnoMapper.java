package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.TechnoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Techno} and its DTO {@link TechnoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TechnoMapper extends EntityMapper<TechnoDTO, Techno> {



    default Techno fromId(Long id) {
        if (id == null) {
            return null;
        }
        Techno techno = new Techno();
        techno.setId(id);
        return techno;
    }
}
