package fr.openent.moisson.service;

import fr.openent.moisson.domain.*;
import fr.openent.moisson.repository.ArticleNumeriqueRepository;
import fr.openent.moisson.repository.search.ArticleNumeriqueSearchRepository;
import fr.openent.moisson.service.dto.ArticleNumeriqueCriteria;
import fr.openent.moisson.service.dto.ArticleNumeriqueDTO;
import fr.openent.moisson.service.mapper.ArticleNumeriqueMapper;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.JoinType;
import java.util.List;

/**
 * Service for executing complex queries for {@link ArticleNumerique} entities in the database.
 * The main input is a {@link ArticleNumeriqueCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ArticleNumeriqueDTO} or a {@link Page} of {@link ArticleNumeriqueDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ArticleNumeriqueQueryService extends QueryService<ArticleNumerique> {

    private final Logger log = LoggerFactory.getLogger(ArticleNumeriqueQueryService.class);

    private final ArticleNumeriqueRepository articleNumeriqueRepository;

    private final ArticleNumeriqueMapper articleNumeriqueMapper;

    private final ArticleNumeriqueSearchRepository articleNumeriqueSearchRepository;

    public ArticleNumeriqueQueryService(ArticleNumeriqueRepository articleNumeriqueRepository, ArticleNumeriqueMapper articleNumeriqueMapper, ArticleNumeriqueSearchRepository articleNumeriqueSearchRepository) {
        this.articleNumeriqueRepository = articleNumeriqueRepository;
        this.articleNumeriqueMapper = articleNumeriqueMapper;
        this.articleNumeriqueSearchRepository = articleNumeriqueSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ArticleNumeriqueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ArticleNumeriqueDTO> findByCriteria(ArticleNumeriqueCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ArticleNumerique> specification = createSpecification(criteria);
        return articleNumeriqueMapper.toDto(articleNumeriqueRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ArticleNumeriqueDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ArticleNumeriqueDTO> findByCriteria(ArticleNumeriqueCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ArticleNumerique> specification = createSpecification(criteria);
        return articleNumeriqueRepository.findAll(specification, page)
            .map(articleNumeriqueMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ArticleNumeriqueCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ArticleNumerique> specification = createSpecification(criteria);
        return articleNumeriqueRepository.count(specification);
    }

    /**
     * Function to convert {@link ArticleNumeriqueCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ArticleNumerique> createSpecification(ArticleNumeriqueCriteria criteria) {
        Specification<ArticleNumerique> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ArticleNumerique_.id));
            }
            if (criteria.getEan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEan(), ArticleNumerique_.ean));
            }
            if (criteria.getArk() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArk(), ArticleNumerique_.ark));
            }
            if (criteria.getTitre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitre(), ArticleNumerique_.titre));
            }
            if (criteria.getEditeur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEditeur(), ArticleNumerique_.editeur));
            }
            if (criteria.getAuteur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuteur(), ArticleNumerique_.auteur));
            }
            if (criteria.getCollection() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCollection(), ArticleNumerique_.collection));
            }
            if (criteria.getDistributeur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistributeur(), ArticleNumerique_.distributeur));
            }
            if (criteria.getUrlCouverture() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlCouverture(), ArticleNumerique_.urlCouverture));
            }
            if (criteria.getUrlDemo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlDemo(), ArticleNumerique_.urlDemo));
            }
            if (criteria.getDateParution() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateParution(), ArticleNumerique_.dateParution));
            }
            if (criteria.getCompatibleGAR() != null) {
                specification = specification.and(buildSpecification(criteria.getCompatibleGAR(), ArticleNumerique_.compatibleGAR));
            }
            if (criteria.getAccessibleENT() != null) {
                specification = specification.and(buildSpecification(criteria.getAccessibleENT(), ArticleNumerique_.accessibleENT));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ArticleNumerique_.description));
            }
            if (criteria.getPublicCible() != null) {
                specification = specification.and(buildSpecification(criteria.getPublicCible(), ArticleNumerique_.publiccible));
            }
            if (criteria.getEanPapier() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEanPapier(), ArticleNumerique_.eanPapier));
            }
            if (criteria.getDisciplineId() != null) {
                specification = specification.and(buildSpecification(criteria.getDisciplineId(),
                    root -> root.join(ArticleNumerique_.disciplines, JoinType.LEFT).get(Discipline_.id)));
            }
            if (criteria.getNiveauId() != null) {
                specification = specification.and(buildSpecification(criteria.getNiveauId(),
                    root -> root.join(ArticleNumerique_.niveaus, JoinType.LEFT).get(Niveau_.id)));
            }
            if (criteria.getOffreId() != null) {
                specification = specification.and(buildSpecification(criteria.getOffreId(),
                    root -> root.join(ArticleNumerique_.offres, JoinType.LEFT).get(Offre_.id)));
            }
            if (criteria.getTechnoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTechnoId(),
                    root -> root.join(ArticleNumerique_.technos, JoinType.LEFT).get(Techno_.id)));
            }
            if (criteria.getDisponibiliteId() != null) {
                specification = specification.and(buildSpecification(criteria.getDisponibiliteId(),
                    root -> root.join(ArticleNumerique_.disponibilite, JoinType.LEFT).get(Disponibilite_.id)));
            }
        }
        return specification;
    }
}
