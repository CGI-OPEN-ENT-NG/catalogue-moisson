package fr.openent.moisson.service;

import fr.openent.moisson.domain.Condition;
import fr.openent.moisson.domain.Condition_;
import fr.openent.moisson.domain.Lep_;
import fr.openent.moisson.repository.ConditionRepository;
import fr.openent.moisson.repository.search.ConditionSearchRepository;
import fr.openent.moisson.service.dto.ConditionCriteria;
import fr.openent.moisson.service.dto.ConditionDTO;
import fr.openent.moisson.service.mapper.ConditionMapper;
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
 * Service for executing complex queries for {@link Condition} entities in the database.
 * The main input is a {@link ConditionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConditionDTO} or a {@link Page} of {@link ConditionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConditionQueryService extends QueryService<Condition> {

    private final Logger log = LoggerFactory.getLogger(ConditionQueryService.class);

    private final ConditionRepository conditionRepository;

    private final ConditionMapper conditionMapper;

    private final ConditionSearchRepository conditionSearchRepository;

    public ConditionQueryService(ConditionRepository conditionRepository, ConditionMapper conditionMapper, ConditionSearchRepository conditionSearchRepository) {
        this.conditionRepository = conditionRepository;
        this.conditionMapper = conditionMapper;
        this.conditionSearchRepository = conditionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConditionDTO> findByCriteria(ConditionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Condition> specification = createSpecification(criteria);
        return conditionMapper.toDto(conditionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ConditionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConditionDTO> findByCriteria(ConditionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Condition> specification = createSpecification(criteria);
        return conditionRepository.findAll(specification, page)
            .map(conditionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConditionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Condition> specification = createSpecification(criteria);
        return conditionRepository.count(specification);
    }

    /**
     * Function to convert {@link ConditionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Condition> createSpecification(ConditionCriteria criteria) {
        Specification<Condition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Condition_.id));
            }
            if (criteria.getGratuite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGratuite(), Condition_.gratuite));
            }
            if (criteria.getConditionGratuite() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getConditionGratuite(), Condition_.conditionGratuite));
            }
            if (criteria.getLepId() != null) {
                specification = specification.and(buildSpecification(criteria.getLepId(),
                    root -> root.join(Condition_.lep, JoinType.LEFT).get(Lep_.id)));
            }
        }
        return specification;
    }
}
