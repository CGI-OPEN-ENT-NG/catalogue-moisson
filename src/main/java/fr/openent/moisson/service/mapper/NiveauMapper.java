package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.Niveau;
import fr.openent.moisson.service.dto.NiveauDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Niveau} and its DTO {@link NiveauDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArticleNumeriqueMapper.class, ArticlePapierMapper.class})
public interface NiveauMapper extends EntityMapper<NiveauDTO, Niveau> {

    @Mapping(source = "articleNumerique.id", target = "articleNumeriqueId")
    @Mapping(source = "articlePapier.id", target = "articlePapierId")
    NiveauDTO toDto(Niveau niveau);

    @Mapping(source = "articleNumeriqueId", target = "articleNumerique")
    @Mapping(source = "articlePapierId", target = "articlePapier")
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
