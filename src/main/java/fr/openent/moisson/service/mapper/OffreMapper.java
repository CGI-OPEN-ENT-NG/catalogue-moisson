package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.OffreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offre} and its DTO {@link OffreDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OffreMapper extends EntityMapper<OffreDTO, Offre> {


    @Mapping(target = "tvas", ignore = true)
    @Mapping(target = "removeTva", ignore = true)
    @Mapping(target = "leps", ignore = true)
    @Mapping(target = "removeLep", ignore = true)
    Offre toEntity(OffreDTO offreDTO);

    default Offre fromId(Long id) {
        if (id == null) {
            return null;
        }
        Offre offre = new Offre();
        offre.setId(id);
        return offre;
    }
}
