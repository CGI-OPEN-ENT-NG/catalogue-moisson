package fr.openent.moisson.service;

import fr.openent.moisson.domain.ArticleNumerique_;
import fr.openent.moisson.domain.ArticlePapier_;
import fr.openent.moisson.domain.Disponibilite;
import fr.openent.moisson.domain.Disponibilite_;
import fr.openent.moisson.repository.DisponibiliteRepository;
import fr.openent.moisson.repository.search.DisponibiliteSearchRepository;
import fr.openent.moisson.service.dto.DisponibiliteCriteria;
import fr.openent.moisson.service.dto.DisponibiliteDTO;
import fr.openent.moisson.service.mapper.DisponibiliteMapper;
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
 * Service for executing complex queries for {@link Disponibilite} entities in the database.
 * The main input is a {@link DisponibiliteCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DisponibiliteDTO} or a {@link Page} of {@link DisponibiliteDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DisponibiliteQueryService extends QueryService<Disponibilite> {

    private final Logger log = LoggerFactory.getLogger(DisponibiliteQueryService.class);

    private final DisponibiliteRepository disponibiliteRepository;

    private final DisponibiliteMapper disponibiliteMapper;

    private final DisponibiliteSearchRepository disponibiliteSearchRepository;

    public DisponibiliteQueryService(DisponibiliteRepository disponibiliteRepository, DisponibiliteMapper disponibiliteMapper, DisponibiliteSearchRepository disponibiliteSearchRepository) {
        this.disponibiliteRepository = disponibiliteRepository;
        this.disponibiliteMapper = disponibiliteMapper;
        this.disponibiliteSearchRepository = disponibiliteSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DisponibiliteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DisponibiliteDTO> findByCriteria(DisponibiliteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Disponibilite> specification = createSpecification(criteria);
        return disponibiliteMapper.toDto(disponibiliteRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DisponibiliteDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DisponibiliteDTO> findByCriteria(DisponibiliteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Disponibilite> specification = createSpecification(criteria);
        return disponibiliteRepository.findAll(specification, page)
            .map(disponibiliteMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DisponibiliteCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Disponibilite> specification = createSpecification(criteria);
        return disponibiliteRepository.count(specification);
    }

    /**
     * Function to convert {@link DisponibiliteCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Disponibilite> createSpecification(DisponibiliteCriteria criteria) {
        Specification<Disponibilite> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Disponibilite_.id));
            }
            if (criteria.getCommentaire() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommentaire(), Disponibilite_.commentaire));
            }
            if (criteria.getDateDisponibilite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateDisponibilite(), Disponibilite_.dateDisponibilite));
            }
            if (criteria.getCommandable() != null) {
                specification = specification.and(buildSpecification(criteria.getCommandable(), Disponibilite_.commandable));
            }
            if (criteria.getValeur() != null) {
                specification = specification.and(buildSpecification(criteria.getValeur(), Disponibilite_.valeur));
            }
            if (criteria.getArticlePapierId() != null) {
                specification = specification.and(buildSpecification(criteria.getArticlePapierId(),
                    root -> root.join(Disponibilite_.articlePapier, JoinType.LEFT).get(ArticlePapier_.id)));
            }
            if (criteria.getArticleNumeriqueId() != null) {
                specification = specification.and(buildSpecification(criteria.getArticleNumeriqueId(),
                    root -> root.join(Disponibilite_.articleNumerique, JoinType.LEFT).get(ArticleNumerique_.id)));
            }
        }
        return specification;
    }
}
