package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.ArticleNumeriqueDTO;

import fr.openent.moisson.service.dto.NiveauDTO;
import fr.openent.moisson.service.dto.OffreDTO;
import fr.openent.moisson.service.dto.TechnoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArticleNumerique} and its DTO {@link ArticleNumeriqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {OffreMapper.class, NiveauMapper.class, OffreMapper.class, TechnoMapper.class})
public interface ArticleNumeriqueMapper extends EntityMapper<ArticleNumeriqueDTO, ArticleNumerique> {

    // @Mapping(target = "disciplines", ignore = true)
    @Mapping(target = "removeDiscipline", ignore = true)
    // @Mapping(target = "niveaus", ignore = true)
    @Mapping(target = "removeNiveau", ignore = true)
    // @Mapping(target = "offres", ignore = true)
    @Mapping(target = "removeOffre", ignore = true)
    // @Mapping(target = "technos", ignore = true)
    @Mapping(target = "removeTechno", ignore = true)
    ArticleNumerique toEntity(ArticleNumeriqueDTO articleNumeriqueDTO);

    default ArticleNumerique fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArticleNumerique articleNumerique = new ArticleNumerique();
        articleNumerique.setId(id);
        return articleNumerique;
    }
}
