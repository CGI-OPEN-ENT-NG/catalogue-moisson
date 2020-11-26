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

import fr.openent.moisson.domain.Discipline;
import fr.openent.moisson.domain.*; // for static metamodels
import fr.openent.moisson.repository.DisciplineRepository;
import fr.openent.moisson.repository.search.DisciplineSearchRepository;
import fr.openent.moisson.service.dto.DisciplineCriteria;
import fr.openent.moisson.service.dto.DisciplineDTO;
import fr.openent.moisson.service.mapper.DisciplineMapper;

/**
 * Service for executing complex queries for {@link Discipline} entities in the database.
 * The main input is a {@link DisciplineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DisciplineDTO} or a {@link Page} of {@link DisciplineDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DisciplineQueryService extends QueryService<Discipline> {

    private final Logger log = LoggerFactory.getLogger(DisciplineQueryService.class);

    private final DisciplineRepository disciplineRepository;

    private final DisciplineMapper disciplineMapper;

    private final DisciplineSearchRepository disciplineSearchRepository;

    public DisciplineQueryService(DisciplineRepository disciplineRepository, DisciplineMapper disciplineMapper, DisciplineSearchRepository disciplineSearchRepository) {
        this.disciplineRepository = disciplineRepository;
        this.disciplineMapper = disciplineMapper;
        this.disciplineSearchRepository = disciplineSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DisciplineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DisciplineDTO> findByCriteria(DisciplineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Discipline> specification = createSpecification(criteria);
        return disciplineMapper.toDto(disciplineRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DisciplineDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DisciplineDTO> findByCriteria(DisciplineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Discipline> specification = createSpecification(criteria);
        return disciplineRepository.findAll(specification, page)
            .map(disciplineMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DisciplineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Discipline> specification = createSpecification(criteria);
        return disciplineRepository.count(specification);
    }

    /**
     * Function to convert {@link DisciplineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Discipline> createSpecification(DisciplineCriteria criteria) {
        Specification<Discipline> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Discipline_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Discipline_.libelle));
            }
            if (criteria.getTerme() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTerme(), Discipline_.terme));
            }
            if (criteria.getConcept() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConcept(), Discipline_.concept));
            }
            if (criteria.getArticleNumeriqueId() != null) {
                specification = specification.and(buildSpecification(criteria.getArticleNumeriqueId(),
                    root -> root.join(Discipline_.articleNumerique, JoinType.LEFT).get(ArticleNumerique_.id)));
            }
        }
        return specification;
    }
}
