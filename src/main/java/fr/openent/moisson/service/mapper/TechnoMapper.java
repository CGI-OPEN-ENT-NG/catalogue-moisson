package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.*;
import fr.openent.moisson.service.dto.TechnoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Techno} and its DTO {@link TechnoDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleNumeriqueMapper.class})
public interface TechnoMapper extends EntityMapper<TechnoDTO, Techno> {

    @Mapping(source = "articleNumerique.id", target = "articleNumeriqueId")
    TechnoDTO toDto(Techno techno);

    @Mapping(source = "articleNumeriqueId", target = "articleNumerique")
    Techno toEntity(TechnoDTO technoDTO);

    default Techno fromId(Long id) {
        if (id == null) {
            return null;
        }
        Techno techno = new Techno();
        techno.setId(id);
        return techno;
    }
}
