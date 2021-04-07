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

import fr.openent.moisson.domain.Classe;
import fr.openent.moisson.domain.*; // for static metamodels
import fr.openent.moisson.repository.ClasseRepository;
import fr.openent.moisson.repository.search.ClasseSearchRepository;
import fr.openent.moisson.service.dto.ClasseCriteria;
import fr.openent.moisson.service.dto.ClasseDTO;
import fr.openent.moisson.service.mapper.ClasseMapper;

/**
 * Service for executing complex queries for {@link Classe} entities in the database.
 * The main input is a {@link ClasseCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClasseDTO} or a {@link Page} of {@link ClasseDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClasseQueryService extends QueryService<Classe> {

    private final Logger log = LoggerFactory.getLogger(ClasseQueryService.class);

    private final ClasseRepository classeRepository;

    private final ClasseMapper classeMapper;

    private final ClasseSearchRepository classeSearchRepository;

    public ClasseQueryService(ClasseRepository classeRepository, ClasseMapper classeMapper, ClasseSearchRepository classeSearchRepository) {
        this.classeRepository = classeRepository;
        this.classeMapper = classeMapper;
        this.classeSearchRepository = classeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ClasseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClasseDTO> findByCriteria(ClasseCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Classe> specification = createSpecification(criteria);
        return classeMapper.toDto(classeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClasseDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClasseDTO> findByCriteria(ClasseCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Classe> specification = createSpecification(criteria);
        return classeRepository.findAll(specification, page)
            .map(classeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClasseCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Classe> specification = createSpecification(criteria);
        return classeRepository.count(specification);
    }

    /**
     * Function to convert {@link ClasseCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Classe> createSpecification(ClasseCriteria criteria) {
        Specification<Classe> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Classe_.id));
            }
            if (criteria.getLibelle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLibelle(), Classe_.libelle));
            }
            if (criteria.getArticleNumeriqueId() != null) {
                specification = specification.and(buildSpecification(criteria.getArticleNumeriqueId(),
                    root -> root.join(Classe_.articleNumerique, JoinType.LEFT).get(ArticleNumerique_.id)));
            }
        }
        return specification;
    }
}
