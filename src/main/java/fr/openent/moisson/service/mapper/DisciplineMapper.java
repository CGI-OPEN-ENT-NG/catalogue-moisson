package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.DisciplineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Discipline} and its DTO {@link DisciplineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleNumeriqueMapper.class})
public interface DisciplineMapper extends EntityMapper<DisciplineDTO, Discipline> {

    @Mapping(source = "articleNumerique.id", target = "articleNumeriqueId")
    DisciplineDTO toDto(Discipline discipline);

    @Mapping(source = "articleNumeriqueId", target = "articleNumerique")
    Discipline toEntity(DisciplineDTO disciplineDTO);

    default Discipline fromId(Long id) {
        if (id == null) {
            return null;
        }
        Discipline discipline = new Discipline();
        discipline.setId(id);
        return discipline;
    }
}
