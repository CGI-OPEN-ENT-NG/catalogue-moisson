package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.Discipline;
import fr.openent.moisson.service.dto.DisciplineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Discipline} and its DTO {@link DisciplineDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleNumeriqueMapper.class, ArticlePapierMapper.class})
public interface DisciplineMapper extends EntityMapper<DisciplineDTO, Discipline> {

    @Mapping(source = "articleNumerique.id", target = "articleNumeriqueId")
    @Mapping(source = "articlePapier.id", target = "articlePapierId")
    DisciplineDTO toDto(Discipline discipline);

    @Mapping(source = "articleNumeriqueId", target = "articleNumerique")
    @Mapping(source = "articlePapierId", target = "articlePapier")
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
