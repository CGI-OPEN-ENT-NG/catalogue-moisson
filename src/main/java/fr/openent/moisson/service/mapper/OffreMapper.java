package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.OffreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Offre} and its DTO {@link OffreDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleNumeriqueMapper.class, LicenceMapper.class})
public interface OffreMapper extends EntityMapper<OffreDTO, Offre> {

    @Mapping(source = "articleNumerique.id", target = "articleNumeriqueId")
    @Mapping(source = "licence.id", target = "licenceId")
    OffreDTO toDto(Offre offre);

    @Mapping(target = "tvas", ignore = true)
    @Mapping(target = "removeTva", ignore = true)
    @Mapping(target = "leps", ignore = true)
    @Mapping(target = "removeLep", ignore = true)
    @Mapping(source = "articleNumeriqueId", target = "articleNumerique")
    @Mapping(source = "licenceId", target = "licence")
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
