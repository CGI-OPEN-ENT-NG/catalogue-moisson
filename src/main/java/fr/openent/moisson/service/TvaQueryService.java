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

import fr.openent.moisson.domain.Tva;
import fr.openent.moisson.domain.*; // for static metamodels
import fr.openent.moisson.repository.TvaRepository;
import fr.openent.moisson.repository.search.TvaSearchRepository;
import fr.openent.moisson.service.dto.TvaCriteria;
import fr.openent.moisson.service.dto.TvaDTO;
import fr.openent.moisson.service.mapper.TvaMapper;

/**
 * Service for executing complex queries for {@link Tva} entities in the database.
 * The main input is a {@link TvaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TvaDTO} or a {@link Page} of {@link TvaDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TvaQueryService extends QueryService<Tva> {

    private final Logger log = LoggerFactory.getLogger(TvaQueryService.class);

    private final TvaRepository tvaRepository;

    private final TvaMapper tvaMapper;

    private final TvaSearchRepository tvaSearchRepository;

    public TvaQueryService(TvaRepository tvaRepository, TvaMapper tvaMapper, TvaSearchRepository tvaSearchRepository) {
        this.tvaRepository = tvaRepository;
        this.tvaMapper = tvaMapper;
        this.tvaSearchRepository = tvaSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TvaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TvaDTO> findByCriteria(TvaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tva> specification = createSpecification(criteria);
        return tvaMapper.toDto(tvaRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TvaDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TvaDTO> findByCriteria(TvaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tva> specification = createSpecification(criteria);
        return tvaRepository.findAll(specification, page)
            .map(tvaMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TvaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tva> specification = createSpecification(criteria);
        return tvaRepository.count(specification);
    }

    /**
     * Function to convert {@link TvaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tva> createSpecification(TvaCriteria criteria) {
        Specification<Tva> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tva_.id));
            }
            if (criteria.getTaux() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaux(), Tva_.taux));
            }
            if (criteria.getPourcent() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPourcent(), Tva_.pourcent));
            }
            if (criteria.getOffreId() != null) {
                specification = specification.and(buildSpecification(criteria.getOffreId(),
                    root -> root.join(Tva_.offre, JoinType.LEFT).get(Offre_.id)));
            }
        }
        return specification;
    }
}
