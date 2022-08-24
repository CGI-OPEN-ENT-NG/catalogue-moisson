package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.Techno;
import fr.openent.moisson.domain.ArticleNumerique;
import fr.openent.moisson.repository.TechnoRepository;
import fr.openent.moisson.repository.search.TechnoSearchRepository;
import fr.openent.moisson.service.TechnoService;
import fr.openent.moisson.service.dto.TechnoDTO;
import fr.openent.moisson.service.mapper.TechnoMapper;
import fr.openent.moisson.service.dto.TechnoCriteria;
import fr.openent.moisson.service.TechnoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.openent.moisson.domain.enumeration.Technologie;
import fr.openent.moisson.domain.enumeration.TypeLicenceGAR;
/**
 * Integration tests for the {@link TechnoResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TechnoResourceIT {

    private static final String DEFAULT_TECHNOLOGIE = Technologie.WINDOWS.getValue();
    private static final String UPDATED_TECHNOLOGIE = Technologie.MAC_OS.getValue();

    private static final String DEFAULT_VERSION_READER = "AAAAAAAAAA";
    private static final String UPDATED_VERSION_READER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AVAILABLE_HORS_ENT = false;
    private static final Boolean UPDATED_AVAILABLE_HORS_ENT = true;

    private static final Boolean DEFAULT_AVAILABLE_VIA_ENT = false;
    private static final Boolean UPDATED_AVAILABLE_VIA_ENT = true;

    private static final Boolean DEFAULT_AVAILABLE_VIA_GAR = false;
    private static final Boolean UPDATED_AVAILABLE_VIA_GAR = true;

    private static final String DEFAULT_TYPE_LICENCE_GAR = TypeLicenceGAR.TRANSFERABLE.getValue();
    private static final String UPDATED_TYPE_LICENCE_GAR = TypeLicenceGAR.NON_TRANFERABLE.getValue();

    private static final Boolean DEFAULT_CAN_USE_OFFLINE = false;
    private static final Boolean UPDATED_CAN_USE_OFFLINE = true;

    private static final Boolean DEFAULT_ONE_CLIC = false;
    private static final Boolean UPDATED_ONE_CLIC = true;

    private static final Boolean DEFAULT_EXPORT_CLE_USB = false;
    private static final Boolean UPDATED_EXPORT_CLE_USB = true;

    private static final Boolean DEFAULT_DEPLOIEMENT_MASSE = false;
    private static final Boolean UPDATED_DEPLOIEMENT_MASSE = true;

    private static final String DEFAULT_CONFIGURATION_MINI_OS = "AAAAAAAAAA";
    private static final String UPDATED_CONFIGURATION_MINI_OS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NEED_FLASH = false;
    private static final Boolean UPDATED_NEED_FLASH = true;

    private static final Boolean DEFAULT_ANNOTATIONS = false;
    private static final Boolean UPDATED_ANNOTATIONS = true;

    private static final Boolean DEFAULT_CREATION_COURS = false;
    private static final Boolean UPDATED_CREATION_COURS = true;

    private static final Boolean DEFAULT_WEB_ADAPTATIF = false;
    private static final Boolean UPDATED_WEB_ADAPTATIF = true;

    private static final Boolean DEFAULT_MARQUE_PAGE = false;
    private static final Boolean UPDATED_MARQUE_PAGE = true;

    private static final Boolean DEFAULT_CAPTURE_IMAGE = false;
    private static final Boolean UPDATED_CAPTURE_IMAGE = true;

    private static final Boolean DEFAULT_ZOOM = false;
    private static final Boolean UPDATED_ZOOM = true;

    private static final Boolean DEFAULT_FONCTIONS_RECHERCHE = false;
    private static final Boolean UPDATED_FONCTIONS_RECHERCHE = true;

    private static final Boolean DEFAULT_CORRIGES_POUR_ENSEIGNANTS = false;
    private static final Boolean UPDATED_CORRIGES_POUR_ENSEIGNANTS = true;

    private static final Boolean DEFAULT_ASSIGNATION_TACHES_ELEVES = false;
    private static final Boolean UPDATED_ASSIGNATION_TACHES_ELEVES = true;

    private static final Boolean DEFAULT_PARTAGE_CONTENU_ELEVES = false;
    private static final Boolean UPDATED_PARTAGE_CONTENU_ELEVES = true;

    private static final Boolean DEFAULT_EXERCICES_INTERACTIFS = false;
    private static final Boolean UPDATED_EXERCICES_INTERACTIFS = true;

    private static final Boolean DEFAULT_EXERCICES_AUTO_CORRIGES = false;
    private static final Boolean UPDATED_EXERCICES_AUTO_CORRIGES = true;

    private static final Boolean DEFAULT_EXPORT_REPONSES_ELEVES = false;
    private static final Boolean UPDATED_EXPORT_REPONSES_ELEVES = true;

    private static final Boolean DEFAULT_IMPORT_DOCUMENT = false;
    private static final Boolean UPDATED_IMPORT_DOCUMENT = true;

    private static final Boolean DEFAULT_EXPORT_DOCUMENT = false;
    private static final Boolean UPDATED_EXPORT_DOCUMENT = true;

    private static final Boolean DEFAULT_EXPORT_SCORM = false;
    private static final Boolean UPDATED_EXPORT_SCORM = true;

    private static final Boolean DEFAULT_PERSONNALISATION_USER_INTERFACE = false;
    private static final Boolean UPDATED_PERSONNALISATION_USER_INTERFACE = true;

    private static final Boolean DEFAULT_MODIF_CONTENU_EDITORIAL = false;
    private static final Boolean UPDATED_MODIF_CONTENU_EDITORIAL = true;

    private static final Boolean DEFAULT_DISPOSITIF_DYS = false;
    private static final Boolean UPDATED_DISPOSITIF_DYS = true;

    private static final String DEFAULT_NB_MAXI_INSTALL = "AAAAAAAAAA";
    private static final String UPDATED_NB_MAXI_INSTALL = "BBBBBBBBBB";

    private static final String DEFAULT_NB_MAX_SIMULT_CONNEXIONS = "AAAAAAAAAA";
    private static final String UPDATED_NB_MAX_SIMULT_CONNEXIONS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_MESSAGERIE = false;
    private static final Boolean UPDATED_MESSAGERIE = true;

    @Autowired
    private TechnoRepository technoRepository;

    @Autowired
    private TechnoMapper technoMapper;

    @Autowired
    private TechnoService technoService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.TechnoSearchRepositoryMockConfiguration
     */
    @Autowired
    private TechnoSearchRepository mockTechnoSearchRepository;

    @Autowired
    private TechnoQueryService technoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTechnoMockMvc;

    private Techno techno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Techno createEntity(EntityManager em) {
        Techno techno = new Techno()
            .technologie(DEFAULT_TECHNOLOGIE)
            .versionReader(DEFAULT_VERSION_READER)
            .availableHorsENT(DEFAULT_AVAILABLE_HORS_ENT)
            .availableViaENT(DEFAULT_AVAILABLE_VIA_ENT)
            .availableViaGAR(DEFAULT_AVAILABLE_VIA_GAR)
            .typeLicenceGAR(DEFAULT_TYPE_LICENCE_GAR)
            .canUseOffline(DEFAULT_CAN_USE_OFFLINE)
            .oneClic(DEFAULT_ONE_CLIC)
            .exportCleUSB(DEFAULT_EXPORT_CLE_USB)
            .deploiementMasse(DEFAULT_DEPLOIEMENT_MASSE)
            .configurationMiniOS(DEFAULT_CONFIGURATION_MINI_OS)
            .needFlash(DEFAULT_NEED_FLASH)
            .annotations(DEFAULT_ANNOTATIONS)
            .creationCours(DEFAULT_CREATION_COURS)
            .webAdaptatif(DEFAULT_WEB_ADAPTATIF)
            .marquePage(DEFAULT_MARQUE_PAGE)
            .captureImage(DEFAULT_CAPTURE_IMAGE)
            .zoom(DEFAULT_ZOOM)
            .fonctionsRecherche(DEFAULT_FONCTIONS_RECHERCHE)
            .corrigesPourEnseignants(DEFAULT_CORRIGES_POUR_ENSEIGNANTS)
            .assignationTachesEleves(DEFAULT_ASSIGNATION_TACHES_ELEVES)
            .partageContenuEleves(DEFAULT_PARTAGE_CONTENU_ELEVES)
            .exercicesInteractifs(DEFAULT_EXERCICES_INTERACTIFS)
            .exercicesAutoCorriges(DEFAULT_EXERCICES_AUTO_CORRIGES)
            .exportReponsesEleves(DEFAULT_EXPORT_REPONSES_ELEVES)
            .importDocument(DEFAULT_IMPORT_DOCUMENT)
            .exportDocument(DEFAULT_EXPORT_DOCUMENT)
            .exportSCORM(DEFAULT_EXPORT_SCORM)
            .personnalisationUserInterface(DEFAULT_PERSONNALISATION_USER_INTERFACE)
            .modifContenuEditorial(DEFAULT_MODIF_CONTENU_EDITORIAL)
            .dispositifDYS(DEFAULT_DISPOSITIF_DYS)
            .nbMaxiInstall(DEFAULT_NB_MAXI_INSTALL)
            .nbMaxSimultConnexions(DEFAULT_NB_MAX_SIMULT_CONNEXIONS)
            .messagerie(DEFAULT_MESSAGERIE);
        return techno;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Techno createUpdatedEntity(EntityManager em) {
        Techno techno = new Techno()
            .technologie(UPDATED_TECHNOLOGIE)
            .versionReader(UPDATED_VERSION_READER)
            .availableHorsENT(UPDATED_AVAILABLE_HORS_ENT)
            .availableViaENT(UPDATED_AVAILABLE_VIA_ENT)
            .availableViaGAR(UPDATED_AVAILABLE_VIA_GAR)
            .typeLicenceGAR(UPDATED_TYPE_LICENCE_GAR)
            .canUseOffline(UPDATED_CAN_USE_OFFLINE)
            .oneClic(UPDATED_ONE_CLIC)
            .exportCleUSB(UPDATED_EXPORT_CLE_USB)
            .deploiementMasse(UPDATED_DEPLOIEMENT_MASSE)
            .configurationMiniOS(UPDATED_CONFIGURATION_MINI_OS)
            .needFlash(UPDATED_NEED_FLASH)
            .annotations(UPDATED_ANNOTATIONS)
            .creationCours(UPDATED_CREATION_COURS)
            .webAdaptatif(UPDATED_WEB_ADAPTATIF)
            .marquePage(UPDATED_MARQUE_PAGE)
            .captureImage(UPDATED_CAPTURE_IMAGE)
            .zoom(UPDATED_ZOOM)
            .fonctionsRecherche(UPDATED_FONCTIONS_RECHERCHE)
            .corrigesPourEnseignants(UPDATED_CORRIGES_POUR_ENSEIGNANTS)
            .assignationTachesEleves(UPDATED_ASSIGNATION_TACHES_ELEVES)
            .partageContenuEleves(UPDATED_PARTAGE_CONTENU_ELEVES)
            .exercicesInteractifs(UPDATED_EXERCICES_INTERACTIFS)
            .exercicesAutoCorriges(UPDATED_EXERCICES_AUTO_CORRIGES)
            .exportReponsesEleves(UPDATED_EXPORT_REPONSES_ELEVES)
            .importDocument(UPDATED_IMPORT_DOCUMENT)
            .exportDocument(UPDATED_EXPORT_DOCUMENT)
            .exportSCORM(UPDATED_EXPORT_SCORM)
            .personnalisationUserInterface(UPDATED_PERSONNALISATION_USER_INTERFACE)
            .modifContenuEditorial(UPDATED_MODIF_CONTENU_EDITORIAL)
            .dispositifDYS(UPDATED_DISPOSITIF_DYS)
            .nbMaxiInstall(UPDATED_NB_MAXI_INSTALL)
            .nbMaxSimultConnexions(UPDATED_NB_MAX_SIMULT_CONNEXIONS)
            .messagerie(UPDATED_MESSAGERIE);
        return techno;
    }

    @BeforeEach
    public void initTest() {
        techno = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechno() throws Exception {
        int databaseSizeBeforeCreate = technoRepository.findAll().size();
        // Create the Techno
        TechnoDTO technoDTO = technoMapper.toDto(techno);
        restTechnoMockMvc.perform(post("/api/technos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technoDTO)))
            .andExpect(status().isCreated());

        // Validate the Techno in the database
        List<Techno> technoList = technoRepository.findAll();
        assertThat(technoList).hasSize(databaseSizeBeforeCreate + 1);
        Techno testTechno = technoList.get(technoList.size() - 1);
        assertThat(testTechno.getTechnologie()).isEqualTo(DEFAULT_TECHNOLOGIE);
        assertThat(testTechno.getVersionReader()).isEqualTo(DEFAULT_VERSION_READER);
        assertThat(testTechno.isAvailableHorsENT()).isEqualTo(DEFAULT_AVAILABLE_HORS_ENT);
        assertThat(testTechno.isAvailableViaENT()).isEqualTo(DEFAULT_AVAILABLE_VIA_ENT);
        assertThat(testTechno.isAvailableViaGAR()).isEqualTo(DEFAULT_AVAILABLE_VIA_GAR);
        assertThat(testTechno.getTypeLicenceGAR()).isEqualTo(DEFAULT_TYPE_LICENCE_GAR);
        assertThat(testTechno.isCanUseOffline()).isEqualTo(DEFAULT_CAN_USE_OFFLINE);
        assertThat(testTechno.isOneClic()).isEqualTo(DEFAULT_ONE_CLIC);
        assertThat(testTechno.isExportCleUSB()).isEqualTo(DEFAULT_EXPORT_CLE_USB);
        assertThat(testTechno.isDeploiementMasse()).isEqualTo(DEFAULT_DEPLOIEMENT_MASSE);
        assertThat(testTechno.getConfigurationMiniOS()).isEqualTo(DEFAULT_CONFIGURATION_MINI_OS);
        assertThat(testTechno.isNeedFlash()).isEqualTo(DEFAULT_NEED_FLASH);
        assertThat(testTechno.isAnnotations()).isEqualTo(DEFAULT_ANNOTATIONS);
        assertThat(testTechno.isCreationCours()).isEqualTo(DEFAULT_CREATION_COURS);
        assertThat(testTechno.isWebAdaptatif()).isEqualTo(DEFAULT_WEB_ADAPTATIF);
        assertThat(testTechno.isMarquePage()).isEqualTo(DEFAULT_MARQUE_PAGE);
        assertThat(testTechno.isCaptureImage()).isEqualTo(DEFAULT_CAPTURE_IMAGE);
        assertThat(testTechno.isZoom()).isEqualTo(DEFAULT_ZOOM);
        assertThat(testTechno.isFonctionsRecherche()).isEqualTo(DEFAULT_FONCTIONS_RECHERCHE);
        assertThat(testTechno.isCorrigesPourEnseignants()).isEqualTo(DEFAULT_CORRIGES_POUR_ENSEIGNANTS);
        assertThat(testTechno.isAssignationTachesEleves()).isEqualTo(DEFAULT_ASSIGNATION_TACHES_ELEVES);
        assertThat(testTechno.isPartageContenuEleves()).isEqualTo(DEFAULT_PARTAGE_CONTENU_ELEVES);
        assertThat(testTechno.isExercicesInteractifs()).isEqualTo(DEFAULT_EXERCICES_INTERACTIFS);
        assertThat(testTechno.isExercicesAutoCorriges()).isEqualTo(DEFAULT_EXERCICES_AUTO_CORRIGES);
        assertThat(testTechno.isExportReponsesEleves()).isEqualTo(DEFAULT_EXPORT_REPONSES_ELEVES);
        assertThat(testTechno.isImportDocument()).isEqualTo(DEFAULT_IMPORT_DOCUMENT);
        assertThat(testTechno.isExportDocument()).isEqualTo(DEFAULT_EXPORT_DOCUMENT);
        assertThat(testTechno.isExportSCORM()).isEqualTo(DEFAULT_EXPORT_SCORM);
        assertThat(testTechno.isPersonnalisationUserInterface()).isEqualTo(DEFAULT_PERSONNALISATION_USER_INTERFACE);
        assertThat(testTechno.isModifContenuEditorial()).isEqualTo(DEFAULT_MODIF_CONTENU_EDITORIAL);
        assertThat(testTechno.isDispositifDYS()).isEqualTo(DEFAULT_DISPOSITIF_DYS);
        assertThat(testTechno.getNbMaxiInstall()).isEqualTo(DEFAULT_NB_MAXI_INSTALL);
        assertThat(testTechno.getNbMaxSimultConnexions()).isEqualTo(DEFAULT_NB_MAX_SIMULT_CONNEXIONS);
        assertThat(testTechno.isMessagerie()).isEqualTo(DEFAULT_MESSAGERIE);

        // Validate the Techno in Elasticsearch
        verify(mockTechnoSearchRepository, times(1)).save(testTechno);
    }

    @Test
    @Transactional
    public void createTechnoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = technoRepository.findAll().size();

        // Create the Techno with an existing ID
        techno.setId(1L);
        TechnoDTO technoDTO = technoMapper.toDto(techno);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechnoMockMvc.perform(post("/api/technos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Techno in the database
        List<Techno> technoList = technoRepository.findAll();
        assertThat(technoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Techno in Elasticsearch
        verify(mockTechnoSearchRepository, times(0)).save(techno);
    }


    @Test
    @Transactional
    public void checkTypeLicenceGARIsRequired() throws Exception {
        int databaseSizeBeforeTest = technoRepository.findAll().size();
        // set the field null
        techno.setTypeLicenceGAR(null);

        // Create the Techno, which fails.
        TechnoDTO technoDTO = technoMapper.toDto(techno);


        restTechnoMockMvc.perform(post("/api/technos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technoDTO)))
            .andExpect(status().isBadRequest());

        List<Techno> technoList = technoRepository.findAll();
        assertThat(technoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTechnos() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList
        restTechnoMockMvc.perform(get("/api/technos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(techno.getId().intValue())))
            .andExpect(jsonPath("$.[*].technologie").value(hasItem(DEFAULT_TECHNOLOGIE.toString())))
            .andExpect(jsonPath("$.[*].versionReader").value(hasItem(DEFAULT_VERSION_READER)))
            .andExpect(jsonPath("$.[*].availableHorsENT").value(hasItem(DEFAULT_AVAILABLE_HORS_ENT.booleanValue())))
            .andExpect(jsonPath("$.[*].availableViaENT").value(hasItem(DEFAULT_AVAILABLE_VIA_ENT.booleanValue())))
            .andExpect(jsonPath("$.[*].availableViaGAR").value(hasItem(DEFAULT_AVAILABLE_VIA_GAR.booleanValue())))
            .andExpect(jsonPath("$.[*].typeLicenceGAR").value(hasItem(DEFAULT_TYPE_LICENCE_GAR.toString())))
            .andExpect(jsonPath("$.[*].canUseOffline").value(hasItem(DEFAULT_CAN_USE_OFFLINE.booleanValue())))
            .andExpect(jsonPath("$.[*].oneClic").value(hasItem(DEFAULT_ONE_CLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].exportCleUSB").value(hasItem(DEFAULT_EXPORT_CLE_USB.booleanValue())))
            .andExpect(jsonPath("$.[*].deploiementMasse").value(hasItem(DEFAULT_DEPLOIEMENT_MASSE.booleanValue())))
            .andExpect(jsonPath("$.[*].configurationMiniOS").value(hasItem(DEFAULT_CONFIGURATION_MINI_OS)))
            .andExpect(jsonPath("$.[*].needFlash").value(hasItem(DEFAULT_NEED_FLASH.booleanValue())))
            .andExpect(jsonPath("$.[*].annotations").value(hasItem(DEFAULT_ANNOTATIONS.booleanValue())))
            .andExpect(jsonPath("$.[*].creationCours").value(hasItem(DEFAULT_CREATION_COURS.booleanValue())))
            .andExpect(jsonPath("$.[*].webAdaptatif").value(hasItem(DEFAULT_WEB_ADAPTATIF.booleanValue())))
            .andExpect(jsonPath("$.[*].marquePage").value(hasItem(DEFAULT_MARQUE_PAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].captureImage").value(hasItem(DEFAULT_CAPTURE_IMAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].zoom").value(hasItem(DEFAULT_ZOOM.booleanValue())))
            .andExpect(jsonPath("$.[*].fonctionsRecherche").value(hasItem(DEFAULT_FONCTIONS_RECHERCHE.booleanValue())))
            .andExpect(jsonPath("$.[*].corrigesPourEnseignants").value(hasItem(DEFAULT_CORRIGES_POUR_ENSEIGNANTS.booleanValue())))
            .andExpect(jsonPath("$.[*].assignationTachesEleves").value(hasItem(DEFAULT_ASSIGNATION_TACHES_ELEVES.booleanValue())))
            .andExpect(jsonPath("$.[*].partageContenuEleves").value(hasItem(DEFAULT_PARTAGE_CONTENU_ELEVES.booleanValue())))
            .andExpect(jsonPath("$.[*].exercicesInteractifs").value(hasItem(DEFAULT_EXERCICES_INTERACTIFS.booleanValue())))
            .andExpect(jsonPath("$.[*].exercicesAutoCorriges").value(hasItem(DEFAULT_EXERCICES_AUTO_CORRIGES.booleanValue())))
            .andExpect(jsonPath("$.[*].exportReponsesEleves").value(hasItem(DEFAULT_EXPORT_REPONSES_ELEVES.booleanValue())))
            .andExpect(jsonPath("$.[*].importDocument").value(hasItem(DEFAULT_IMPORT_DOCUMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].exportDocument").value(hasItem(DEFAULT_EXPORT_DOCUMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].exportSCORM").value(hasItem(DEFAULT_EXPORT_SCORM.booleanValue())))
            .andExpect(jsonPath("$.[*].personnalisationUserInterface").value(hasItem(DEFAULT_PERSONNALISATION_USER_INTERFACE.booleanValue())))
            .andExpect(jsonPath("$.[*].modifContenuEditorial").value(hasItem(DEFAULT_MODIF_CONTENU_EDITORIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].dispositifDYS").value(hasItem(DEFAULT_DISPOSITIF_DYS.booleanValue())))
            .andExpect(jsonPath("$.[*].nbMaxiInstall").value(hasItem(DEFAULT_NB_MAXI_INSTALL)))
            .andExpect(jsonPath("$.[*].nbMaxSimultConnexions").value(hasItem(DEFAULT_NB_MAX_SIMULT_CONNEXIONS)))
            .andExpect(jsonPath("$.[*].messagerie").value(hasItem(DEFAULT_MESSAGERIE.booleanValue())));
    }

    @Test
    @Transactional
    public void getTechno() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get the techno
        restTechnoMockMvc.perform(get("/api/technos/{id}", techno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(techno.getId().intValue()))
            .andExpect(jsonPath("$.technologie").value(DEFAULT_TECHNOLOGIE.toString()))
            .andExpect(jsonPath("$.versionReader").value(DEFAULT_VERSION_READER))
            .andExpect(jsonPath("$.availableHorsENT").value(DEFAULT_AVAILABLE_HORS_ENT.booleanValue()))
            .andExpect(jsonPath("$.availableViaENT").value(DEFAULT_AVAILABLE_VIA_ENT.booleanValue()))
            .andExpect(jsonPath("$.availableViaGAR").value(DEFAULT_AVAILABLE_VIA_GAR.booleanValue()))
            .andExpect(jsonPath("$.typeLicenceGAR").value(DEFAULT_TYPE_LICENCE_GAR.toString()))
            .andExpect(jsonPath("$.canUseOffline").value(DEFAULT_CAN_USE_OFFLINE.booleanValue()))
            .andExpect(jsonPath("$.oneClic").value(DEFAULT_ONE_CLIC.booleanValue()))
            .andExpect(jsonPath("$.exportCleUSB").value(DEFAULT_EXPORT_CLE_USB.booleanValue()))
            .andExpect(jsonPath("$.deploiementMasse").value(DEFAULT_DEPLOIEMENT_MASSE.booleanValue()))
            .andExpect(jsonPath("$.configurationMiniOS").value(DEFAULT_CONFIGURATION_MINI_OS))
            .andExpect(jsonPath("$.needFlash").value(DEFAULT_NEED_FLASH.booleanValue()))
            .andExpect(jsonPath("$.annotations").value(DEFAULT_ANNOTATIONS.booleanValue()))
            .andExpect(jsonPath("$.creationCours").value(DEFAULT_CREATION_COURS.booleanValue()))
            .andExpect(jsonPath("$.webAdaptatif").value(DEFAULT_WEB_ADAPTATIF.booleanValue()))
            .andExpect(jsonPath("$.marquePage").value(DEFAULT_MARQUE_PAGE.booleanValue()))
            .andExpect(jsonPath("$.captureImage").value(DEFAULT_CAPTURE_IMAGE.booleanValue()))
            .andExpect(jsonPath("$.zoom").value(DEFAULT_ZOOM.booleanValue()))
            .andExpect(jsonPath("$.fonctionsRecherche").value(DEFAULT_FONCTIONS_RECHERCHE.booleanValue()))
            .andExpect(jsonPath("$.corrigesPourEnseignants").value(DEFAULT_CORRIGES_POUR_ENSEIGNANTS.booleanValue()))
            .andExpect(jsonPath("$.assignationTachesEleves").value(DEFAULT_ASSIGNATION_TACHES_ELEVES.booleanValue()))
            .andExpect(jsonPath("$.partageContenuEleves").value(DEFAULT_PARTAGE_CONTENU_ELEVES.booleanValue()))
            .andExpect(jsonPath("$.exercicesInteractifs").value(DEFAULT_EXERCICES_INTERACTIFS.booleanValue()))
            .andExpect(jsonPath("$.exercicesAutoCorriges").value(DEFAULT_EXERCICES_AUTO_CORRIGES.booleanValue()))
            .andExpect(jsonPath("$.exportReponsesEleves").value(DEFAULT_EXPORT_REPONSES_ELEVES.booleanValue()))
            .andExpect(jsonPath("$.importDocument").value(DEFAULT_IMPORT_DOCUMENT.booleanValue()))
            .andExpect(jsonPath("$.exportDocument").value(DEFAULT_EXPORT_DOCUMENT.booleanValue()))
            .andExpect(jsonPath("$.exportSCORM").value(DEFAULT_EXPORT_SCORM.booleanValue()))
            .andExpect(jsonPath("$.personnalisationUserInterface").value(DEFAULT_PERSONNALISATION_USER_INTERFACE.booleanValue()))
            .andExpect(jsonPath("$.modifContenuEditorial").value(DEFAULT_MODIF_CONTENU_EDITORIAL.booleanValue()))
            .andExpect(jsonPath("$.dispositifDYS").value(DEFAULT_DISPOSITIF_DYS.booleanValue()))
            .andExpect(jsonPath("$.nbMaxiInstall").value(DEFAULT_NB_MAXI_INSTALL))
            .andExpect(jsonPath("$.nbMaxSimultConnexions").value(DEFAULT_NB_MAX_SIMULT_CONNEXIONS))
            .andExpect(jsonPath("$.messagerie").value(DEFAULT_MESSAGERIE.booleanValue()));
    }


    @Test
    @Transactional
    public void getTechnosByIdFiltering() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        Long id = techno.getId();

        defaultTechnoShouldBeFound("id.equals=" + id);
        defaultTechnoShouldNotBeFound("id.notEquals=" + id);

        defaultTechnoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTechnoShouldNotBeFound("id.greaterThan=" + id);

        defaultTechnoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTechnoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTechnosByTechnologieIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where technologie equals to DEFAULT_TECHNOLOGIE
        defaultTechnoShouldBeFound("technologie.equals=" + DEFAULT_TECHNOLOGIE);

        // Get all the technoList where technologie equals to UPDATED_TECHNOLOGIE
        defaultTechnoShouldNotBeFound("technologie.equals=" + UPDATED_TECHNOLOGIE);
    }

    @Test
    @Transactional
    public void getAllTechnosByTechnologieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where technologie not equals to DEFAULT_TECHNOLOGIE
        defaultTechnoShouldNotBeFound("technologie.notEquals=" + DEFAULT_TECHNOLOGIE);

        // Get all the technoList where technologie not equals to UPDATED_TECHNOLOGIE
        defaultTechnoShouldBeFound("technologie.notEquals=" + UPDATED_TECHNOLOGIE);
    }

    @Test
    @Transactional
    public void getAllTechnosByTechnologieIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where technologie in DEFAULT_TECHNOLOGIE or UPDATED_TECHNOLOGIE
        defaultTechnoShouldBeFound("technologie.in=" + DEFAULT_TECHNOLOGIE + "," + UPDATED_TECHNOLOGIE);

        // Get all the technoList where technologie equals to UPDATED_TECHNOLOGIE
        defaultTechnoShouldNotBeFound("technologie.in=" + UPDATED_TECHNOLOGIE);
    }

    @Test
    @Transactional
    public void getAllTechnosByTechnologieIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where technologie is not null
        defaultTechnoShouldBeFound("technologie.specified=true");

        // Get all the technoList where technologie is null
        defaultTechnoShouldNotBeFound("technologie.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByVersionReaderIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where versionReader equals to DEFAULT_VERSION_READER
        defaultTechnoShouldBeFound("versionReader.equals=" + DEFAULT_VERSION_READER);

        // Get all the technoList where versionReader equals to UPDATED_VERSION_READER
        defaultTechnoShouldNotBeFound("versionReader.equals=" + UPDATED_VERSION_READER);
    }

    @Test
    @Transactional
    public void getAllTechnosByVersionReaderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where versionReader not equals to DEFAULT_VERSION_READER
        defaultTechnoShouldNotBeFound("versionReader.notEquals=" + DEFAULT_VERSION_READER);

        // Get all the technoList where versionReader not equals to UPDATED_VERSION_READER
        defaultTechnoShouldBeFound("versionReader.notEquals=" + UPDATED_VERSION_READER);
    }

    @Test
    @Transactional
    public void getAllTechnosByVersionReaderIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where versionReader in DEFAULT_VERSION_READER or UPDATED_VERSION_READER
        defaultTechnoShouldBeFound("versionReader.in=" + DEFAULT_VERSION_READER + "," + UPDATED_VERSION_READER);

        // Get all the technoList where versionReader equals to UPDATED_VERSION_READER
        defaultTechnoShouldNotBeFound("versionReader.in=" + UPDATED_VERSION_READER);
    }

    @Test
    @Transactional
    public void getAllTechnosByVersionReaderIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where versionReader is not null
        defaultTechnoShouldBeFound("versionReader.specified=true");

        // Get all the technoList where versionReader is null
        defaultTechnoShouldNotBeFound("versionReader.specified=false");
    }
                @Test
    @Transactional
    public void getAllTechnosByVersionReaderContainsSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where versionReader contains DEFAULT_VERSION_READER
        defaultTechnoShouldBeFound("versionReader.contains=" + DEFAULT_VERSION_READER);

        // Get all the technoList where versionReader contains UPDATED_VERSION_READER
        defaultTechnoShouldNotBeFound("versionReader.contains=" + UPDATED_VERSION_READER);
    }

    @Test
    @Transactional
    public void getAllTechnosByVersionReaderNotContainsSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where versionReader does not contain DEFAULT_VERSION_READER
        defaultTechnoShouldNotBeFound("versionReader.doesNotContain=" + DEFAULT_VERSION_READER);

        // Get all the technoList where versionReader does not contain UPDATED_VERSION_READER
        defaultTechnoShouldBeFound("versionReader.doesNotContain=" + UPDATED_VERSION_READER);
    }


    @Test
    @Transactional
    public void getAllTechnosByAvailableHorsENTIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableHorsENT equals to DEFAULT_AVAILABLE_HORS_ENT
        defaultTechnoShouldBeFound("availableHorsENT.equals=" + DEFAULT_AVAILABLE_HORS_ENT);

        // Get all the technoList where availableHorsENT equals to UPDATED_AVAILABLE_HORS_ENT
        defaultTechnoShouldNotBeFound("availableHorsENT.equals=" + UPDATED_AVAILABLE_HORS_ENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByAvailableHorsENTIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableHorsENT not equals to DEFAULT_AVAILABLE_HORS_ENT
        defaultTechnoShouldNotBeFound("availableHorsENT.notEquals=" + DEFAULT_AVAILABLE_HORS_ENT);

        // Get all the technoList where availableHorsENT not equals to UPDATED_AVAILABLE_HORS_ENT
        defaultTechnoShouldBeFound("availableHorsENT.notEquals=" + UPDATED_AVAILABLE_HORS_ENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByAvailableHorsENTIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableHorsENT in DEFAULT_AVAILABLE_HORS_ENT or UPDATED_AVAILABLE_HORS_ENT
        defaultTechnoShouldBeFound("availableHorsENT.in=" + DEFAULT_AVAILABLE_HORS_ENT + "," + UPDATED_AVAILABLE_HORS_ENT);

        // Get all the technoList where availableHorsENT equals to UPDATED_AVAILABLE_HORS_ENT
        defaultTechnoShouldNotBeFound("availableHorsENT.in=" + UPDATED_AVAILABLE_HORS_ENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByAvailableHorsENTIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableHorsENT is not null
        defaultTechnoShouldBeFound("availableHorsENT.specified=true");

        // Get all the technoList where availableHorsENT is null
        defaultTechnoShouldNotBeFound("availableHorsENT.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByAvailableViaENTIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableViaENT equals to DEFAULT_AVAILABLE_VIA_ENT
        defaultTechnoShouldBeFound("availableViaENT.equals=" + DEFAULT_AVAILABLE_VIA_ENT);

        // Get all the technoList where availableViaENT equals to UPDATED_AVAILABLE_VIA_ENT
        defaultTechnoShouldNotBeFound("availableViaENT.equals=" + UPDATED_AVAILABLE_VIA_ENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByAvailableViaENTIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableViaENT not equals to DEFAULT_AVAILABLE_VIA_ENT
        defaultTechnoShouldNotBeFound("availableViaENT.notEquals=" + DEFAULT_AVAILABLE_VIA_ENT);

        // Get all the technoList where availableViaENT not equals to UPDATED_AVAILABLE_VIA_ENT
        defaultTechnoShouldBeFound("availableViaENT.notEquals=" + UPDATED_AVAILABLE_VIA_ENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByAvailableViaENTIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableViaENT in DEFAULT_AVAILABLE_VIA_ENT or UPDATED_AVAILABLE_VIA_ENT
        defaultTechnoShouldBeFound("availableViaENT.in=" + DEFAULT_AVAILABLE_VIA_ENT + "," + UPDATED_AVAILABLE_VIA_ENT);

        // Get all the technoList where availableViaENT equals to UPDATED_AVAILABLE_VIA_ENT
        defaultTechnoShouldNotBeFound("availableViaENT.in=" + UPDATED_AVAILABLE_VIA_ENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByAvailableViaENTIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableViaENT is not null
        defaultTechnoShouldBeFound("availableViaENT.specified=true");

        // Get all the technoList where availableViaENT is null
        defaultTechnoShouldNotBeFound("availableViaENT.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByAvailableViaGARIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableViaGAR equals to DEFAULT_AVAILABLE_VIA_GAR
        defaultTechnoShouldBeFound("availableViaGAR.equals=" + DEFAULT_AVAILABLE_VIA_GAR);

        // Get all the technoList where availableViaGAR equals to UPDATED_AVAILABLE_VIA_GAR
        defaultTechnoShouldNotBeFound("availableViaGAR.equals=" + UPDATED_AVAILABLE_VIA_GAR);
    }

    @Test
    @Transactional
    public void getAllTechnosByAvailableViaGARIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableViaGAR not equals to DEFAULT_AVAILABLE_VIA_GAR
        defaultTechnoShouldNotBeFound("availableViaGAR.notEquals=" + DEFAULT_AVAILABLE_VIA_GAR);

        // Get all the technoList where availableViaGAR not equals to UPDATED_AVAILABLE_VIA_GAR
        defaultTechnoShouldBeFound("availableViaGAR.notEquals=" + UPDATED_AVAILABLE_VIA_GAR);
    }

    @Test
    @Transactional
    public void getAllTechnosByAvailableViaGARIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableViaGAR in DEFAULT_AVAILABLE_VIA_GAR or UPDATED_AVAILABLE_VIA_GAR
        defaultTechnoShouldBeFound("availableViaGAR.in=" + DEFAULT_AVAILABLE_VIA_GAR + "," + UPDATED_AVAILABLE_VIA_GAR);

        // Get all the technoList where availableViaGAR equals to UPDATED_AVAILABLE_VIA_GAR
        defaultTechnoShouldNotBeFound("availableViaGAR.in=" + UPDATED_AVAILABLE_VIA_GAR);
    }

    @Test
    @Transactional
    public void getAllTechnosByAvailableViaGARIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where availableViaGAR is not null
        defaultTechnoShouldBeFound("availableViaGAR.specified=true");

        // Get all the technoList where availableViaGAR is null
        defaultTechnoShouldNotBeFound("availableViaGAR.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByTypeLicenceGARIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where typeLicenceGAR equals to DEFAULT_TYPE_LICENCE_GAR
        defaultTechnoShouldBeFound("typeLicenceGAR.equals=" + DEFAULT_TYPE_LICENCE_GAR);

        // Get all the technoList where typeLicenceGAR equals to UPDATED_TYPE_LICENCE_GAR
        defaultTechnoShouldNotBeFound("typeLicenceGAR.equals=" + UPDATED_TYPE_LICENCE_GAR);
    }

    @Test
    @Transactional
    public void getAllTechnosByTypeLicenceGARIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where typeLicenceGAR not equals to DEFAULT_TYPE_LICENCE_GAR
        defaultTechnoShouldNotBeFound("typeLicenceGAR.notEquals=" + DEFAULT_TYPE_LICENCE_GAR);

        // Get all the technoList where typeLicenceGAR not equals to UPDATED_TYPE_LICENCE_GAR
        defaultTechnoShouldBeFound("typeLicenceGAR.notEquals=" + UPDATED_TYPE_LICENCE_GAR);
    }

    @Test
    @Transactional
    public void getAllTechnosByTypeLicenceGARIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where typeLicenceGAR in DEFAULT_TYPE_LICENCE_GAR or UPDATED_TYPE_LICENCE_GAR
        defaultTechnoShouldBeFound("typeLicenceGAR.in=" + DEFAULT_TYPE_LICENCE_GAR + "," + UPDATED_TYPE_LICENCE_GAR);

        // Get all the technoList where typeLicenceGAR equals to UPDATED_TYPE_LICENCE_GAR
        defaultTechnoShouldNotBeFound("typeLicenceGAR.in=" + UPDATED_TYPE_LICENCE_GAR);
    }

    @Test
    @Transactional
    public void getAllTechnosByTypeLicenceGARIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where typeLicenceGAR is not null
        defaultTechnoShouldBeFound("typeLicenceGAR.specified=true");

        // Get all the technoList where typeLicenceGAR is null
        defaultTechnoShouldNotBeFound("typeLicenceGAR.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByCanUseOfflineIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where canUseOffline equals to DEFAULT_CAN_USE_OFFLINE
        defaultTechnoShouldBeFound("canUseOffline.equals=" + DEFAULT_CAN_USE_OFFLINE);

        // Get all the technoList where canUseOffline equals to UPDATED_CAN_USE_OFFLINE
        defaultTechnoShouldNotBeFound("canUseOffline.equals=" + UPDATED_CAN_USE_OFFLINE);
    }

    @Test
    @Transactional
    public void getAllTechnosByCanUseOfflineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where canUseOffline not equals to DEFAULT_CAN_USE_OFFLINE
        defaultTechnoShouldNotBeFound("canUseOffline.notEquals=" + DEFAULT_CAN_USE_OFFLINE);

        // Get all the technoList where canUseOffline not equals to UPDATED_CAN_USE_OFFLINE
        defaultTechnoShouldBeFound("canUseOffline.notEquals=" + UPDATED_CAN_USE_OFFLINE);
    }

    @Test
    @Transactional
    public void getAllTechnosByCanUseOfflineIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where canUseOffline in DEFAULT_CAN_USE_OFFLINE or UPDATED_CAN_USE_OFFLINE
        defaultTechnoShouldBeFound("canUseOffline.in=" + DEFAULT_CAN_USE_OFFLINE + "," + UPDATED_CAN_USE_OFFLINE);

        // Get all the technoList where canUseOffline equals to UPDATED_CAN_USE_OFFLINE
        defaultTechnoShouldNotBeFound("canUseOffline.in=" + UPDATED_CAN_USE_OFFLINE);
    }

    @Test
    @Transactional
    public void getAllTechnosByCanUseOfflineIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where canUseOffline is not null
        defaultTechnoShouldBeFound("canUseOffline.specified=true");

        // Get all the technoList where canUseOffline is null
        defaultTechnoShouldNotBeFound("canUseOffline.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByOneClicIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where oneClic equals to DEFAULT_ONE_CLIC
        defaultTechnoShouldBeFound("oneClic.equals=" + DEFAULT_ONE_CLIC);

        // Get all the technoList where oneClic equals to UPDATED_ONE_CLIC
        defaultTechnoShouldNotBeFound("oneClic.equals=" + UPDATED_ONE_CLIC);
    }

    @Test
    @Transactional
    public void getAllTechnosByOneClicIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where oneClic not equals to DEFAULT_ONE_CLIC
        defaultTechnoShouldNotBeFound("oneClic.notEquals=" + DEFAULT_ONE_CLIC);

        // Get all the technoList where oneClic not equals to UPDATED_ONE_CLIC
        defaultTechnoShouldBeFound("oneClic.notEquals=" + UPDATED_ONE_CLIC);
    }

    @Test
    @Transactional
    public void getAllTechnosByOneClicIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where oneClic in DEFAULT_ONE_CLIC or UPDATED_ONE_CLIC
        defaultTechnoShouldBeFound("oneClic.in=" + DEFAULT_ONE_CLIC + "," + UPDATED_ONE_CLIC);

        // Get all the technoList where oneClic equals to UPDATED_ONE_CLIC
        defaultTechnoShouldNotBeFound("oneClic.in=" + UPDATED_ONE_CLIC);
    }

    @Test
    @Transactional
    public void getAllTechnosByOneClicIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where oneClic is not null
        defaultTechnoShouldBeFound("oneClic.specified=true");

        // Get all the technoList where oneClic is null
        defaultTechnoShouldNotBeFound("oneClic.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByExportCleUSBIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportCleUSB equals to DEFAULT_EXPORT_CLE_USB
        defaultTechnoShouldBeFound("exportCleUSB.equals=" + DEFAULT_EXPORT_CLE_USB);

        // Get all the technoList where exportCleUSB equals to UPDATED_EXPORT_CLE_USB
        defaultTechnoShouldNotBeFound("exportCleUSB.equals=" + UPDATED_EXPORT_CLE_USB);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportCleUSBIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportCleUSB not equals to DEFAULT_EXPORT_CLE_USB
        defaultTechnoShouldNotBeFound("exportCleUSB.notEquals=" + DEFAULT_EXPORT_CLE_USB);

        // Get all the technoList where exportCleUSB not equals to UPDATED_EXPORT_CLE_USB
        defaultTechnoShouldBeFound("exportCleUSB.notEquals=" + UPDATED_EXPORT_CLE_USB);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportCleUSBIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportCleUSB in DEFAULT_EXPORT_CLE_USB or UPDATED_EXPORT_CLE_USB
        defaultTechnoShouldBeFound("exportCleUSB.in=" + DEFAULT_EXPORT_CLE_USB + "," + UPDATED_EXPORT_CLE_USB);

        // Get all the technoList where exportCleUSB equals to UPDATED_EXPORT_CLE_USB
        defaultTechnoShouldNotBeFound("exportCleUSB.in=" + UPDATED_EXPORT_CLE_USB);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportCleUSBIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportCleUSB is not null
        defaultTechnoShouldBeFound("exportCleUSB.specified=true");

        // Get all the technoList where exportCleUSB is null
        defaultTechnoShouldNotBeFound("exportCleUSB.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByDeploiementMasseIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where deploiementMasse equals to DEFAULT_DEPLOIEMENT_MASSE
        defaultTechnoShouldBeFound("deploiementMasse.equals=" + DEFAULT_DEPLOIEMENT_MASSE);

        // Get all the technoList where deploiementMasse equals to UPDATED_DEPLOIEMENT_MASSE
        defaultTechnoShouldNotBeFound("deploiementMasse.equals=" + UPDATED_DEPLOIEMENT_MASSE);
    }

    @Test
    @Transactional
    public void getAllTechnosByDeploiementMasseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where deploiementMasse not equals to DEFAULT_DEPLOIEMENT_MASSE
        defaultTechnoShouldNotBeFound("deploiementMasse.notEquals=" + DEFAULT_DEPLOIEMENT_MASSE);

        // Get all the technoList where deploiementMasse not equals to UPDATED_DEPLOIEMENT_MASSE
        defaultTechnoShouldBeFound("deploiementMasse.notEquals=" + UPDATED_DEPLOIEMENT_MASSE);
    }

    @Test
    @Transactional
    public void getAllTechnosByDeploiementMasseIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where deploiementMasse in DEFAULT_DEPLOIEMENT_MASSE or UPDATED_DEPLOIEMENT_MASSE
        defaultTechnoShouldBeFound("deploiementMasse.in=" + DEFAULT_DEPLOIEMENT_MASSE + "," + UPDATED_DEPLOIEMENT_MASSE);

        // Get all the technoList where deploiementMasse equals to UPDATED_DEPLOIEMENT_MASSE
        defaultTechnoShouldNotBeFound("deploiementMasse.in=" + UPDATED_DEPLOIEMENT_MASSE);
    }

    @Test
    @Transactional
    public void getAllTechnosByDeploiementMasseIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where deploiementMasse is not null
        defaultTechnoShouldBeFound("deploiementMasse.specified=true");

        // Get all the technoList where deploiementMasse is null
        defaultTechnoShouldNotBeFound("deploiementMasse.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByConfigurationMiniOSIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where configurationMiniOS equals to DEFAULT_CONFIGURATION_MINI_OS
        defaultTechnoShouldBeFound("configurationMiniOS.equals=" + DEFAULT_CONFIGURATION_MINI_OS);

        // Get all the technoList where configurationMiniOS equals to UPDATED_CONFIGURATION_MINI_OS
        defaultTechnoShouldNotBeFound("configurationMiniOS.equals=" + UPDATED_CONFIGURATION_MINI_OS);
    }

    @Test
    @Transactional
    public void getAllTechnosByConfigurationMiniOSIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where configurationMiniOS not equals to DEFAULT_CONFIGURATION_MINI_OS
        defaultTechnoShouldNotBeFound("configurationMiniOS.notEquals=" + DEFAULT_CONFIGURATION_MINI_OS);

        // Get all the technoList where configurationMiniOS not equals to UPDATED_CONFIGURATION_MINI_OS
        defaultTechnoShouldBeFound("configurationMiniOS.notEquals=" + UPDATED_CONFIGURATION_MINI_OS);
    }

    @Test
    @Transactional
    public void getAllTechnosByConfigurationMiniOSIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where configurationMiniOS in DEFAULT_CONFIGURATION_MINI_OS or UPDATED_CONFIGURATION_MINI_OS
        defaultTechnoShouldBeFound("configurationMiniOS.in=" + DEFAULT_CONFIGURATION_MINI_OS + "," + UPDATED_CONFIGURATION_MINI_OS);

        // Get all the technoList where configurationMiniOS equals to UPDATED_CONFIGURATION_MINI_OS
        defaultTechnoShouldNotBeFound("configurationMiniOS.in=" + UPDATED_CONFIGURATION_MINI_OS);
    }

    @Test
    @Transactional
    public void getAllTechnosByConfigurationMiniOSIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where configurationMiniOS is not null
        defaultTechnoShouldBeFound("configurationMiniOS.specified=true");

        // Get all the technoList where configurationMiniOS is null
        defaultTechnoShouldNotBeFound("configurationMiniOS.specified=false");
    }
                @Test
    @Transactional
    public void getAllTechnosByConfigurationMiniOSContainsSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where configurationMiniOS contains DEFAULT_CONFIGURATION_MINI_OS
        defaultTechnoShouldBeFound("configurationMiniOS.contains=" + DEFAULT_CONFIGURATION_MINI_OS);

        // Get all the technoList where configurationMiniOS contains UPDATED_CONFIGURATION_MINI_OS
        defaultTechnoShouldNotBeFound("configurationMiniOS.contains=" + UPDATED_CONFIGURATION_MINI_OS);
    }

    @Test
    @Transactional
    public void getAllTechnosByConfigurationMiniOSNotContainsSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where configurationMiniOS does not contain DEFAULT_CONFIGURATION_MINI_OS
        defaultTechnoShouldNotBeFound("configurationMiniOS.doesNotContain=" + DEFAULT_CONFIGURATION_MINI_OS);

        // Get all the technoList where configurationMiniOS does not contain UPDATED_CONFIGURATION_MINI_OS
        defaultTechnoShouldBeFound("configurationMiniOS.doesNotContain=" + UPDATED_CONFIGURATION_MINI_OS);
    }


    @Test
    @Transactional
    public void getAllTechnosByNeedFlashIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where needFlash equals to DEFAULT_NEED_FLASH
        defaultTechnoShouldBeFound("needFlash.equals=" + DEFAULT_NEED_FLASH);

        // Get all the technoList where needFlash equals to UPDATED_NEED_FLASH
        defaultTechnoShouldNotBeFound("needFlash.equals=" + UPDATED_NEED_FLASH);
    }

    @Test
    @Transactional
    public void getAllTechnosByNeedFlashIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where needFlash not equals to DEFAULT_NEED_FLASH
        defaultTechnoShouldNotBeFound("needFlash.notEquals=" + DEFAULT_NEED_FLASH);

        // Get all the technoList where needFlash not equals to UPDATED_NEED_FLASH
        defaultTechnoShouldBeFound("needFlash.notEquals=" + UPDATED_NEED_FLASH);
    }

    @Test
    @Transactional
    public void getAllTechnosByNeedFlashIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where needFlash in DEFAULT_NEED_FLASH or UPDATED_NEED_FLASH
        defaultTechnoShouldBeFound("needFlash.in=" + DEFAULT_NEED_FLASH + "," + UPDATED_NEED_FLASH);

        // Get all the technoList where needFlash equals to UPDATED_NEED_FLASH
        defaultTechnoShouldNotBeFound("needFlash.in=" + UPDATED_NEED_FLASH);
    }

    @Test
    @Transactional
    public void getAllTechnosByNeedFlashIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where needFlash is not null
        defaultTechnoShouldBeFound("needFlash.specified=true");

        // Get all the technoList where needFlash is null
        defaultTechnoShouldNotBeFound("needFlash.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByAnnotationsIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where annotations equals to DEFAULT_ANNOTATIONS
        defaultTechnoShouldBeFound("annotations.equals=" + DEFAULT_ANNOTATIONS);

        // Get all the technoList where annotations equals to UPDATED_ANNOTATIONS
        defaultTechnoShouldNotBeFound("annotations.equals=" + UPDATED_ANNOTATIONS);
    }

    @Test
    @Transactional
    public void getAllTechnosByAnnotationsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where annotations not equals to DEFAULT_ANNOTATIONS
        defaultTechnoShouldNotBeFound("annotations.notEquals=" + DEFAULT_ANNOTATIONS);

        // Get all the technoList where annotations not equals to UPDATED_ANNOTATIONS
        defaultTechnoShouldBeFound("annotations.notEquals=" + UPDATED_ANNOTATIONS);
    }

    @Test
    @Transactional
    public void getAllTechnosByAnnotationsIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where annotations in DEFAULT_ANNOTATIONS or UPDATED_ANNOTATIONS
        defaultTechnoShouldBeFound("annotations.in=" + DEFAULT_ANNOTATIONS + "," + UPDATED_ANNOTATIONS);

        // Get all the technoList where annotations equals to UPDATED_ANNOTATIONS
        defaultTechnoShouldNotBeFound("annotations.in=" + UPDATED_ANNOTATIONS);
    }

    @Test
    @Transactional
    public void getAllTechnosByAnnotationsIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where annotations is not null
        defaultTechnoShouldBeFound("annotations.specified=true");

        // Get all the technoList where annotations is null
        defaultTechnoShouldNotBeFound("annotations.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByCreationCoursIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where creationCours equals to DEFAULT_CREATION_COURS
        defaultTechnoShouldBeFound("creationCours.equals=" + DEFAULT_CREATION_COURS);

        // Get all the technoList where creationCours equals to UPDATED_CREATION_COURS
        defaultTechnoShouldNotBeFound("creationCours.equals=" + UPDATED_CREATION_COURS);
    }

    @Test
    @Transactional
    public void getAllTechnosByCreationCoursIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where creationCours not equals to DEFAULT_CREATION_COURS
        defaultTechnoShouldNotBeFound("creationCours.notEquals=" + DEFAULT_CREATION_COURS);

        // Get all the technoList where creationCours not equals to UPDATED_CREATION_COURS
        defaultTechnoShouldBeFound("creationCours.notEquals=" + UPDATED_CREATION_COURS);
    }

    @Test
    @Transactional
    public void getAllTechnosByCreationCoursIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where creationCours in DEFAULT_CREATION_COURS or UPDATED_CREATION_COURS
        defaultTechnoShouldBeFound("creationCours.in=" + DEFAULT_CREATION_COURS + "," + UPDATED_CREATION_COURS);

        // Get all the technoList where creationCours equals to UPDATED_CREATION_COURS
        defaultTechnoShouldNotBeFound("creationCours.in=" + UPDATED_CREATION_COURS);
    }

    @Test
    @Transactional
    public void getAllTechnosByCreationCoursIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where creationCours is not null
        defaultTechnoShouldBeFound("creationCours.specified=true");

        // Get all the technoList where creationCours is null
        defaultTechnoShouldNotBeFound("creationCours.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByWebAdaptatifIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where webAdaptatif equals to DEFAULT_WEB_ADAPTATIF
        defaultTechnoShouldBeFound("webAdaptatif.equals=" + DEFAULT_WEB_ADAPTATIF);

        // Get all the technoList where webAdaptatif equals to UPDATED_WEB_ADAPTATIF
        defaultTechnoShouldNotBeFound("webAdaptatif.equals=" + UPDATED_WEB_ADAPTATIF);
    }

    @Test
    @Transactional
    public void getAllTechnosByWebAdaptatifIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where webAdaptatif not equals to DEFAULT_WEB_ADAPTATIF
        defaultTechnoShouldNotBeFound("webAdaptatif.notEquals=" + DEFAULT_WEB_ADAPTATIF);

        // Get all the technoList where webAdaptatif not equals to UPDATED_WEB_ADAPTATIF
        defaultTechnoShouldBeFound("webAdaptatif.notEquals=" + UPDATED_WEB_ADAPTATIF);
    }

    @Test
    @Transactional
    public void getAllTechnosByWebAdaptatifIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where webAdaptatif in DEFAULT_WEB_ADAPTATIF or UPDATED_WEB_ADAPTATIF
        defaultTechnoShouldBeFound("webAdaptatif.in=" + DEFAULT_WEB_ADAPTATIF + "," + UPDATED_WEB_ADAPTATIF);

        // Get all the technoList where webAdaptatif equals to UPDATED_WEB_ADAPTATIF
        defaultTechnoShouldNotBeFound("webAdaptatif.in=" + UPDATED_WEB_ADAPTATIF);
    }

    @Test
    @Transactional
    public void getAllTechnosByWebAdaptatifIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where webAdaptatif is not null
        defaultTechnoShouldBeFound("webAdaptatif.specified=true");

        // Get all the technoList where webAdaptatif is null
        defaultTechnoShouldNotBeFound("webAdaptatif.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByMarquePageIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where marquePage equals to DEFAULT_MARQUE_PAGE
        defaultTechnoShouldBeFound("marquePage.equals=" + DEFAULT_MARQUE_PAGE);

        // Get all the technoList where marquePage equals to UPDATED_MARQUE_PAGE
        defaultTechnoShouldNotBeFound("marquePage.equals=" + UPDATED_MARQUE_PAGE);
    }

    @Test
    @Transactional
    public void getAllTechnosByMarquePageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where marquePage not equals to DEFAULT_MARQUE_PAGE
        defaultTechnoShouldNotBeFound("marquePage.notEquals=" + DEFAULT_MARQUE_PAGE);

        // Get all the technoList where marquePage not equals to UPDATED_MARQUE_PAGE
        defaultTechnoShouldBeFound("marquePage.notEquals=" + UPDATED_MARQUE_PAGE);
    }

    @Test
    @Transactional
    public void getAllTechnosByMarquePageIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where marquePage in DEFAULT_MARQUE_PAGE or UPDATED_MARQUE_PAGE
        defaultTechnoShouldBeFound("marquePage.in=" + DEFAULT_MARQUE_PAGE + "," + UPDATED_MARQUE_PAGE);

        // Get all the technoList where marquePage equals to UPDATED_MARQUE_PAGE
        defaultTechnoShouldNotBeFound("marquePage.in=" + UPDATED_MARQUE_PAGE);
    }

    @Test
    @Transactional
    public void getAllTechnosByMarquePageIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where marquePage is not null
        defaultTechnoShouldBeFound("marquePage.specified=true");

        // Get all the technoList where marquePage is null
        defaultTechnoShouldNotBeFound("marquePage.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByCaptureImageIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where captureImage equals to DEFAULT_CAPTURE_IMAGE
        defaultTechnoShouldBeFound("captureImage.equals=" + DEFAULT_CAPTURE_IMAGE);

        // Get all the technoList where captureImage equals to UPDATED_CAPTURE_IMAGE
        defaultTechnoShouldNotBeFound("captureImage.equals=" + UPDATED_CAPTURE_IMAGE);
    }

    @Test
    @Transactional
    public void getAllTechnosByCaptureImageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where captureImage not equals to DEFAULT_CAPTURE_IMAGE
        defaultTechnoShouldNotBeFound("captureImage.notEquals=" + DEFAULT_CAPTURE_IMAGE);

        // Get all the technoList where captureImage not equals to UPDATED_CAPTURE_IMAGE
        defaultTechnoShouldBeFound("captureImage.notEquals=" + UPDATED_CAPTURE_IMAGE);
    }

    @Test
    @Transactional
    public void getAllTechnosByCaptureImageIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where captureImage in DEFAULT_CAPTURE_IMAGE or UPDATED_CAPTURE_IMAGE
        defaultTechnoShouldBeFound("captureImage.in=" + DEFAULT_CAPTURE_IMAGE + "," + UPDATED_CAPTURE_IMAGE);

        // Get all the technoList where captureImage equals to UPDATED_CAPTURE_IMAGE
        defaultTechnoShouldNotBeFound("captureImage.in=" + UPDATED_CAPTURE_IMAGE);
    }

    @Test
    @Transactional
    public void getAllTechnosByCaptureImageIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where captureImage is not null
        defaultTechnoShouldBeFound("captureImage.specified=true");

        // Get all the technoList where captureImage is null
        defaultTechnoShouldNotBeFound("captureImage.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByZoomIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where zoom equals to DEFAULT_ZOOM
        defaultTechnoShouldBeFound("zoom.equals=" + DEFAULT_ZOOM);

        // Get all the technoList where zoom equals to UPDATED_ZOOM
        defaultTechnoShouldNotBeFound("zoom.equals=" + UPDATED_ZOOM);
    }

    @Test
    @Transactional
    public void getAllTechnosByZoomIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where zoom not equals to DEFAULT_ZOOM
        defaultTechnoShouldNotBeFound("zoom.notEquals=" + DEFAULT_ZOOM);

        // Get all the technoList where zoom not equals to UPDATED_ZOOM
        defaultTechnoShouldBeFound("zoom.notEquals=" + UPDATED_ZOOM);
    }

    @Test
    @Transactional
    public void getAllTechnosByZoomIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where zoom in DEFAULT_ZOOM or UPDATED_ZOOM
        defaultTechnoShouldBeFound("zoom.in=" + DEFAULT_ZOOM + "," + UPDATED_ZOOM);

        // Get all the technoList where zoom equals to UPDATED_ZOOM
        defaultTechnoShouldNotBeFound("zoom.in=" + UPDATED_ZOOM);
    }

    @Test
    @Transactional
    public void getAllTechnosByZoomIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where zoom is not null
        defaultTechnoShouldBeFound("zoom.specified=true");

        // Get all the technoList where zoom is null
        defaultTechnoShouldNotBeFound("zoom.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByFonctionsRechercheIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where fonctionsRecherche equals to DEFAULT_FONCTIONS_RECHERCHE
        defaultTechnoShouldBeFound("fonctionsRecherche.equals=" + DEFAULT_FONCTIONS_RECHERCHE);

        // Get all the technoList where fonctionsRecherche equals to UPDATED_FONCTIONS_RECHERCHE
        defaultTechnoShouldNotBeFound("fonctionsRecherche.equals=" + UPDATED_FONCTIONS_RECHERCHE);
    }

    @Test
    @Transactional
    public void getAllTechnosByFonctionsRechercheIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where fonctionsRecherche not equals to DEFAULT_FONCTIONS_RECHERCHE
        defaultTechnoShouldNotBeFound("fonctionsRecherche.notEquals=" + DEFAULT_FONCTIONS_RECHERCHE);

        // Get all the technoList where fonctionsRecherche not equals to UPDATED_FONCTIONS_RECHERCHE
        defaultTechnoShouldBeFound("fonctionsRecherche.notEquals=" + UPDATED_FONCTIONS_RECHERCHE);
    }

    @Test
    @Transactional
    public void getAllTechnosByFonctionsRechercheIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where fonctionsRecherche in DEFAULT_FONCTIONS_RECHERCHE or UPDATED_FONCTIONS_RECHERCHE
        defaultTechnoShouldBeFound("fonctionsRecherche.in=" + DEFAULT_FONCTIONS_RECHERCHE + "," + UPDATED_FONCTIONS_RECHERCHE);

        // Get all the technoList where fonctionsRecherche equals to UPDATED_FONCTIONS_RECHERCHE
        defaultTechnoShouldNotBeFound("fonctionsRecherche.in=" + UPDATED_FONCTIONS_RECHERCHE);
    }

    @Test
    @Transactional
    public void getAllTechnosByFonctionsRechercheIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where fonctionsRecherche is not null
        defaultTechnoShouldBeFound("fonctionsRecherche.specified=true");

        // Get all the technoList where fonctionsRecherche is null
        defaultTechnoShouldNotBeFound("fonctionsRecherche.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByCorrigesPourEnseignantsIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where corrigesPourEnseignants equals to DEFAULT_CORRIGES_POUR_ENSEIGNANTS
        defaultTechnoShouldBeFound("corrigesPourEnseignants.equals=" + DEFAULT_CORRIGES_POUR_ENSEIGNANTS);

        // Get all the technoList where corrigesPourEnseignants equals to UPDATED_CORRIGES_POUR_ENSEIGNANTS
        defaultTechnoShouldNotBeFound("corrigesPourEnseignants.equals=" + UPDATED_CORRIGES_POUR_ENSEIGNANTS);
    }

    @Test
    @Transactional
    public void getAllTechnosByCorrigesPourEnseignantsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where corrigesPourEnseignants not equals to DEFAULT_CORRIGES_POUR_ENSEIGNANTS
        defaultTechnoShouldNotBeFound("corrigesPourEnseignants.notEquals=" + DEFAULT_CORRIGES_POUR_ENSEIGNANTS);

        // Get all the technoList where corrigesPourEnseignants not equals to UPDATED_CORRIGES_POUR_ENSEIGNANTS
        defaultTechnoShouldBeFound("corrigesPourEnseignants.notEquals=" + UPDATED_CORRIGES_POUR_ENSEIGNANTS);
    }

    @Test
    @Transactional
    public void getAllTechnosByCorrigesPourEnseignantsIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where corrigesPourEnseignants in DEFAULT_CORRIGES_POUR_ENSEIGNANTS or UPDATED_CORRIGES_POUR_ENSEIGNANTS
        defaultTechnoShouldBeFound("corrigesPourEnseignants.in=" + DEFAULT_CORRIGES_POUR_ENSEIGNANTS + "," + UPDATED_CORRIGES_POUR_ENSEIGNANTS);

        // Get all the technoList where corrigesPourEnseignants equals to UPDATED_CORRIGES_POUR_ENSEIGNANTS
        defaultTechnoShouldNotBeFound("corrigesPourEnseignants.in=" + UPDATED_CORRIGES_POUR_ENSEIGNANTS);
    }

    @Test
    @Transactional
    public void getAllTechnosByCorrigesPourEnseignantsIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where corrigesPourEnseignants is not null
        defaultTechnoShouldBeFound("corrigesPourEnseignants.specified=true");

        // Get all the technoList where corrigesPourEnseignants is null
        defaultTechnoShouldNotBeFound("corrigesPourEnseignants.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByAssignationTachesElevesIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where assignationTachesEleves equals to DEFAULT_ASSIGNATION_TACHES_ELEVES
        defaultTechnoShouldBeFound("assignationTachesEleves.equals=" + DEFAULT_ASSIGNATION_TACHES_ELEVES);

        // Get all the technoList where assignationTachesEleves equals to UPDATED_ASSIGNATION_TACHES_ELEVES
        defaultTechnoShouldNotBeFound("assignationTachesEleves.equals=" + UPDATED_ASSIGNATION_TACHES_ELEVES);
    }

    @Test
    @Transactional
    public void getAllTechnosByAssignationTachesElevesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where assignationTachesEleves not equals to DEFAULT_ASSIGNATION_TACHES_ELEVES
        defaultTechnoShouldNotBeFound("assignationTachesEleves.notEquals=" + DEFAULT_ASSIGNATION_TACHES_ELEVES);

        // Get all the technoList where assignationTachesEleves not equals to UPDATED_ASSIGNATION_TACHES_ELEVES
        defaultTechnoShouldBeFound("assignationTachesEleves.notEquals=" + UPDATED_ASSIGNATION_TACHES_ELEVES);
    }

    @Test
    @Transactional
    public void getAllTechnosByAssignationTachesElevesIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where assignationTachesEleves in DEFAULT_ASSIGNATION_TACHES_ELEVES or UPDATED_ASSIGNATION_TACHES_ELEVES
        defaultTechnoShouldBeFound("assignationTachesEleves.in=" + DEFAULT_ASSIGNATION_TACHES_ELEVES + "," + UPDATED_ASSIGNATION_TACHES_ELEVES);

        // Get all the technoList where assignationTachesEleves equals to UPDATED_ASSIGNATION_TACHES_ELEVES
        defaultTechnoShouldNotBeFound("assignationTachesEleves.in=" + UPDATED_ASSIGNATION_TACHES_ELEVES);
    }

    @Test
    @Transactional
    public void getAllTechnosByAssignationTachesElevesIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where assignationTachesEleves is not null
        defaultTechnoShouldBeFound("assignationTachesEleves.specified=true");

        // Get all the technoList where assignationTachesEleves is null
        defaultTechnoShouldNotBeFound("assignationTachesEleves.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByPartageContenuElevesIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where partageContenuEleves equals to DEFAULT_PARTAGE_CONTENU_ELEVES
        defaultTechnoShouldBeFound("partageContenuEleves.equals=" + DEFAULT_PARTAGE_CONTENU_ELEVES);

        // Get all the technoList where partageContenuEleves equals to UPDATED_PARTAGE_CONTENU_ELEVES
        defaultTechnoShouldNotBeFound("partageContenuEleves.equals=" + UPDATED_PARTAGE_CONTENU_ELEVES);
    }

    @Test
    @Transactional
    public void getAllTechnosByPartageContenuElevesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where partageContenuEleves not equals to DEFAULT_PARTAGE_CONTENU_ELEVES
        defaultTechnoShouldNotBeFound("partageContenuEleves.notEquals=" + DEFAULT_PARTAGE_CONTENU_ELEVES);

        // Get all the technoList where partageContenuEleves not equals to UPDATED_PARTAGE_CONTENU_ELEVES
        defaultTechnoShouldBeFound("partageContenuEleves.notEquals=" + UPDATED_PARTAGE_CONTENU_ELEVES);
    }

    @Test
    @Transactional
    public void getAllTechnosByPartageContenuElevesIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where partageContenuEleves in DEFAULT_PARTAGE_CONTENU_ELEVES or UPDATED_PARTAGE_CONTENU_ELEVES
        defaultTechnoShouldBeFound("partageContenuEleves.in=" + DEFAULT_PARTAGE_CONTENU_ELEVES + "," + UPDATED_PARTAGE_CONTENU_ELEVES);

        // Get all the technoList where partageContenuEleves equals to UPDATED_PARTAGE_CONTENU_ELEVES
        defaultTechnoShouldNotBeFound("partageContenuEleves.in=" + UPDATED_PARTAGE_CONTENU_ELEVES);
    }

    @Test
    @Transactional
    public void getAllTechnosByPartageContenuElevesIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where partageContenuEleves is not null
        defaultTechnoShouldBeFound("partageContenuEleves.specified=true");

        // Get all the technoList where partageContenuEleves is null
        defaultTechnoShouldNotBeFound("partageContenuEleves.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByExercicesInteractifsIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exercicesInteractifs equals to DEFAULT_EXERCICES_INTERACTIFS
        defaultTechnoShouldBeFound("exercicesInteractifs.equals=" + DEFAULT_EXERCICES_INTERACTIFS);

        // Get all the technoList where exercicesInteractifs equals to UPDATED_EXERCICES_INTERACTIFS
        defaultTechnoShouldNotBeFound("exercicesInteractifs.equals=" + UPDATED_EXERCICES_INTERACTIFS);
    }

    @Test
    @Transactional
    public void getAllTechnosByExercicesInteractifsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exercicesInteractifs not equals to DEFAULT_EXERCICES_INTERACTIFS
        defaultTechnoShouldNotBeFound("exercicesInteractifs.notEquals=" + DEFAULT_EXERCICES_INTERACTIFS);

        // Get all the technoList where exercicesInteractifs not equals to UPDATED_EXERCICES_INTERACTIFS
        defaultTechnoShouldBeFound("exercicesInteractifs.notEquals=" + UPDATED_EXERCICES_INTERACTIFS);
    }

    @Test
    @Transactional
    public void getAllTechnosByExercicesInteractifsIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exercicesInteractifs in DEFAULT_EXERCICES_INTERACTIFS or UPDATED_EXERCICES_INTERACTIFS
        defaultTechnoShouldBeFound("exercicesInteractifs.in=" + DEFAULT_EXERCICES_INTERACTIFS + "," + UPDATED_EXERCICES_INTERACTIFS);

        // Get all the technoList where exercicesInteractifs equals to UPDATED_EXERCICES_INTERACTIFS
        defaultTechnoShouldNotBeFound("exercicesInteractifs.in=" + UPDATED_EXERCICES_INTERACTIFS);
    }

    @Test
    @Transactional
    public void getAllTechnosByExercicesInteractifsIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exercicesInteractifs is not null
        defaultTechnoShouldBeFound("exercicesInteractifs.specified=true");

        // Get all the technoList where exercicesInteractifs is null
        defaultTechnoShouldNotBeFound("exercicesInteractifs.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByExercicesAutoCorrigesIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exercicesAutoCorriges equals to DEFAULT_EXERCICES_AUTO_CORRIGES
        defaultTechnoShouldBeFound("exercicesAutoCorriges.equals=" + DEFAULT_EXERCICES_AUTO_CORRIGES);

        // Get all the technoList where exercicesAutoCorriges equals to UPDATED_EXERCICES_AUTO_CORRIGES
        defaultTechnoShouldNotBeFound("exercicesAutoCorriges.equals=" + UPDATED_EXERCICES_AUTO_CORRIGES);
    }

    @Test
    @Transactional
    public void getAllTechnosByExercicesAutoCorrigesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exercicesAutoCorriges not equals to DEFAULT_EXERCICES_AUTO_CORRIGES
        defaultTechnoShouldNotBeFound("exercicesAutoCorriges.notEquals=" + DEFAULT_EXERCICES_AUTO_CORRIGES);

        // Get all the technoList where exercicesAutoCorriges not equals to UPDATED_EXERCICES_AUTO_CORRIGES
        defaultTechnoShouldBeFound("exercicesAutoCorriges.notEquals=" + UPDATED_EXERCICES_AUTO_CORRIGES);
    }

    @Test
    @Transactional
    public void getAllTechnosByExercicesAutoCorrigesIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exercicesAutoCorriges in DEFAULT_EXERCICES_AUTO_CORRIGES or UPDATED_EXERCICES_AUTO_CORRIGES
        defaultTechnoShouldBeFound("exercicesAutoCorriges.in=" + DEFAULT_EXERCICES_AUTO_CORRIGES + "," + UPDATED_EXERCICES_AUTO_CORRIGES);

        // Get all the technoList where exercicesAutoCorriges equals to UPDATED_EXERCICES_AUTO_CORRIGES
        defaultTechnoShouldNotBeFound("exercicesAutoCorriges.in=" + UPDATED_EXERCICES_AUTO_CORRIGES);
    }

    @Test
    @Transactional
    public void getAllTechnosByExercicesAutoCorrigesIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exercicesAutoCorriges is not null
        defaultTechnoShouldBeFound("exercicesAutoCorriges.specified=true");

        // Get all the technoList where exercicesAutoCorriges is null
        defaultTechnoShouldNotBeFound("exercicesAutoCorriges.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByExportReponsesElevesIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportReponsesEleves equals to DEFAULT_EXPORT_REPONSES_ELEVES
        defaultTechnoShouldBeFound("exportReponsesEleves.equals=" + DEFAULT_EXPORT_REPONSES_ELEVES);

        // Get all the technoList where exportReponsesEleves equals to UPDATED_EXPORT_REPONSES_ELEVES
        defaultTechnoShouldNotBeFound("exportReponsesEleves.equals=" + UPDATED_EXPORT_REPONSES_ELEVES);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportReponsesElevesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportReponsesEleves not equals to DEFAULT_EXPORT_REPONSES_ELEVES
        defaultTechnoShouldNotBeFound("exportReponsesEleves.notEquals=" + DEFAULT_EXPORT_REPONSES_ELEVES);

        // Get all the technoList where exportReponsesEleves not equals to UPDATED_EXPORT_REPONSES_ELEVES
        defaultTechnoShouldBeFound("exportReponsesEleves.notEquals=" + UPDATED_EXPORT_REPONSES_ELEVES);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportReponsesElevesIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportReponsesEleves in DEFAULT_EXPORT_REPONSES_ELEVES or UPDATED_EXPORT_REPONSES_ELEVES
        defaultTechnoShouldBeFound("exportReponsesEleves.in=" + DEFAULT_EXPORT_REPONSES_ELEVES + "," + UPDATED_EXPORT_REPONSES_ELEVES);

        // Get all the technoList where exportReponsesEleves equals to UPDATED_EXPORT_REPONSES_ELEVES
        defaultTechnoShouldNotBeFound("exportReponsesEleves.in=" + UPDATED_EXPORT_REPONSES_ELEVES);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportReponsesElevesIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportReponsesEleves is not null
        defaultTechnoShouldBeFound("exportReponsesEleves.specified=true");

        // Get all the technoList where exportReponsesEleves is null
        defaultTechnoShouldNotBeFound("exportReponsesEleves.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByImportDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where importDocument equals to DEFAULT_IMPORT_DOCUMENT
        defaultTechnoShouldBeFound("importDocument.equals=" + DEFAULT_IMPORT_DOCUMENT);

        // Get all the technoList where importDocument equals to UPDATED_IMPORT_DOCUMENT
        defaultTechnoShouldNotBeFound("importDocument.equals=" + UPDATED_IMPORT_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByImportDocumentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where importDocument not equals to DEFAULT_IMPORT_DOCUMENT
        defaultTechnoShouldNotBeFound("importDocument.notEquals=" + DEFAULT_IMPORT_DOCUMENT);

        // Get all the technoList where importDocument not equals to UPDATED_IMPORT_DOCUMENT
        defaultTechnoShouldBeFound("importDocument.notEquals=" + UPDATED_IMPORT_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByImportDocumentIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where importDocument in DEFAULT_IMPORT_DOCUMENT or UPDATED_IMPORT_DOCUMENT
        defaultTechnoShouldBeFound("importDocument.in=" + DEFAULT_IMPORT_DOCUMENT + "," + UPDATED_IMPORT_DOCUMENT);

        // Get all the technoList where importDocument equals to UPDATED_IMPORT_DOCUMENT
        defaultTechnoShouldNotBeFound("importDocument.in=" + UPDATED_IMPORT_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByImportDocumentIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where importDocument is not null
        defaultTechnoShouldBeFound("importDocument.specified=true");

        // Get all the technoList where importDocument is null
        defaultTechnoShouldNotBeFound("importDocument.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByExportDocumentIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportDocument equals to DEFAULT_EXPORT_DOCUMENT
        defaultTechnoShouldBeFound("exportDocument.equals=" + DEFAULT_EXPORT_DOCUMENT);

        // Get all the technoList where exportDocument equals to UPDATED_EXPORT_DOCUMENT
        defaultTechnoShouldNotBeFound("exportDocument.equals=" + UPDATED_EXPORT_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportDocumentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportDocument not equals to DEFAULT_EXPORT_DOCUMENT
        defaultTechnoShouldNotBeFound("exportDocument.notEquals=" + DEFAULT_EXPORT_DOCUMENT);

        // Get all the technoList where exportDocument not equals to UPDATED_EXPORT_DOCUMENT
        defaultTechnoShouldBeFound("exportDocument.notEquals=" + UPDATED_EXPORT_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportDocumentIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportDocument in DEFAULT_EXPORT_DOCUMENT or UPDATED_EXPORT_DOCUMENT
        defaultTechnoShouldBeFound("exportDocument.in=" + DEFAULT_EXPORT_DOCUMENT + "," + UPDATED_EXPORT_DOCUMENT);

        // Get all the technoList where exportDocument equals to UPDATED_EXPORT_DOCUMENT
        defaultTechnoShouldNotBeFound("exportDocument.in=" + UPDATED_EXPORT_DOCUMENT);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportDocumentIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportDocument is not null
        defaultTechnoShouldBeFound("exportDocument.specified=true");

        // Get all the technoList where exportDocument is null
        defaultTechnoShouldNotBeFound("exportDocument.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByExportSCORMIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportSCORM equals to DEFAULT_EXPORT_SCORM
        defaultTechnoShouldBeFound("exportSCORM.equals=" + DEFAULT_EXPORT_SCORM);

        // Get all the technoList where exportSCORM equals to UPDATED_EXPORT_SCORM
        defaultTechnoShouldNotBeFound("exportSCORM.equals=" + UPDATED_EXPORT_SCORM);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportSCORMIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportSCORM not equals to DEFAULT_EXPORT_SCORM
        defaultTechnoShouldNotBeFound("exportSCORM.notEquals=" + DEFAULT_EXPORT_SCORM);

        // Get all the technoList where exportSCORM not equals to UPDATED_EXPORT_SCORM
        defaultTechnoShouldBeFound("exportSCORM.notEquals=" + UPDATED_EXPORT_SCORM);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportSCORMIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportSCORM in DEFAULT_EXPORT_SCORM or UPDATED_EXPORT_SCORM
        defaultTechnoShouldBeFound("exportSCORM.in=" + DEFAULT_EXPORT_SCORM + "," + UPDATED_EXPORT_SCORM);

        // Get all the technoList where exportSCORM equals to UPDATED_EXPORT_SCORM
        defaultTechnoShouldNotBeFound("exportSCORM.in=" + UPDATED_EXPORT_SCORM);
    }

    @Test
    @Transactional
    public void getAllTechnosByExportSCORMIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where exportSCORM is not null
        defaultTechnoShouldBeFound("exportSCORM.specified=true");

        // Get all the technoList where exportSCORM is null
        defaultTechnoShouldNotBeFound("exportSCORM.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByPersonnalisationUserInterfaceIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where personnalisationUserInterface equals to DEFAULT_PERSONNALISATION_USER_INTERFACE
        defaultTechnoShouldBeFound("personnalisationUserInterface.equals=" + DEFAULT_PERSONNALISATION_USER_INTERFACE);

        // Get all the technoList where personnalisationUserInterface equals to UPDATED_PERSONNALISATION_USER_INTERFACE
        defaultTechnoShouldNotBeFound("personnalisationUserInterface.equals=" + UPDATED_PERSONNALISATION_USER_INTERFACE);
    }

    @Test
    @Transactional
    public void getAllTechnosByPersonnalisationUserInterfaceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where personnalisationUserInterface not equals to DEFAULT_PERSONNALISATION_USER_INTERFACE
        defaultTechnoShouldNotBeFound("personnalisationUserInterface.notEquals=" + DEFAULT_PERSONNALISATION_USER_INTERFACE);

        // Get all the technoList where personnalisationUserInterface not equals to UPDATED_PERSONNALISATION_USER_INTERFACE
        defaultTechnoShouldBeFound("personnalisationUserInterface.notEquals=" + UPDATED_PERSONNALISATION_USER_INTERFACE);
    }

    @Test
    @Transactional
    public void getAllTechnosByPersonnalisationUserInterfaceIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where personnalisationUserInterface in DEFAULT_PERSONNALISATION_USER_INTERFACE or UPDATED_PERSONNALISATION_USER_INTERFACE
        defaultTechnoShouldBeFound("personnalisationUserInterface.in=" + DEFAULT_PERSONNALISATION_USER_INTERFACE + "," + UPDATED_PERSONNALISATION_USER_INTERFACE);

        // Get all the technoList where personnalisationUserInterface equals to UPDATED_PERSONNALISATION_USER_INTERFACE
        defaultTechnoShouldNotBeFound("personnalisationUserInterface.in=" + UPDATED_PERSONNALISATION_USER_INTERFACE);
    }

    @Test
    @Transactional
    public void getAllTechnosByPersonnalisationUserInterfaceIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where personnalisationUserInterface is not null
        defaultTechnoShouldBeFound("personnalisationUserInterface.specified=true");

        // Get all the technoList where personnalisationUserInterface is null
        defaultTechnoShouldNotBeFound("personnalisationUserInterface.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByModifContenuEditorialIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where modifContenuEditorial equals to DEFAULT_MODIF_CONTENU_EDITORIAL
        defaultTechnoShouldBeFound("modifContenuEditorial.equals=" + DEFAULT_MODIF_CONTENU_EDITORIAL);

        // Get all the technoList where modifContenuEditorial equals to UPDATED_MODIF_CONTENU_EDITORIAL
        defaultTechnoShouldNotBeFound("modifContenuEditorial.equals=" + UPDATED_MODIF_CONTENU_EDITORIAL);
    }

    @Test
    @Transactional
    public void getAllTechnosByModifContenuEditorialIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where modifContenuEditorial not equals to DEFAULT_MODIF_CONTENU_EDITORIAL
        defaultTechnoShouldNotBeFound("modifContenuEditorial.notEquals=" + DEFAULT_MODIF_CONTENU_EDITORIAL);

        // Get all the technoList where modifContenuEditorial not equals to UPDATED_MODIF_CONTENU_EDITORIAL
        defaultTechnoShouldBeFound("modifContenuEditorial.notEquals=" + UPDATED_MODIF_CONTENU_EDITORIAL);
    }

    @Test
    @Transactional
    public void getAllTechnosByModifContenuEditorialIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where modifContenuEditorial in DEFAULT_MODIF_CONTENU_EDITORIAL or UPDATED_MODIF_CONTENU_EDITORIAL
        defaultTechnoShouldBeFound("modifContenuEditorial.in=" + DEFAULT_MODIF_CONTENU_EDITORIAL + "," + UPDATED_MODIF_CONTENU_EDITORIAL);

        // Get all the technoList where modifContenuEditorial equals to UPDATED_MODIF_CONTENU_EDITORIAL
        defaultTechnoShouldNotBeFound("modifContenuEditorial.in=" + UPDATED_MODIF_CONTENU_EDITORIAL);
    }

    @Test
    @Transactional
    public void getAllTechnosByModifContenuEditorialIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where modifContenuEditorial is not null
        defaultTechnoShouldBeFound("modifContenuEditorial.specified=true");

        // Get all the technoList where modifContenuEditorial is null
        defaultTechnoShouldNotBeFound("modifContenuEditorial.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByDispositifDYSIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where dispositifDYS equals to DEFAULT_DISPOSITIF_DYS
        defaultTechnoShouldBeFound("dispositifDYS.equals=" + DEFAULT_DISPOSITIF_DYS);

        // Get all the technoList where dispositifDYS equals to UPDATED_DISPOSITIF_DYS
        defaultTechnoShouldNotBeFound("dispositifDYS.equals=" + UPDATED_DISPOSITIF_DYS);
    }

    @Test
    @Transactional
    public void getAllTechnosByDispositifDYSIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where dispositifDYS not equals to DEFAULT_DISPOSITIF_DYS
        defaultTechnoShouldNotBeFound("dispositifDYS.notEquals=" + DEFAULT_DISPOSITIF_DYS);

        // Get all the technoList where dispositifDYS not equals to UPDATED_DISPOSITIF_DYS
        defaultTechnoShouldBeFound("dispositifDYS.notEquals=" + UPDATED_DISPOSITIF_DYS);
    }

    @Test
    @Transactional
    public void getAllTechnosByDispositifDYSIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where dispositifDYS in DEFAULT_DISPOSITIF_DYS or UPDATED_DISPOSITIF_DYS
        defaultTechnoShouldBeFound("dispositifDYS.in=" + DEFAULT_DISPOSITIF_DYS + "," + UPDATED_DISPOSITIF_DYS);

        // Get all the technoList where dispositifDYS equals to UPDATED_DISPOSITIF_DYS
        defaultTechnoShouldNotBeFound("dispositifDYS.in=" + UPDATED_DISPOSITIF_DYS);
    }

    @Test
    @Transactional
    public void getAllTechnosByDispositifDYSIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where dispositifDYS is not null
        defaultTechnoShouldBeFound("dispositifDYS.specified=true");

        // Get all the technoList where dispositifDYS is null
        defaultTechnoShouldNotBeFound("dispositifDYS.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByNbMaxiInstallIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxiInstall equals to DEFAULT_NB_MAXI_INSTALL
        defaultTechnoShouldBeFound("nbMaxiInstall.equals=" + DEFAULT_NB_MAXI_INSTALL);

        // Get all the technoList where nbMaxiInstall equals to UPDATED_NB_MAXI_INSTALL
        defaultTechnoShouldNotBeFound("nbMaxiInstall.equals=" + UPDATED_NB_MAXI_INSTALL);
    }

    @Test
    @Transactional
    public void getAllTechnosByNbMaxiInstallIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxiInstall not equals to DEFAULT_NB_MAXI_INSTALL
        defaultTechnoShouldNotBeFound("nbMaxiInstall.notEquals=" + DEFAULT_NB_MAXI_INSTALL);

        // Get all the technoList where nbMaxiInstall not equals to UPDATED_NB_MAXI_INSTALL
        defaultTechnoShouldBeFound("nbMaxiInstall.notEquals=" + UPDATED_NB_MAXI_INSTALL);
    }

    @Test
    @Transactional
    public void getAllTechnosByNbMaxiInstallIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxiInstall in DEFAULT_NB_MAXI_INSTALL or UPDATED_NB_MAXI_INSTALL
        defaultTechnoShouldBeFound("nbMaxiInstall.in=" + DEFAULT_NB_MAXI_INSTALL + "," + UPDATED_NB_MAXI_INSTALL);

        // Get all the technoList where nbMaxiInstall equals to UPDATED_NB_MAXI_INSTALL
        defaultTechnoShouldNotBeFound("nbMaxiInstall.in=" + UPDATED_NB_MAXI_INSTALL);
    }

    @Test
    @Transactional
    public void getAllTechnosByNbMaxiInstallIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxiInstall is not null
        defaultTechnoShouldBeFound("nbMaxiInstall.specified=true");

        // Get all the technoList where nbMaxiInstall is null
        defaultTechnoShouldNotBeFound("nbMaxiInstall.specified=false");
    }
                @Test
    @Transactional
    public void getAllTechnosByNbMaxiInstallContainsSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxiInstall contains DEFAULT_NB_MAXI_INSTALL
        defaultTechnoShouldBeFound("nbMaxiInstall.contains=" + DEFAULT_NB_MAXI_INSTALL);

        // Get all the technoList where nbMaxiInstall contains UPDATED_NB_MAXI_INSTALL
        defaultTechnoShouldNotBeFound("nbMaxiInstall.contains=" + UPDATED_NB_MAXI_INSTALL);
    }

    @Test
    @Transactional
    public void getAllTechnosByNbMaxiInstallNotContainsSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxiInstall does not contain DEFAULT_NB_MAXI_INSTALL
        defaultTechnoShouldNotBeFound("nbMaxiInstall.doesNotContain=" + DEFAULT_NB_MAXI_INSTALL);

        // Get all the technoList where nbMaxiInstall does not contain UPDATED_NB_MAXI_INSTALL
        defaultTechnoShouldBeFound("nbMaxiInstall.doesNotContain=" + UPDATED_NB_MAXI_INSTALL);
    }


    @Test
    @Transactional
    public void getAllTechnosByNbMaxSimultConnexionsIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxSimultConnexions equals to DEFAULT_NB_MAX_SIMULT_CONNEXIONS
        defaultTechnoShouldBeFound("nbMaxSimultConnexions.equals=" + DEFAULT_NB_MAX_SIMULT_CONNEXIONS);

        // Get all the technoList where nbMaxSimultConnexions equals to UPDATED_NB_MAX_SIMULT_CONNEXIONS
        defaultTechnoShouldNotBeFound("nbMaxSimultConnexions.equals=" + UPDATED_NB_MAX_SIMULT_CONNEXIONS);
    }

    @Test
    @Transactional
    public void getAllTechnosByNbMaxSimultConnexionsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxSimultConnexions not equals to DEFAULT_NB_MAX_SIMULT_CONNEXIONS
        defaultTechnoShouldNotBeFound("nbMaxSimultConnexions.notEquals=" + DEFAULT_NB_MAX_SIMULT_CONNEXIONS);

        // Get all the technoList where nbMaxSimultConnexions not equals to UPDATED_NB_MAX_SIMULT_CONNEXIONS
        defaultTechnoShouldBeFound("nbMaxSimultConnexions.notEquals=" + UPDATED_NB_MAX_SIMULT_CONNEXIONS);
    }

    @Test
    @Transactional
    public void getAllTechnosByNbMaxSimultConnexionsIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxSimultConnexions in DEFAULT_NB_MAX_SIMULT_CONNEXIONS or UPDATED_NB_MAX_SIMULT_CONNEXIONS
        defaultTechnoShouldBeFound("nbMaxSimultConnexions.in=" + DEFAULT_NB_MAX_SIMULT_CONNEXIONS + "," + UPDATED_NB_MAX_SIMULT_CONNEXIONS);

        // Get all the technoList where nbMaxSimultConnexions equals to UPDATED_NB_MAX_SIMULT_CONNEXIONS
        defaultTechnoShouldNotBeFound("nbMaxSimultConnexions.in=" + UPDATED_NB_MAX_SIMULT_CONNEXIONS);
    }

    @Test
    @Transactional
    public void getAllTechnosByNbMaxSimultConnexionsIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxSimultConnexions is not null
        defaultTechnoShouldBeFound("nbMaxSimultConnexions.specified=true");

        // Get all the technoList where nbMaxSimultConnexions is null
        defaultTechnoShouldNotBeFound("nbMaxSimultConnexions.specified=false");
    }
                @Test
    @Transactional
    public void getAllTechnosByNbMaxSimultConnexionsContainsSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxSimultConnexions contains DEFAULT_NB_MAX_SIMULT_CONNEXIONS
        defaultTechnoShouldBeFound("nbMaxSimultConnexions.contains=" + DEFAULT_NB_MAX_SIMULT_CONNEXIONS);

        // Get all the technoList where nbMaxSimultConnexions contains UPDATED_NB_MAX_SIMULT_CONNEXIONS
        defaultTechnoShouldNotBeFound("nbMaxSimultConnexions.contains=" + UPDATED_NB_MAX_SIMULT_CONNEXIONS);
    }

    @Test
    @Transactional
    public void getAllTechnosByNbMaxSimultConnexionsNotContainsSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where nbMaxSimultConnexions does not contain DEFAULT_NB_MAX_SIMULT_CONNEXIONS
        defaultTechnoShouldNotBeFound("nbMaxSimultConnexions.doesNotContain=" + DEFAULT_NB_MAX_SIMULT_CONNEXIONS);

        // Get all the technoList where nbMaxSimultConnexions does not contain UPDATED_NB_MAX_SIMULT_CONNEXIONS
        defaultTechnoShouldBeFound("nbMaxSimultConnexions.doesNotContain=" + UPDATED_NB_MAX_SIMULT_CONNEXIONS);
    }


    @Test
    @Transactional
    public void getAllTechnosByMessagerieIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where messagerie equals to DEFAULT_MESSAGERIE
        defaultTechnoShouldBeFound("messagerie.equals=" + DEFAULT_MESSAGERIE);

        // Get all the technoList where messagerie equals to UPDATED_MESSAGERIE
        defaultTechnoShouldNotBeFound("messagerie.equals=" + UPDATED_MESSAGERIE);
    }

    @Test
    @Transactional
    public void getAllTechnosByMessagerieIsNotEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where messagerie not equals to DEFAULT_MESSAGERIE
        defaultTechnoShouldNotBeFound("messagerie.notEquals=" + DEFAULT_MESSAGERIE);

        // Get all the technoList where messagerie not equals to UPDATED_MESSAGERIE
        defaultTechnoShouldBeFound("messagerie.notEquals=" + UPDATED_MESSAGERIE);
    }

    @Test
    @Transactional
    public void getAllTechnosByMessagerieIsInShouldWork() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where messagerie in DEFAULT_MESSAGERIE or UPDATED_MESSAGERIE
        defaultTechnoShouldBeFound("messagerie.in=" + DEFAULT_MESSAGERIE + "," + UPDATED_MESSAGERIE);

        // Get all the technoList where messagerie equals to UPDATED_MESSAGERIE
        defaultTechnoShouldNotBeFound("messagerie.in=" + UPDATED_MESSAGERIE);
    }

    @Test
    @Transactional
    public void getAllTechnosByMessagerieIsNullOrNotNull() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        // Get all the technoList where messagerie is not null
        defaultTechnoShouldBeFound("messagerie.specified=true");

        // Get all the technoList where messagerie is null
        defaultTechnoShouldNotBeFound("messagerie.specified=false");
    }

    @Test
    @Transactional
    public void getAllTechnosByArticleNumeriqueIsEqualToSomething() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);
        ArticleNumerique articleNumerique = ArticleNumeriqueResourceIT.createEntity(em);
        em.persist(articleNumerique);
        em.flush();
        techno.setArticleNumerique(articleNumerique);
        technoRepository.saveAndFlush(techno);
        Long articleNumeriqueId = articleNumerique.getId();

        // Get all the technoList where articleNumerique equals to articleNumeriqueId
        defaultTechnoShouldBeFound("articleNumeriqueId.equals=" + articleNumeriqueId);

        // Get all the technoList where articleNumerique equals to articleNumeriqueId + 1
        defaultTechnoShouldNotBeFound("articleNumeriqueId.equals=" + (articleNumeriqueId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTechnoShouldBeFound(String filter) throws Exception {
        restTechnoMockMvc.perform(get("/api/technos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(techno.getId().intValue())))
            .andExpect(jsonPath("$.[*].technologie").value(hasItem(DEFAULT_TECHNOLOGIE.toString())))
            .andExpect(jsonPath("$.[*].versionReader").value(hasItem(DEFAULT_VERSION_READER)))
            .andExpect(jsonPath("$.[*].availableHorsENT").value(hasItem(DEFAULT_AVAILABLE_HORS_ENT.booleanValue())))
            .andExpect(jsonPath("$.[*].availableViaENT").value(hasItem(DEFAULT_AVAILABLE_VIA_ENT.booleanValue())))
            .andExpect(jsonPath("$.[*].availableViaGAR").value(hasItem(DEFAULT_AVAILABLE_VIA_GAR.booleanValue())))
            .andExpect(jsonPath("$.[*].typeLicenceGAR").value(hasItem(DEFAULT_TYPE_LICENCE_GAR.toString())))
            .andExpect(jsonPath("$.[*].canUseOffline").value(hasItem(DEFAULT_CAN_USE_OFFLINE.booleanValue())))
            .andExpect(jsonPath("$.[*].oneClic").value(hasItem(DEFAULT_ONE_CLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].exportCleUSB").value(hasItem(DEFAULT_EXPORT_CLE_USB.booleanValue())))
            .andExpect(jsonPath("$.[*].deploiementMasse").value(hasItem(DEFAULT_DEPLOIEMENT_MASSE.booleanValue())))
            .andExpect(jsonPath("$.[*].configurationMiniOS").value(hasItem(DEFAULT_CONFIGURATION_MINI_OS)))
            .andExpect(jsonPath("$.[*].needFlash").value(hasItem(DEFAULT_NEED_FLASH.booleanValue())))
            .andExpect(jsonPath("$.[*].annotations").value(hasItem(DEFAULT_ANNOTATIONS.booleanValue())))
            .andExpect(jsonPath("$.[*].creationCours").value(hasItem(DEFAULT_CREATION_COURS.booleanValue())))
            .andExpect(jsonPath("$.[*].webAdaptatif").value(hasItem(DEFAULT_WEB_ADAPTATIF.booleanValue())))
            .andExpect(jsonPath("$.[*].marquePage").value(hasItem(DEFAULT_MARQUE_PAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].captureImage").value(hasItem(DEFAULT_CAPTURE_IMAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].zoom").value(hasItem(DEFAULT_ZOOM.booleanValue())))
            .andExpect(jsonPath("$.[*].fonctionsRecherche").value(hasItem(DEFAULT_FONCTIONS_RECHERCHE.booleanValue())))
            .andExpect(jsonPath("$.[*].corrigesPourEnseignants").value(hasItem(DEFAULT_CORRIGES_POUR_ENSEIGNANTS.booleanValue())))
            .andExpect(jsonPath("$.[*].assignationTachesEleves").value(hasItem(DEFAULT_ASSIGNATION_TACHES_ELEVES.booleanValue())))
            .andExpect(jsonPath("$.[*].partageContenuEleves").value(hasItem(DEFAULT_PARTAGE_CONTENU_ELEVES.booleanValue())))
            .andExpect(jsonPath("$.[*].exercicesInteractifs").value(hasItem(DEFAULT_EXERCICES_INTERACTIFS.booleanValue())))
            .andExpect(jsonPath("$.[*].exercicesAutoCorriges").value(hasItem(DEFAULT_EXERCICES_AUTO_CORRIGES.booleanValue())))
            .andExpect(jsonPath("$.[*].exportReponsesEleves").value(hasItem(DEFAULT_EXPORT_REPONSES_ELEVES.booleanValue())))
            .andExpect(jsonPath("$.[*].importDocument").value(hasItem(DEFAULT_IMPORT_DOCUMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].exportDocument").value(hasItem(DEFAULT_EXPORT_DOCUMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].exportSCORM").value(hasItem(DEFAULT_EXPORT_SCORM.booleanValue())))
            .andExpect(jsonPath("$.[*].personnalisationUserInterface").value(hasItem(DEFAULT_PERSONNALISATION_USER_INTERFACE.booleanValue())))
            .andExpect(jsonPath("$.[*].modifContenuEditorial").value(hasItem(DEFAULT_MODIF_CONTENU_EDITORIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].dispositifDYS").value(hasItem(DEFAULT_DISPOSITIF_DYS.booleanValue())))
            .andExpect(jsonPath("$.[*].nbMaxiInstall").value(hasItem(DEFAULT_NB_MAXI_INSTALL)))
            .andExpect(jsonPath("$.[*].nbMaxSimultConnexions").value(hasItem(DEFAULT_NB_MAX_SIMULT_CONNEXIONS)))
            .andExpect(jsonPath("$.[*].messagerie").value(hasItem(DEFAULT_MESSAGERIE.booleanValue())));

        // Check, that the count call also returns 1
        restTechnoMockMvc.perform(get("/api/technos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTechnoShouldNotBeFound(String filter) throws Exception {
        restTechnoMockMvc.perform(get("/api/technos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTechnoMockMvc.perform(get("/api/technos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTechno() throws Exception {
        // Get the techno
        restTechnoMockMvc.perform(get("/api/technos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechno() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        int databaseSizeBeforeUpdate = technoRepository.findAll().size();

        // Update the techno
        Techno updatedTechno = technoRepository.findById(techno.getId()).get();
        // Disconnect from session so that the updates on updatedTechno are not directly saved in db
        em.detach(updatedTechno);
        updatedTechno
            .technologie(UPDATED_TECHNOLOGIE)
            .versionReader(UPDATED_VERSION_READER)
            .availableHorsENT(UPDATED_AVAILABLE_HORS_ENT)
            .availableViaENT(UPDATED_AVAILABLE_VIA_ENT)
            .availableViaGAR(UPDATED_AVAILABLE_VIA_GAR)
            .typeLicenceGAR(UPDATED_TYPE_LICENCE_GAR)
            .canUseOffline(UPDATED_CAN_USE_OFFLINE)
            .oneClic(UPDATED_ONE_CLIC)
            .exportCleUSB(UPDATED_EXPORT_CLE_USB)
            .deploiementMasse(UPDATED_DEPLOIEMENT_MASSE)
            .configurationMiniOS(UPDATED_CONFIGURATION_MINI_OS)
            .needFlash(UPDATED_NEED_FLASH)
            .annotations(UPDATED_ANNOTATIONS)
            .creationCours(UPDATED_CREATION_COURS)
            .webAdaptatif(UPDATED_WEB_ADAPTATIF)
            .marquePage(UPDATED_MARQUE_PAGE)
            .captureImage(UPDATED_CAPTURE_IMAGE)
            .zoom(UPDATED_ZOOM)
            .fonctionsRecherche(UPDATED_FONCTIONS_RECHERCHE)
            .corrigesPourEnseignants(UPDATED_CORRIGES_POUR_ENSEIGNANTS)
            .assignationTachesEleves(UPDATED_ASSIGNATION_TACHES_ELEVES)
            .partageContenuEleves(UPDATED_PARTAGE_CONTENU_ELEVES)
            .exercicesInteractifs(UPDATED_EXERCICES_INTERACTIFS)
            .exercicesAutoCorriges(UPDATED_EXERCICES_AUTO_CORRIGES)
            .exportReponsesEleves(UPDATED_EXPORT_REPONSES_ELEVES)
            .importDocument(UPDATED_IMPORT_DOCUMENT)
            .exportDocument(UPDATED_EXPORT_DOCUMENT)
            .exportSCORM(UPDATED_EXPORT_SCORM)
            .personnalisationUserInterface(UPDATED_PERSONNALISATION_USER_INTERFACE)
            .modifContenuEditorial(UPDATED_MODIF_CONTENU_EDITORIAL)
            .dispositifDYS(UPDATED_DISPOSITIF_DYS)
            .nbMaxiInstall(UPDATED_NB_MAXI_INSTALL)
            .nbMaxSimultConnexions(UPDATED_NB_MAX_SIMULT_CONNEXIONS)
            .messagerie(UPDATED_MESSAGERIE);
        TechnoDTO technoDTO = technoMapper.toDto(updatedTechno);

        restTechnoMockMvc.perform(put("/api/technos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technoDTO)))
            .andExpect(status().isOk());

        // Validate the Techno in the database
        List<Techno> technoList = technoRepository.findAll();
        assertThat(technoList).hasSize(databaseSizeBeforeUpdate);
        Techno testTechno = technoList.get(technoList.size() - 1);
        assertThat(testTechno.getTechnologie()).isEqualTo(UPDATED_TECHNOLOGIE);
        assertThat(testTechno.getVersionReader()).isEqualTo(UPDATED_VERSION_READER);
        assertThat(testTechno.isAvailableHorsENT()).isEqualTo(UPDATED_AVAILABLE_HORS_ENT);
        assertThat(testTechno.isAvailableViaENT()).isEqualTo(UPDATED_AVAILABLE_VIA_ENT);
        assertThat(testTechno.isAvailableViaGAR()).isEqualTo(UPDATED_AVAILABLE_VIA_GAR);
        assertThat(testTechno.getTypeLicenceGAR()).isEqualTo(UPDATED_TYPE_LICENCE_GAR);
        assertThat(testTechno.isCanUseOffline()).isEqualTo(UPDATED_CAN_USE_OFFLINE);
        assertThat(testTechno.isOneClic()).isEqualTo(UPDATED_ONE_CLIC);
        assertThat(testTechno.isExportCleUSB()).isEqualTo(UPDATED_EXPORT_CLE_USB);
        assertThat(testTechno.isDeploiementMasse()).isEqualTo(UPDATED_DEPLOIEMENT_MASSE);
        assertThat(testTechno.getConfigurationMiniOS()).isEqualTo(UPDATED_CONFIGURATION_MINI_OS);
        assertThat(testTechno.isNeedFlash()).isEqualTo(UPDATED_NEED_FLASH);
        assertThat(testTechno.isAnnotations()).isEqualTo(UPDATED_ANNOTATIONS);
        assertThat(testTechno.isCreationCours()).isEqualTo(UPDATED_CREATION_COURS);
        assertThat(testTechno.isWebAdaptatif()).isEqualTo(UPDATED_WEB_ADAPTATIF);
        assertThat(testTechno.isMarquePage()).isEqualTo(UPDATED_MARQUE_PAGE);
        assertThat(testTechno.isCaptureImage()).isEqualTo(UPDATED_CAPTURE_IMAGE);
        assertThat(testTechno.isZoom()).isEqualTo(UPDATED_ZOOM);
        assertThat(testTechno.isFonctionsRecherche()).isEqualTo(UPDATED_FONCTIONS_RECHERCHE);
        assertThat(testTechno.isCorrigesPourEnseignants()).isEqualTo(UPDATED_CORRIGES_POUR_ENSEIGNANTS);
        assertThat(testTechno.isAssignationTachesEleves()).isEqualTo(UPDATED_ASSIGNATION_TACHES_ELEVES);
        assertThat(testTechno.isPartageContenuEleves()).isEqualTo(UPDATED_PARTAGE_CONTENU_ELEVES);
        assertThat(testTechno.isExercicesInteractifs()).isEqualTo(UPDATED_EXERCICES_INTERACTIFS);
        assertThat(testTechno.isExercicesAutoCorriges()).isEqualTo(UPDATED_EXERCICES_AUTO_CORRIGES);
        assertThat(testTechno.isExportReponsesEleves()).isEqualTo(UPDATED_EXPORT_REPONSES_ELEVES);
        assertThat(testTechno.isImportDocument()).isEqualTo(UPDATED_IMPORT_DOCUMENT);
        assertThat(testTechno.isExportDocument()).isEqualTo(UPDATED_EXPORT_DOCUMENT);
        assertThat(testTechno.isExportSCORM()).isEqualTo(UPDATED_EXPORT_SCORM);
        assertThat(testTechno.isPersonnalisationUserInterface()).isEqualTo(UPDATED_PERSONNALISATION_USER_INTERFACE);
        assertThat(testTechno.isModifContenuEditorial()).isEqualTo(UPDATED_MODIF_CONTENU_EDITORIAL);
        assertThat(testTechno.isDispositifDYS()).isEqualTo(UPDATED_DISPOSITIF_DYS);
        assertThat(testTechno.getNbMaxiInstall()).isEqualTo(UPDATED_NB_MAXI_INSTALL);
        assertThat(testTechno.getNbMaxSimultConnexions()).isEqualTo(UPDATED_NB_MAX_SIMULT_CONNEXIONS);
        assertThat(testTechno.isMessagerie()).isEqualTo(UPDATED_MESSAGERIE);

        // Validate the Techno in Elasticsearch
        verify(mockTechnoSearchRepository, times(1)).save(testTechno);
    }

    @Test
    @Transactional
    public void updateNonExistingTechno() throws Exception {
        int databaseSizeBeforeUpdate = technoRepository.findAll().size();

        // Create the Techno
        TechnoDTO technoDTO = technoMapper.toDto(techno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechnoMockMvc.perform(put("/api/technos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(technoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Techno in the database
        List<Techno> technoList = technoRepository.findAll();
        assertThat(technoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Techno in Elasticsearch
        verify(mockTechnoSearchRepository, times(0)).save(techno);
    }

    @Test
    @Transactional
    public void deleteTechno() throws Exception {
        // Initialize the database
        technoRepository.saveAndFlush(techno);

        int databaseSizeBeforeDelete = technoRepository.findAll().size();

        // Delete the techno
        restTechnoMockMvc.perform(delete("/api/technos/{id}", techno.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Techno> technoList = technoRepository.findAll();
        assertThat(technoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Techno in Elasticsearch
        verify(mockTechnoSearchRepository, times(1)).deleteById(techno.getId());
    }

    @Test
    @Transactional
    public void searchTechno() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        technoRepository.saveAndFlush(techno);
        when(mockTechnoSearchRepository.search(queryStringQuery("id:" + techno.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(techno), PageRequest.of(0, 1), 1));

        // Search the techno
        restTechnoMockMvc.perform(get("/api/_search/technos?query=id:" + techno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(techno.getId().intValue())))
            .andExpect(jsonPath("$.[*].technologie").value(hasItem(DEFAULT_TECHNOLOGIE.toString())))
            .andExpect(jsonPath("$.[*].versionReader").value(hasItem(DEFAULT_VERSION_READER)))
            .andExpect(jsonPath("$.[*].availableHorsENT").value(hasItem(DEFAULT_AVAILABLE_HORS_ENT.booleanValue())))
            .andExpect(jsonPath("$.[*].availableViaENT").value(hasItem(DEFAULT_AVAILABLE_VIA_ENT.booleanValue())))
            .andExpect(jsonPath("$.[*].availableViaGAR").value(hasItem(DEFAULT_AVAILABLE_VIA_GAR.booleanValue())))
            .andExpect(jsonPath("$.[*].typeLicenceGAR").value(hasItem(DEFAULT_TYPE_LICENCE_GAR.toString())))
            .andExpect(jsonPath("$.[*].canUseOffline").value(hasItem(DEFAULT_CAN_USE_OFFLINE.booleanValue())))
            .andExpect(jsonPath("$.[*].oneClic").value(hasItem(DEFAULT_ONE_CLIC.booleanValue())))
            .andExpect(jsonPath("$.[*].exportCleUSB").value(hasItem(DEFAULT_EXPORT_CLE_USB.booleanValue())))
            .andExpect(jsonPath("$.[*].deploiementMasse").value(hasItem(DEFAULT_DEPLOIEMENT_MASSE.booleanValue())))
            .andExpect(jsonPath("$.[*].configurationMiniOS").value(hasItem(DEFAULT_CONFIGURATION_MINI_OS)))
            .andExpect(jsonPath("$.[*].needFlash").value(hasItem(DEFAULT_NEED_FLASH.booleanValue())))
            .andExpect(jsonPath("$.[*].annotations").value(hasItem(DEFAULT_ANNOTATIONS.booleanValue())))
            .andExpect(jsonPath("$.[*].creationCours").value(hasItem(DEFAULT_CREATION_COURS.booleanValue())))
            .andExpect(jsonPath("$.[*].webAdaptatif").value(hasItem(DEFAULT_WEB_ADAPTATIF.booleanValue())))
            .andExpect(jsonPath("$.[*].marquePage").value(hasItem(DEFAULT_MARQUE_PAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].captureImage").value(hasItem(DEFAULT_CAPTURE_IMAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].zoom").value(hasItem(DEFAULT_ZOOM.booleanValue())))
            .andExpect(jsonPath("$.[*].fonctionsRecherche").value(hasItem(DEFAULT_FONCTIONS_RECHERCHE.booleanValue())))
            .andExpect(jsonPath("$.[*].corrigesPourEnseignants").value(hasItem(DEFAULT_CORRIGES_POUR_ENSEIGNANTS.booleanValue())))
            .andExpect(jsonPath("$.[*].assignationTachesEleves").value(hasItem(DEFAULT_ASSIGNATION_TACHES_ELEVES.booleanValue())))
            .andExpect(jsonPath("$.[*].partageContenuEleves").value(hasItem(DEFAULT_PARTAGE_CONTENU_ELEVES.booleanValue())))
            .andExpect(jsonPath("$.[*].exercicesInteractifs").value(hasItem(DEFAULT_EXERCICES_INTERACTIFS.booleanValue())))
            .andExpect(jsonPath("$.[*].exercicesAutoCorriges").value(hasItem(DEFAULT_EXERCICES_AUTO_CORRIGES.booleanValue())))
            .andExpect(jsonPath("$.[*].exportReponsesEleves").value(hasItem(DEFAULT_EXPORT_REPONSES_ELEVES.booleanValue())))
            .andExpect(jsonPath("$.[*].importDocument").value(hasItem(DEFAULT_IMPORT_DOCUMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].exportDocument").value(hasItem(DEFAULT_EXPORT_DOCUMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].exportSCORM").value(hasItem(DEFAULT_EXPORT_SCORM.booleanValue())))
            .andExpect(jsonPath("$.[*].personnalisationUserInterface").value(hasItem(DEFAULT_PERSONNALISATION_USER_INTERFACE.booleanValue())))
            .andExpect(jsonPath("$.[*].modifContenuEditorial").value(hasItem(DEFAULT_MODIF_CONTENU_EDITORIAL.booleanValue())))
            .andExpect(jsonPath("$.[*].dispositifDYS").value(hasItem(DEFAULT_DISPOSITIF_DYS.booleanValue())))
            .andExpect(jsonPath("$.[*].nbMaxiInstall").value(hasItem(DEFAULT_NB_MAXI_INSTALL)))
            .andExpect(jsonPath("$.[*].nbMaxSimultConnexions").value(hasItem(DEFAULT_NB_MAX_SIMULT_CONNEXIONS)))
            .andExpect(jsonPath("$.[*].messagerie").value(hasItem(DEFAULT_MESSAGERIE.booleanValue())));
    }
}
