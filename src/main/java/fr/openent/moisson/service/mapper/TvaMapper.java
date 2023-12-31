package fr.openent.moisson.service.mapper;


import fr.openent.moisson.domain.Tva;
import fr.openent.moisson.service.dto.TvaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
/**
 * Mapper for the entity {@link Tva} and its DTO {@link TvaDTO}.
 */
@Mapper(componentModel = "spring", uses = {OffreMapper.class, ArticlePapierMapper.class})
public interface TvaMapper extends EntityMapper<TvaDTO, Tva> {

    @Mapping(source = "offre.id", target = "offreId")
    @Mapping(source = "articlePapier.id", target = "articlePapierId")
    TvaDTO toDto(Tva tva);

    @Mapping(source = "offreId", target = "offre")
    @Mapping(source = "articlePapierId", target = "articlePapier")
    Tva toEntity(TvaDTO tvaDTO);

    default Tva fromId(Long id) {
        if (id == null) {
            return null;
        }
        Tva tva = new Tva();
        tva.setId(id);
        return tva;
    }
}
