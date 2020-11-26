package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.NiveauDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Niveau} and its DTO {@link NiveauDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleNumeriqueMapper.class})
public interface NiveauMapper extends EntityMapper<NiveauDTO, Niveau> {

    @Mapping(source = "articleNumerique.id", target = "articleNumeriqueId")
    NiveauDTO toDto(Niveau niveau);

    @Mapping(source = "articleNumeriqueId", target = "articleNumerique")
    Niveau toEntity(NiveauDTO niveauDTO);

    default Niveau fromId(Long id) {
        if (id == null) {
            return null;
        }
        Niveau niveau = new Niveau();
        niveau.setId(id);
        return niveau;
    }
}
