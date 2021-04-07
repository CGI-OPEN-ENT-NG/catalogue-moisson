package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.ClasseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Classe} and its DTO {@link ClasseDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleNumeriqueMapper.class})
public interface ClasseMapper extends EntityMapper<ClasseDTO, Classe> {

    @Mapping(source = "articleNumerique.id", target = "articleNumeriqueId")
    ClasseDTO toDto(Classe classe);

    @Mapping(source = "articleNumeriqueId", target = "articleNumerique")
    Classe toEntity(ClasseDTO classeDTO);

    default Classe fromId(Long id) {
        if (id == null) {
            return null;
        }
        Classe classe = new Classe();
        classe.setId(id);
        return classe;
    }
}
