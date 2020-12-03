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

import fr.openent.moisson.domain.Licence;
import fr.openent.moisson.domain.*; // for static metamodels
import fr.openent.moisson.repository.LicenceRepository;
import fr.openent.moisson.repository.search.LicenceSearchRepository;
import fr.openent.moisson.service.dto.LicenceCriteria;
import fr.openent.moisson.service.dto.LicenceDTO;
import fr.openent.moisson.service.mapper.LicenceMapper;

/**
 * Service for executing complex queries for {@link Licence} entities in the database.
 * The main input is a {@link LicenceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link LicenceDTO} or a {@link Page} of {@link LicenceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LicenceQueryService extends QueryService<Licence> {

    private final Logger log = LoggerFactory.getLogger(LicenceQueryService.class);

    private final LicenceRepository licenceRepository;

    private final LicenceMapper licenceMapper;

    private final LicenceSearchRepository licenceSearchRepository;

    public LicenceQueryService(LicenceRepository licenceRepository, LicenceMapper licenceMapper, LicenceSearchRepository licenceSearchRepository) {
        this.licenceRepository = licenceRepository;
        this.licenceMapper = licenceMapper;
        this.licenceSearchRepository = licenceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link LicenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<LicenceDTO> findByCriteria(LicenceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Licence> specification = createSpecification(criteria);
        return licenceMapper.toDto(licenceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link LicenceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<LicenceDTO> findByCriteria(LicenceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Licence> specification = createSpecification(criteria);
        return licenceRepository.findAll(specification, page)
            .map(licenceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LicenceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Licence> specification = createSpecification(criteria);
        return licenceRepository.count(specification);
    }

    /**
     * Function to convert {@link LicenceCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Licence> createSpecification(LicenceCriteria criteria) {
        Specification<Licence> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Licence_.id));
            }
            if (criteria.getValeur() != null) {
                specification = specification.and(buildStringSpecification(criteria.getValeur(), Licence_.valeur));
            }
            if (criteria.getOffreId() != null) {
                specification = specification.and(buildSpecification(criteria.getOffreId(),
                    root -> root.join(Licence_.offre, JoinType.LEFT).get(Offre_.id)));
            }
            if (criteria.getLepId() != null) {
                specification = specification.and(buildSpecification(criteria.getLepId(),
                    root -> root.join(Licence_.lep, JoinType.LEFT).get(Lep_.id)));
            }
        }
        return specification;
    }
}
