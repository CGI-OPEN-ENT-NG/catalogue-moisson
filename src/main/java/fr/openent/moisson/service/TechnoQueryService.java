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

import fr.openent.moisson.domain.Techno;
import fr.openent.moisson.domain.*; // for static metamodels
import fr.openent.moisson.repository.TechnoRepository;
import fr.openent.moisson.repository.search.TechnoSearchRepository;
import fr.openent.moisson.service.dto.TechnoCriteria;
import fr.openent.moisson.service.dto.TechnoDTO;
import fr.openent.moisson.service.mapper.TechnoMapper;

/**
 * Service for executing complex queries for {@link Techno} entities in the database.
 * The main input is a {@link TechnoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TechnoDTO} or a {@link Page} of {@link TechnoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TechnoQueryService extends QueryService<Techno> {

    private final Logger log = LoggerFactory.getLogger(TechnoQueryService.class);

    private final TechnoRepository technoRepository;

    private final TechnoMapper technoMapper;

    private final TechnoSearchRepository technoSearchRepository;

    public TechnoQueryService(TechnoRepository technoRepository, TechnoMapper technoMapper, TechnoSearchRepository technoSearchRepository) {
        this.technoRepository = technoRepository;
        this.technoMapper = technoMapper;
        this.technoSearchRepository = technoSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TechnoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TechnoDTO> findByCriteria(TechnoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Techno> specification = createSpecification(criteria);
        return technoMapper.toDto(technoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TechnoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TechnoDTO> findByCriteria(TechnoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Techno> specification = createSpecification(criteria);
        return technoRepository.findAll(specification, page)
            .map(technoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TechnoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Techno> specification = createSpecification(criteria);
        return technoRepository.count(specification);
    }

    /**
     * Function to convert {@link TechnoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Techno> createSpecification(TechnoCriteria criteria) {
        Specification<Techno> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Techno_.id));
            }
            if (criteria.getTechnologie() != null) {
                specification = specification.and(buildSpecification(criteria.getTechnologie(), Techno_.technologie));
            }
            if (criteria.getVersionReader() != null) {
                specification = specification.and(buildStringSpecification(criteria.getVersionReader(), Techno_.versionReader));
            }
            if (criteria.getAvailableHorsENT() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailableHorsENT(), Techno_.availableHorsENT));
            }
            if (criteria.getAvailableViaENT() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailableViaENT(), Techno_.availableViaENT));
            }
            if (criteria.getAvailableViaGAR() != null) {
                specification = specification.and(buildSpecification(criteria.getAvailableViaGAR(), Techno_.availableViaGAR));
            }
            if (criteria.getTypeLicenceGAR() != null) {
                specification = specification.and(buildSpecification(criteria.getTypeLicenceGAR(), Techno_.typeLicenceGAR));
            }
            if (criteria.getCanUseOffline() != null) {
                specification = specification.and(buildSpecification(criteria.getCanUseOffline(), Techno_.canUseOffline));
            }
            if (criteria.getOneClic() != null) {
                specification = specification.and(buildSpecification(criteria.getOneClic(), Techno_.oneClic));
            }
            if (criteria.getExportCleUSB() != null) {
                specification = specification.and(buildSpecification(criteria.getExportCleUSB(), Techno_.exportCleUSB));
            }
            if (criteria.getDeploiementMasse() != null) {
                specification = specification.and(buildSpecification(criteria.getDeploiementMasse(), Techno_.deploiementMasse));
            }
            if (criteria.getConfigurationMiniOS() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConfigurationMiniOS(), Techno_.configurationMiniOS));
            }
            if (criteria.getNeedFlash() != null) {
                specification = specification.and(buildSpecification(criteria.getNeedFlash(), Techno_.needFlash));
            }
            if (criteria.getAnnotations() != null) {
                specification = specification.and(buildSpecification(criteria.getAnnotations(), Techno_.annotations));
            }
            if (criteria.getCreationCours() != null) {
                specification = specification.and(buildSpecification(criteria.getCreationCours(), Techno_.creationCours));
            }
            if (criteria.getWebAdaptatif() != null) {
                specification = specification.and(buildSpecification(criteria.getWebAdaptatif(), Techno_.webAdaptatif));
            }
            if (criteria.getMarquePage() != null) {
                specification = specification.and(buildSpecification(criteria.getMarquePage(), Techno_.marquePage));
            }
            if (criteria.getCaptureImage() != null) {
                specification = specification.and(buildSpecification(criteria.getCaptureImage(), Techno_.captureImage));
            }
            if (criteria.getZoom() != null) {
                specification = specification.and(buildSpecification(criteria.getZoom(), Techno_.zoom));
            }
            if (criteria.getFonctionsRecherche() != null) {
                specification = specification.and(buildSpecification(criteria.getFonctionsRecherche(), Techno_.fonctionsRecherche));
            }
            if (criteria.getCorrigesPourEnseignants() != null) {
                specification = specification.and(buildSpecification(criteria.getCorrigesPourEnseignants(), Techno_.corrigesPourEnseignants));
            }
            if (criteria.getAssignationTachesEleves() != null) {
                specification = specification.and(buildSpecification(criteria.getAssignationTachesEleves(), Techno_.assignationTachesEleves));
            }
            if (criteria.getPartageContenuEleves() != null) {
                specification = specification.and(buildSpecification(criteria.getPartageContenuEleves(), Techno_.partageContenuEleves));
            }
            if (criteria.getExercicesInteractifs() != null) {
                specification = specification.and(buildSpecification(criteria.getExercicesInteractifs(), Techno_.exercicesInteractifs));
            }
            if (criteria.getExercicesAutoCorriges() != null) {
                specification = specification.and(buildSpecification(criteria.getExercicesAutoCorriges(), Techno_.exercicesAutoCorriges));
            }
            if (criteria.getExportReponsesEleves() != null) {
                specification = specification.and(buildSpecification(criteria.getExportReponsesEleves(), Techno_.exportReponsesEleves));
            }
            if (criteria.getImportDocument() != null) {
                specification = specification.and(buildSpecification(criteria.getImportDocument(), Techno_.importDocument));
            }
            if (criteria.getExportDocument() != null) {
                specification = specification.and(buildSpecification(criteria.getExportDocument(), Techno_.exportDocument));
            }
            if (criteria.getExportSCORM() != null) {
                specification = specification.and(buildSpecification(criteria.getExportSCORM(), Techno_.exportSCORM));
            }
            if (criteria.getPersonnalisationUserInterface() != null) {
                specification = specification.and(buildSpecification(criteria.getPersonnalisationUserInterface(), Techno_.personnalisationUserInterface));
            }
            if (criteria.getModifContenuEditorial() != null) {
                specification = specification.and(buildSpecification(criteria.getModifContenuEditorial(), Techno_.modifContenuEditorial));
            }
            if (criteria.getDispositifDYS() != null) {
                specification = specification.and(buildSpecification(criteria.getDispositifDYS(), Techno_.dispositifDYS));
            }
            if (criteria.getNbMaxiInstall() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNbMaxiInstall(), Techno_.nbMaxiInstall));
            }
            if (criteria.getNbMaxSimultConnexions() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNbMaxSimultConnexions(), Techno_.nbMaxSimultConnexions));
            }
            if (criteria.getArticleNumeriqueId() != null) {
                specification = specification.and(buildSpecification(criteria.getArticleNumeriqueId(),
                    root -> root.join(Techno_.articleNumerique, JoinType.LEFT).get(ArticleNumerique_.id)));
            }
        }
        return specification;
    }
}
