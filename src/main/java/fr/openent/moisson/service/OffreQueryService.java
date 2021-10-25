package fr.openent.moisson.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import fr.openent.moisson.domain.Offre;
import fr.openent.moisson.domain.*; // for static metamodels
import fr.openent.moisson.repository.OffreRepository;
import fr.openent.moisson.repository.search.OffreSearchRepository;
import fr.openent.moisson.service.dto.OffreCriteria;
import fr.openent.moisson.service.dto.OffreDTO;
import fr.openent.moisson.service.mapper.OffreMapper;

/**
 * Service for executing complex queries for {@link Offre} entities in the database.
 * The main input is a {@link OffreCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OffreDTO} or a {@link Page} of {@link OffreDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OffreQueryService extends QueryService<Offre> {

    private final Logger log = LoggerFactory.getLogger(OffreQueryService.class);

    private final OffreRepository offreRepository;

    private final OffreMapper offreMapper;

    private final OffreSearchRepository offreSearchRepository;

    public OffreQueryService(OffreRepository offreRepository, OffreMapper offreMapper, OffreSearchRepository offreSearchRepository) {
        this.offreRepository = offreRepository;
        this.offreMapper = offreMapper;
        this.offreSearchRepository = offreSearchRepository;
    }

    /**
     * Return a {@link List} of {@link OffreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OffreDTO> findByCriteria(OffreCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Offre> specification = createSpecification(criteria);
        return offreMapper.toDto(offreRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OffreDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OffreDTO> findByCriteria(OffreCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Offre> specification = createSpecification(criteria);
        return offreRepository.findAll(specification, page)
            .map(offreMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OffreCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Offre> specification = createSpecification(criteria);
        return offreRepository.count(specification);
    }

    /**
     * Function to convert {@link OffreCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Offre> createSpecification(OffreCriteria criteria) {
        Specification<Offre> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Offre_.id));
            }
            if (criteria.getEanLibraire() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEanLibraire(), Offre_.eanLibraire));
            }
            if (criteria.getQuantiteMinimaleAchat() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantiteMinimaleAchat(), Offre_.quantiteMinimaleAchat));
            }
            if (criteria.getPrescripteur() != null) {
                specification = specification.and(buildSpecification(criteria.getPrescripteur(), Offre_.prescripteur));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Offre_.libelle));
            }
            if (criteria.getPrixHT() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixHT(), Offre_.prixHT));
            }
            if (criteria.getAdoptant() != null) {
                specification = specification.and(buildSpecification(criteria.getAdoptant(), Offre_.adoptant));
            }
            if (criteria.getDuree() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuree(), Offre_.duree));
            }
            if (criteria.getReferenceEditeur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceEditeur(), Offre_.referenceEditeur));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Offre_.type));
            }
            if (criteria.getIs3ans() != null) {
                specification = specification.and(buildSpecification(criteria.getIs3ans(), Offre_.is3ans));
            }
            if (criteria.getTvaId() != null) {
                specification = specification.and(buildSpecification(criteria.getTvaId(),
                    root -> root.join(Offre_.tvas, JoinType.LEFT).get(Tva_.id)));
            }
            if (criteria.getLepId() != null) {
                specification = specification.and(buildSpecification(criteria.getLepId(),
                    root -> root.join(Offre_.leps, JoinType.LEFT).get(Lep_.id)));
            }
            if (criteria.getArticleNumeriqueId() != null) {
                specification = specification.and(buildSpecification(criteria.getArticleNumeriqueId(),
                    root -> root.join(Offre_.articleNumerique, JoinType.LEFT).get(ArticleNumerique_.id)));
            }
            if (criteria.getLicenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getLicenceId(),
                    root -> root.join(Offre_.licence, JoinType.LEFT).get(Licence_.id)));
            }
        }
        return specification;
    }
}
