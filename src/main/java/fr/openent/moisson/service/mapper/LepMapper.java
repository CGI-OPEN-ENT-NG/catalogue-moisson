package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.LepDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Mapper for the entity {@link Lep} and its DTO {@link LepDTO}.
 */
@Mapper(componentModel = "spring", uses = {OffreMapper.class, LicenceMapper.class})
public interface LepMapper extends EntityMapper<LepDTO, Lep> {

    @Mapping(source = "offre.id", target = "offreId")
    @Mapping(source = "licence.id", target = "licenceId")
    LepDTO toDto(Lep lep);

    @Mapping(target = "conditions", ignore = true)
    @Mapping(target = "removeCondition", ignore = true)
    @Mapping(source = "offreId", target = "offre")
    @Mapping(source = "licenceId", target = "licence")
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
