package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.OffreDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Offre} and its DTO {@link OffreDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleNumeriqueMapper.class})
public interface OffreMapper extends EntityMapper<OffreDTO, Offre> {

    @Mapping(source = "articleNumerique.id", target = "articleNumeriqueId")
    OffreDTO toDto(Offre offre);

    @Mapping(target = "tvas", ignore = true)
    @Mapping(target = "removeTva", ignore = true)
    @Mapping(target = "leps", ignore = true)
    @Mapping(target = "removeLep", ignore = true)
    @Mapping(source = "articleNumeriqueId", target = "articleNumerique")
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
