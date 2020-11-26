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

import fr.openent.moisson.domain.ArticlePapier;
import fr.openent.moisson.domain.*; // for static metamodels
import fr.openent.moisson.repository.ArticlePapierRepository;
import fr.openent.moisson.repository.search.ArticlePapierSearchRepository;
import fr.openent.moisson.service.dto.ArticlePapierCriteria;
import fr.openent.moisson.service.dto.ArticlePapierDTO;
import fr.openent.moisson.service.mapper.ArticlePapierMapper;

/**
 * Service for executing complex queries for {@link ArticlePapier} entities in the database.
 * The main input is a {@link ArticlePapierCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ArticlePapierDTO} or a {@link Page} of {@link ArticlePapierDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ArticlePapierQueryService extends QueryService<ArticlePapier> {

    private final Logger log = LoggerFactory.getLogger(ArticlePapierQueryService.class);

    private final ArticlePapierRepository articlePapierRepository;

    private final ArticlePapierMapper articlePapierMapper;

    private final ArticlePapierSearchRepository articlePapierSearchRepository;

    public ArticlePapierQueryService(ArticlePapierRepository articlePapierRepository, ArticlePapierMapper articlePapierMapper, ArticlePapierSearchRepository articlePapierSearchRepository) {
        this.articlePapierRepository = articlePapierRepository;
        this.articlePapierMapper = articlePapierMapper;
        this.articlePapierSearchRepository = articlePapierSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ArticlePapierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ArticlePapierDTO> findByCriteria(ArticlePapierCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ArticlePapier> specification = createSpecification(criteria);
        return articlePapierMapper.toDto(articlePapierRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ArticlePapierDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ArticlePapierDTO> findByCriteria(ArticlePapierCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ArticlePapier> specification = createSpecification(criteria);
        return articlePapierRepository.findAll(specification, page)
            .map(articlePapierMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ArticlePapierCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ArticlePapier> specification = createSpecification(criteria);
        return articlePapierRepository.count(specification);
    }

    /**
     * Function to convert {@link ArticlePapierCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ArticlePapier> createSpecification(ArticlePapierCriteria criteria) {
        Specification<ArticlePapier> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ArticlePapier_.id));
            }
            if (criteria.getEan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEan(), ArticlePapier_.ean));
            }
            if (criteria.getArk() != null) {
                specification = specification.and(buildStringSpecification(criteria.getArk(), ArticlePapier_.ark));
            }
            if (criteria.getTitre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitre(), ArticlePapier_.titre));
            }
            if (criteria.getEditeur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEditeur(), ArticlePapier_.editeur));
            }
            if (criteria.getAuteur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAuteur(), ArticlePapier_.auteur));
            }
            if (criteria.getReferenceEditeur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReferenceEditeur(), ArticlePapier_.referenceEditeur));
            }
            if (criteria.getCollection() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCollection(), ArticlePapier_.collection));
            }
            if (criteria.getDistributeur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDistributeur(), ArticlePapier_.distributeur));
            }
            if (criteria.getUrlCouverture() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlCouverture(), ArticlePapier_.urlCouverture));
            }
            if (criteria.getDisponibilte() != null) {
                specification = specification.and(buildSpecification(criteria.getDisponibilte(), ArticlePapier_.disponibilte));
            }
            if (criteria.getDateDisponibilte() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDisponibilte(), ArticlePapier_.dateDisponibilte));
            }
            if (criteria.getDateParution() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateParution(), ArticlePapier_.dateParution));
            }
            if (criteria.getCommandable() != null) {
                specification = specification.and(buildSpecification(criteria.getCommandable(), ArticlePapier_.commandable));
            }
            if (criteria.getTva() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTva(), ArticlePapier_.tva));
            }
            if (criteria.getPrixHT() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrixHT(), ArticlePapier_.prixHT));
            }
        }
        return specification;
    }
}
