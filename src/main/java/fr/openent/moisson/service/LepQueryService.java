package fr.openent.moisson.service;

import fr.openent.moisson.domain.*;
import fr.openent.moisson.repository.LepRepository;
import fr.openent.moisson.repository.search.LepSearchRepository;
import fr.openent.moisson.service.dto.LepCriteria;
import fr.openent.moisson.service.dto.LepDTO;
import fr.openent.moisson.service.mapper.LepMapper;
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
 * Service for executing complex queries for {@link Lep} entities in the database.
 * The main input is a {@link LepCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LepDTO} or a {@link Page} of {@link LepDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LepQueryService extends QueryService<Lep> {

    private final Logger log = LoggerFactory.getLogger(LepQueryService.class);

    private final LepRepository lepRepository;

    private final LepMapper lepMapper;

    private final LepSearchRepository lepSearchRepository;

    public LepQueryService(LepRepository lepRepository, LepMapper lepMapper, LepSearchRepository lepSearchRepository) {
        this.lepRepository = lepRepository;
        this.lepMapper = lepMapper;
        this.lepSearchRepository = lepSearchRepository;
    }

    /**
     * Return a {@link List} of {@link LepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LepDTO> findByCriteria(LepCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Lep> specification = createSpecification(criteria);
        return lepMapper.toDto(lepRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LepDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LepDTO> findByCriteria(LepCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Lep> specification = createSpecification(criteria);
        return lepRepository.findAll(specification, page)
            .map(lepMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LepCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Lep> specification = createSpecification(criteria);
        return lepRepository.count(specification);
    }

    /**
     * Function to convert {@link LepCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Lep> createSpecification(LepCriteria criteria) {
        Specification<Lep> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Lep_.id));
            }
            if (criteria.getEan() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEan(), Lep_.ean));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Lep_.description));
            }
            if (criteria.getTitre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitre(), Lep_.titre));
            }
            if (criteria.getDuree() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDuree(), Lep_.duree));
            }
            if (criteria.getConditionId() != null) {
                specification = specification.and(buildSpecification(criteria.getConditionId(),
                    root -> root.join(Lep_.conditions, JoinType.LEFT).get(Condition_.id)));
            }
            if (criteria.getOffreId() != null) {
                specification = specification.and(buildSpecification(criteria.getOffreId(),
                    root -> root.join(Lep_.offre, JoinType.LEFT).get(Offre_.id)));
            }
            if (criteria.getLicenceId() != null) {
                specification = specification.and(buildSpecification(criteria.getLicenceId(),
                    root -> root.join(Lep_.licence, JoinType.LEFT).get(Licence_.id)));
            }
        }
        return specification;
    }
}
