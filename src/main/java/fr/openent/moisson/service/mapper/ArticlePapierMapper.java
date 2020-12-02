package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.ArticlePapierDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ArticlePapier} and its DTO {@link ArticlePapierDTO}.
 */
@Mapper(componentModel = "spring", uses = {TvaMapper.class})
public interface ArticlePapierMapper extends EntityMapper<ArticlePapierDTO, ArticlePapier> {

    // @Mapping(target = "tvas", ignore = true)
    @Mapping(target = "removeTva", ignore = true)
    ArticlePapier toEntity(ArticlePapierDTO articlePapierDTO);

    default ArticlePapier fromId(Long id) {
        if (id == null) {
            return null;
        }
        ArticlePapier articlePapier = new ArticlePapier();
        articlePapier.setId(id);
        return articlePapier;
    }
}
