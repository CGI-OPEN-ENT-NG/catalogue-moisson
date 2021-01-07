package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.Licence;
import fr.openent.moisson.service.dto.LicenceDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Licence} and its DTO {@link LicenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LicenceMapper extends EntityMapper<LicenceDTO, Licence> {


    @Mapping(target = "offre", ignore = true)
    @Mapping(target = "lep", ignore = true)
    Licence toEntity(LicenceDTO licenceDTO);

    default Licence fromId(Long id) {
        if (id == null) {
            return null;
        }
        Licence licence = new Licence();
        licence.setId(id);
        return licence;
    }
}
