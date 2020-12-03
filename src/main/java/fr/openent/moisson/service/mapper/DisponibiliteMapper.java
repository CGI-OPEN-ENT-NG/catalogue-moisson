package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.DisponibiliteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Disponibilite} and its DTO {@link DisponibiliteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DisponibiliteMapper extends EntityMapper<DisponibiliteDTO, Disponibilite> {


    @Mapping(target = "articlePapier", ignore = true)
    @Mapping(target = "articleNumerique", ignore = true)
    Disponibilite toEntity(DisponibiliteDTO disponibiliteDTO);

    default Disponibilite fromId(Long id) {
        if (id == null) {
            return null;
        }
        Disponibilite disponibilite = new Disponibilite();
        disponibilite.setId(id);
        return disponibilite;
    }
}
