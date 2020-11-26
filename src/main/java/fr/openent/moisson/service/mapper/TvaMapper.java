package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.TvaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tva} and its DTO {@link TvaDTO}.
 */
@Mapper(componentModel = "spring", uses = {OffreMapper.class})
public interface TvaMapper extends EntityMapper<TvaDTO, Tva> {

    @Mapping(source = "offre.id", target = "offreId")
    TvaDTO toDto(Tva tva);

    @Mapping(source = "offreId", target = "offre")
    Tva toEntity(TvaDTO tvaDTO);

    default Tva fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tva tva = new Tva();
        tva.setId(id);
        return tva;
    }
}
