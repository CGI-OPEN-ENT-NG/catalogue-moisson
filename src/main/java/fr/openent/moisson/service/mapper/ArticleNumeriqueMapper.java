package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.ArticleNumeriqueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArticleNumerique} and its DTO {@link ArticleNumeriqueDTO}.
 */
@Mapper(componentModel = "spring", uses = {DisponibiliteMapper.class})
public interface ArticleNumeriqueMapper extends EntityMapper<ArticleNumeriqueDTO, ArticleNumerique> {

    @Mapping(source = "disponibilite.id", target = "disponibiliteId")
    ArticleNumeriqueDTO toDto(ArticleNumerique articleNumerique);

    @Mapping(target = "disciplines", ignore = true)
    @Mapping(target = "removeDiscipline", ignore = true)
    @Mapping(target = "niveaus", ignore = true)
    @Mapping(target = "removeNiveau", ignore = true)
    @Mapping(target = "offres", ignore = true)
    @Mapping(target = "removeOffre", ignore = true)
    @Mapping(target = "technos", ignore = true)
    @Mapping(target = "removeTechno", ignore = true)
    @Mapping(source = "disponibiliteId", target = "disponibilite")
    @Mapping(target = "classes", ignore = true)
    @Mapping(target = "removeClasse", ignore = true)
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
