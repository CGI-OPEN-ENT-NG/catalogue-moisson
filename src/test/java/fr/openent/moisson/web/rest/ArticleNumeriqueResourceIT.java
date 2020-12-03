package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.ArticleNumerique;
import fr.openent.moisson.domain.Discipline;
import fr.openent.moisson.domain.Niveau;
import fr.openent.moisson.domain.Offre;
import fr.openent.moisson.domain.Techno;
import fr.openent.moisson.domain.Disponibilite;
import fr.openent.moisson.repository.ArticleNumeriqueRepository;
import fr.openent.moisson.repository.search.ArticleNumeriqueSearchRepository;
import fr.openent.moisson.service.ArticleNumeriqueService;
import fr.openent.moisson.service.dto.ArticleNumeriqueDTO;
import fr.openent.moisson.service.mapper.ArticleNumeriqueMapper;
import fr.openent.moisson.service.dto.ArticleNumeriqueCriteria;
import fr.openent.moisson.service.ArticleNumeriqueQueryService;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.openent.moisson.domain.enumeration.TypeArticle;
import fr.openent.moisson.domain.enumeration.PublicCible;
/**
 * Integration tests for the {@link ArticleNumeriqueResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArticleNumeriqueResourceIT {

    private static final String DEFAULT_EAN = "AAAAAAAAAAAAA";
    private static final String UPDATED_EAN = "BBBBBBBBBBBBB";

    private static final String DEFAULT_ARK = "AAAAAAAAAA";
    private static final String UPDATED_ARK = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_EDITEUR = "AAAAAAAAAA";
    private static final String UPDATED_EDITEUR = "BBBBBBBBBB";

    private static final String DEFAULT_AUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_AUTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_COLLECTION = "AAAAAAAAAA";
    private static final String UPDATED_COLLECTION = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRIBUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_DISTRIBUTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_URL_COUVERTURE = "AAAAAAAAAA";
    private static final String UPDATED_URL_COUVERTURE = "BBBBBBBBBB";

    private static final String DEFAULT_URL_DEMO = "AAAAAAAAAA";
    private static final String UPDATED_URL_DEMO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_PARUTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_PARUTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_COMPATIBLE_GAR = false;
    private static final Boolean UPDATED_COMPATIBLE_GAR = true;

    private static final Boolean DEFAULT_ACCESSIBLE_ENT = false;
    private static final Boolean UPDATED_ACCESSIBLE_ENT = true;

    private static final String DEFAULT_EAN_PAPIER = "AAAAAAAAAAAAA";
    private static final String UPDATED_EAN_PAPIER = "BBBBBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final TypeArticle DEFAULT_TYPE = TypeArticle.NUMERIQUE;
    private static final TypeArticle UPDATED_TYPE = TypeArticle.PAPIER;

    private static final PublicCible DEFAULT_PUBLIC_CIBLE = PublicCible.ELEVE;
    private static final PublicCible UPDATED_PUBLIC_CIBLE = PublicCible.ENSEIGNANT;

    @Autowired
    private ArticleNumeriqueRepository articleNumeriqueRepository;

    @Autowired
    private ArticleNumeriqueMapper articleNumeriqueMapper;

    @Autowired
    private ArticleNumeriqueService articleNumeriqueService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.ArticleNumeriqueSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArticleNumeriqueSearchRepository mockArticleNumeriqueSearchRepository;

    @Autowired
    private ArticleNumeriqueQueryService articleNumeriqueQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticleNumeriqueMockMvc;

    private ArticleNumerique articleNumerique;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleNumerique createEntity(EntityManager em) {
        ArticleNumerique articleNumerique = new ArticleNumerique()
            .ean(DEFAULT_EAN)
            .ark(DEFAULT_ARK)
            .titre(DEFAULT_TITRE)
            .editeur(DEFAULT_EDITEUR)
            .auteur(DEFAULT_AUTEUR)
            .collection(DEFAULT_COLLECTION)
            .distributeur(DEFAULT_DISTRIBUTEUR)
            .urlCouverture(DEFAULT_URL_COUVERTURE)
            .urlDemo(DEFAULT_URL_DEMO)
            .dateParution(DEFAULT_DATE_PARUTION)
            .compatibleGAR(DEFAULT_COMPATIBLE_GAR)
            .accessibleENT(DEFAULT_ACCESSIBLE_ENT)
            .eanPapier(DEFAULT_EAN_PAPIER)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .publicCible(DEFAULT_PUBLIC_CIBLE);
        // Add required entity
        Disponibilite disponibilite;
        if (TestUtil.findAll(em, Disponibilite.class).isEmpty()) {
            disponibilite = DisponibiliteResourceIT.createEntity(em);
            em.persist(disponibilite);
            em.flush();
        } else {
            disponibilite = TestUtil.findAll(em, Disponibilite.class).get(0);
        }
        articleNumerique.setDisponibilite(disponibilite);
        return articleNumerique;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticleNumerique createUpdatedEntity(EntityManager em) {
        ArticleNumerique articleNumerique = new ArticleNumerique()
            .ean(UPDATED_EAN)
            .ark(UPDATED_ARK)
            .titre(UPDATED_TITRE)
            .editeur(UPDATED_EDITEUR)
            .auteur(UPDATED_AUTEUR)
            .collection(UPDATED_COLLECTION)
            .distributeur(UPDATED_DISTRIBUTEUR)
            .urlCouverture(UPDATED_URL_COUVERTURE)
            .urlDemo(UPDATED_URL_DEMO)
            .dateParution(UPDATED_DATE_PARUTION)
            .compatibleGAR(UPDATED_COMPATIBLE_GAR)
            .accessibleENT(UPDATED_ACCESSIBLE_ENT)
            .eanPapier(UPDATED_EAN_PAPIER)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .publicCible(UPDATED_PUBLIC_CIBLE);
        // Add required entity
        Disponibilite disponibilite;
        if (TestUtil.findAll(em, Disponibilite.class).isEmpty()) {
            disponibilite = DisponibiliteResourceIT.createUpdatedEntity(em);
            em.persist(disponibilite);
            em.flush();
        } else {
            disponibilite = TestUtil.findAll(em, Disponibilite.class).get(0);
        }
        articleNumerique.setDisponibilite(disponibilite);
        return articleNumerique;
    }

    @BeforeEach
    public void initTest() {
        articleNumerique = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticleNumerique() throws Exception {
        int databaseSizeBeforeCreate = articleNumeriqueRepository.findAll().size();
        // Create the ArticleNumerique
        ArticleNumeriqueDTO articleNumeriqueDTO = articleNumeriqueMapper.toDto(articleNumerique);
        restArticleNumeriqueMockMvc.perform(post("/api/article-numeriques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleNumeriqueDTO)))
            .andExpect(status().isCreated());

        // Validate the ArticleNumerique in the database
        List<ArticleNumerique> articleNumeriqueList = articleNumeriqueRepository.findAll();
        assertThat(articleNumeriqueList).hasSize(databaseSizeBeforeCreate + 1);
        ArticleNumerique testArticleNumerique = articleNumeriqueList.get(articleNumeriqueList.size() - 1);
        assertThat(testArticleNumerique.getEan()).isEqualTo(DEFAULT_EAN);
        assertThat(testArticleNumerique.getArk()).isEqualTo(DEFAULT_ARK);
        assertThat(testArticleNumerique.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testArticleNumerique.getEditeur()).isEqualTo(DEFAULT_EDITEUR);
        assertThat(testArticleNumerique.getAuteur()).isEqualTo(DEFAULT_AUTEUR);
        assertThat(testArticleNumerique.getCollection()).isEqualTo(DEFAULT_COLLECTION);
        assertThat(testArticleNumerique.getDistributeur()).isEqualTo(DEFAULT_DISTRIBUTEUR);
        assertThat(testArticleNumerique.getUrlCouverture()).isEqualTo(DEFAULT_URL_COUVERTURE);
        assertThat(testArticleNumerique.getUrlDemo()).isEqualTo(DEFAULT_URL_DEMO);
        assertThat(testArticleNumerique.getDateParution()).isEqualTo(DEFAULT_DATE_PARUTION);
        assertThat(testArticleNumerique.isCompatibleGAR()).isEqualTo(DEFAULT_COMPATIBLE_GAR);
        assertThat(testArticleNumerique.isAccessibleENT()).isEqualTo(DEFAULT_ACCESSIBLE_ENT);
        assertThat(testArticleNumerique.getEanPapier()).isEqualTo(DEFAULT_EAN_PAPIER);
        assertThat(testArticleNumerique.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testArticleNumerique.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testArticleNumerique.getPublicCible()).isEqualTo(DEFAULT_PUBLIC_CIBLE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testArticleNumerique.getId()).isEqualTo(testArticleNumerique.getDisponibilite().getId());

        // Validate the ArticleNumerique in Elasticsearch
        verify(mockArticleNumeriqueSearchRepository, times(1)).save(testArticleNumerique);
    }

    @Test
    @Transactional
    public void createArticleNumeriqueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articleNumeriqueRepository.findAll().size();

        // Create the ArticleNumerique with an existing ID
        articleNumerique.setId(1L);
        ArticleNumeriqueDTO articleNumeriqueDTO = articleNumeriqueMapper.toDto(articleNumerique);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticleNumeriqueMockMvc.perform(post("/api/article-numeriques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleNumeriqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleNumerique in the database
        List<ArticleNumerique> articleNumeriqueList = articleNumeriqueRepository.findAll();
        assertThat(articleNumeriqueList).hasSize(databaseSizeBeforeCreate);

        // Validate the ArticleNumerique in Elasticsearch
        verify(mockArticleNumeriqueSearchRepository, times(0)).save(articleNumerique);
    }

    @Test
    @Transactional
    public void updateArticleNumeriqueMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);
        int databaseSizeBeforeCreate = articleNumeriqueRepository.findAll().size();


        // Load the articleNumerique
        ArticleNumerique updatedArticleNumerique = articleNumeriqueRepository.findById(articleNumerique.getId()).get();
        // Disconnect from session so that the updates on updatedArticleNumerique are not directly saved in db
        em.detach(updatedArticleNumerique);

        // Update the Disponibilite with new association value
        updatedArticleNumerique.setDisponibilite(articleNumerique.getDisponibilite());
        ArticleNumeriqueDTO updatedArticleNumeriqueDTO = articleNumeriqueMapper.toDto(updatedArticleNumerique);

        // Update the entity
        restArticleNumeriqueMockMvc.perform(put("/api/article-numeriques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticleNumeriqueDTO)))
            .andExpect(status().isOk());

        // Validate the ArticleNumerique in the database
        List<ArticleNumerique> articleNumeriqueList = articleNumeriqueRepository.findAll();
        assertThat(articleNumeriqueList).hasSize(databaseSizeBeforeCreate);
        ArticleNumerique testArticleNumerique = articleNumeriqueList.get(articleNumeriqueList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testArticleNumerique.getId()).isEqualTo(testArticleNumerique.getDisponibilite().getId());

        // Validate the ArticleNumerique in Elasticsearch
        verify(mockArticleNumeriqueSearchRepository, times(1)).save(articleNumerique);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriques() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList
        restArticleNumeriqueMockMvc.perform(get("/api/article-numeriques?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleNumerique.getId().intValue())))
            .andExpect(jsonPath("$.[*].ean").value(hasItem(DEFAULT_EAN)))
            .andExpect(jsonPath("$.[*].ark").value(hasItem(DEFAULT_ARK)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].editeur").value(hasItem(DEFAULT_EDITEUR)))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR)))
            .andExpect(jsonPath("$.[*].collection").value(hasItem(DEFAULT_COLLECTION)))
            .andExpect(jsonPath("$.[*].distributeur").value(hasItem(DEFAULT_DISTRIBUTEUR)))
            .andExpect(jsonPath("$.[*].urlCouverture").value(hasItem(DEFAULT_URL_COUVERTURE)))
            .andExpect(jsonPath("$.[*].urlDemo").value(hasItem(DEFAULT_URL_DEMO)))
            .andExpect(jsonPath("$.[*].dateParution").value(hasItem(DEFAULT_DATE_PARUTION.toString())))
            .andExpect(jsonPath("$.[*].compatibleGAR").value(hasItem(DEFAULT_COMPATIBLE_GAR.booleanValue())))
            .andExpect(jsonPath("$.[*].accessibleENT").value(hasItem(DEFAULT_ACCESSIBLE_ENT.booleanValue())))
            .andExpect(jsonPath("$.[*].eanPapier").value(hasItem(DEFAULT_EAN_PAPIER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].publicCible").value(hasItem(DEFAULT_PUBLIC_CIBLE.toString())));
    }

    @Test
    @Transactional
    public void getArticleNumerique() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get the articleNumerique
        restArticleNumeriqueMockMvc.perform(get("/api/article-numeriques/{id}", articleNumerique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articleNumerique.getId().intValue()))
            .andExpect(jsonPath("$.ean").value(DEFAULT_EAN))
            .andExpect(jsonPath("$.ark").value(DEFAULT_ARK))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.editeur").value(DEFAULT_EDITEUR))
            .andExpect(jsonPath("$.auteur").value(DEFAULT_AUTEUR))
            .andExpect(jsonPath("$.collection").value(DEFAULT_COLLECTION))
            .andExpect(jsonPath("$.distributeur").value(DEFAULT_DISTRIBUTEUR))
            .andExpect(jsonPath("$.urlCouverture").value(DEFAULT_URL_COUVERTURE))
            .andExpect(jsonPath("$.urlDemo").value(DEFAULT_URL_DEMO))
            .andExpect(jsonPath("$.dateParution").value(DEFAULT_DATE_PARUTION.toString()))
            .andExpect(jsonPath("$.compatibleGAR").value(DEFAULT_COMPATIBLE_GAR.booleanValue()))
            .andExpect(jsonPath("$.accessibleENT").value(DEFAULT_ACCESSIBLE_ENT.booleanValue()))
            .andExpect(jsonPath("$.eanPapier").value(DEFAULT_EAN_PAPIER))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.publicCible").value(DEFAULT_PUBLIC_CIBLE.toString()));
    }


    @Test
    @Transactional
    public void getArticleNumeriquesByIdFiltering() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        Long id = articleNumerique.getId();

        defaultArticleNumeriqueShouldBeFound("id.equals=" + id);
        defaultArticleNumeriqueShouldNotBeFound("id.notEquals=" + id);

        defaultArticleNumeriqueShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultArticleNumeriqueShouldNotBeFound("id.greaterThan=" + id);

        defaultArticleNumeriqueShouldBeFound("id.lessThanOrEqual=" + id);
        defaultArticleNumeriqueShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByEanIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ean equals to DEFAULT_EAN
        defaultArticleNumeriqueShouldBeFound("ean.equals=" + DEFAULT_EAN);

        // Get all the articleNumeriqueList where ean equals to UPDATED_EAN
        defaultArticleNumeriqueShouldNotBeFound("ean.equals=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ean not equals to DEFAULT_EAN
        defaultArticleNumeriqueShouldNotBeFound("ean.notEquals=" + DEFAULT_EAN);

        // Get all the articleNumeriqueList where ean not equals to UPDATED_EAN
        defaultArticleNumeriqueShouldBeFound("ean.notEquals=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEanIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ean in DEFAULT_EAN or UPDATED_EAN
        defaultArticleNumeriqueShouldBeFound("ean.in=" + DEFAULT_EAN + "," + UPDATED_EAN);

        // Get all the articleNumeriqueList where ean equals to UPDATED_EAN
        defaultArticleNumeriqueShouldNotBeFound("ean.in=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEanIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ean is not null
        defaultArticleNumeriqueShouldBeFound("ean.specified=true");

        // Get all the articleNumeriqueList where ean is null
        defaultArticleNumeriqueShouldNotBeFound("ean.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticleNumeriquesByEanContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ean contains DEFAULT_EAN
        defaultArticleNumeriqueShouldBeFound("ean.contains=" + DEFAULT_EAN);

        // Get all the articleNumeriqueList where ean contains UPDATED_EAN
        defaultArticleNumeriqueShouldNotBeFound("ean.contains=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEanNotContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ean does not contain DEFAULT_EAN
        defaultArticleNumeriqueShouldNotBeFound("ean.doesNotContain=" + DEFAULT_EAN);

        // Get all the articleNumeriqueList where ean does not contain UPDATED_EAN
        defaultArticleNumeriqueShouldBeFound("ean.doesNotContain=" + UPDATED_EAN);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByArkIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ark equals to DEFAULT_ARK
        defaultArticleNumeriqueShouldBeFound("ark.equals=" + DEFAULT_ARK);

        // Get all the articleNumeriqueList where ark equals to UPDATED_ARK
        defaultArticleNumeriqueShouldNotBeFound("ark.equals=" + UPDATED_ARK);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByArkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ark not equals to DEFAULT_ARK
        defaultArticleNumeriqueShouldNotBeFound("ark.notEquals=" + DEFAULT_ARK);

        // Get all the articleNumeriqueList where ark not equals to UPDATED_ARK
        defaultArticleNumeriqueShouldBeFound("ark.notEquals=" + UPDATED_ARK);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByArkIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ark in DEFAULT_ARK or UPDATED_ARK
        defaultArticleNumeriqueShouldBeFound("ark.in=" + DEFAULT_ARK + "," + UPDATED_ARK);

        // Get all the articleNumeriqueList where ark equals to UPDATED_ARK
        defaultArticleNumeriqueShouldNotBeFound("ark.in=" + UPDATED_ARK);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByArkIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ark is not null
        defaultArticleNumeriqueShouldBeFound("ark.specified=true");

        // Get all the articleNumeriqueList where ark is null
        defaultArticleNumeriqueShouldNotBeFound("ark.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticleNumeriquesByArkContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ark contains DEFAULT_ARK
        defaultArticleNumeriqueShouldBeFound("ark.contains=" + DEFAULT_ARK);

        // Get all the articleNumeriqueList where ark contains UPDATED_ARK
        defaultArticleNumeriqueShouldNotBeFound("ark.contains=" + UPDATED_ARK);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByArkNotContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where ark does not contain DEFAULT_ARK
        defaultArticleNumeriqueShouldNotBeFound("ark.doesNotContain=" + DEFAULT_ARK);

        // Get all the articleNumeriqueList where ark does not contain UPDATED_ARK
        defaultArticleNumeriqueShouldBeFound("ark.doesNotContain=" + UPDATED_ARK);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByTitreIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where titre equals to DEFAULT_TITRE
        defaultArticleNumeriqueShouldBeFound("titre.equals=" + DEFAULT_TITRE);

        // Get all the articleNumeriqueList where titre equals to UPDATED_TITRE
        defaultArticleNumeriqueShouldNotBeFound("titre.equals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByTitreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where titre not equals to DEFAULT_TITRE
        defaultArticleNumeriqueShouldNotBeFound("titre.notEquals=" + DEFAULT_TITRE);

        // Get all the articleNumeriqueList where titre not equals to UPDATED_TITRE
        defaultArticleNumeriqueShouldBeFound("titre.notEquals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByTitreIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where titre in DEFAULT_TITRE or UPDATED_TITRE
        defaultArticleNumeriqueShouldBeFound("titre.in=" + DEFAULT_TITRE + "," + UPDATED_TITRE);

        // Get all the articleNumeriqueList where titre equals to UPDATED_TITRE
        defaultArticleNumeriqueShouldNotBeFound("titre.in=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByTitreIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where titre is not null
        defaultArticleNumeriqueShouldBeFound("titre.specified=true");

        // Get all the articleNumeriqueList where titre is null
        defaultArticleNumeriqueShouldNotBeFound("titre.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticleNumeriquesByTitreContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where titre contains DEFAULT_TITRE
        defaultArticleNumeriqueShouldBeFound("titre.contains=" + DEFAULT_TITRE);

        // Get all the articleNumeriqueList where titre contains UPDATED_TITRE
        defaultArticleNumeriqueShouldNotBeFound("titre.contains=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByTitreNotContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where titre does not contain DEFAULT_TITRE
        defaultArticleNumeriqueShouldNotBeFound("titre.doesNotContain=" + DEFAULT_TITRE);

        // Get all the articleNumeriqueList where titre does not contain UPDATED_TITRE
        defaultArticleNumeriqueShouldBeFound("titre.doesNotContain=" + UPDATED_TITRE);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByEditeurIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where editeur equals to DEFAULT_EDITEUR
        defaultArticleNumeriqueShouldBeFound("editeur.equals=" + DEFAULT_EDITEUR);

        // Get all the articleNumeriqueList where editeur equals to UPDATED_EDITEUR
        defaultArticleNumeriqueShouldNotBeFound("editeur.equals=" + UPDATED_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEditeurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where editeur not equals to DEFAULT_EDITEUR
        defaultArticleNumeriqueShouldNotBeFound("editeur.notEquals=" + DEFAULT_EDITEUR);

        // Get all the articleNumeriqueList where editeur not equals to UPDATED_EDITEUR
        defaultArticleNumeriqueShouldBeFound("editeur.notEquals=" + UPDATED_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEditeurIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where editeur in DEFAULT_EDITEUR or UPDATED_EDITEUR
        defaultArticleNumeriqueShouldBeFound("editeur.in=" + DEFAULT_EDITEUR + "," + UPDATED_EDITEUR);

        // Get all the articleNumeriqueList where editeur equals to UPDATED_EDITEUR
        defaultArticleNumeriqueShouldNotBeFound("editeur.in=" + UPDATED_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEditeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where editeur is not null
        defaultArticleNumeriqueShouldBeFound("editeur.specified=true");

        // Get all the articleNumeriqueList where editeur is null
        defaultArticleNumeriqueShouldNotBeFound("editeur.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticleNumeriquesByEditeurContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where editeur contains DEFAULT_EDITEUR
        defaultArticleNumeriqueShouldBeFound("editeur.contains=" + DEFAULT_EDITEUR);

        // Get all the articleNumeriqueList where editeur contains UPDATED_EDITEUR
        defaultArticleNumeriqueShouldNotBeFound("editeur.contains=" + UPDATED_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEditeurNotContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where editeur does not contain DEFAULT_EDITEUR
        defaultArticleNumeriqueShouldNotBeFound("editeur.doesNotContain=" + DEFAULT_EDITEUR);

        // Get all the articleNumeriqueList where editeur does not contain UPDATED_EDITEUR
        defaultArticleNumeriqueShouldBeFound("editeur.doesNotContain=" + UPDATED_EDITEUR);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByAuteurIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where auteur equals to DEFAULT_AUTEUR
        defaultArticleNumeriqueShouldBeFound("auteur.equals=" + DEFAULT_AUTEUR);

        // Get all the articleNumeriqueList where auteur equals to UPDATED_AUTEUR
        defaultArticleNumeriqueShouldNotBeFound("auteur.equals=" + UPDATED_AUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByAuteurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where auteur not equals to DEFAULT_AUTEUR
        defaultArticleNumeriqueShouldNotBeFound("auteur.notEquals=" + DEFAULT_AUTEUR);

        // Get all the articleNumeriqueList where auteur not equals to UPDATED_AUTEUR
        defaultArticleNumeriqueShouldBeFound("auteur.notEquals=" + UPDATED_AUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByAuteurIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where auteur in DEFAULT_AUTEUR or UPDATED_AUTEUR
        defaultArticleNumeriqueShouldBeFound("auteur.in=" + DEFAULT_AUTEUR + "," + UPDATED_AUTEUR);

        // Get all the articleNumeriqueList where auteur equals to UPDATED_AUTEUR
        defaultArticleNumeriqueShouldNotBeFound("auteur.in=" + UPDATED_AUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByAuteurIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where auteur is not null
        defaultArticleNumeriqueShouldBeFound("auteur.specified=true");

        // Get all the articleNumeriqueList where auteur is null
        defaultArticleNumeriqueShouldNotBeFound("auteur.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticleNumeriquesByAuteurContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where auteur contains DEFAULT_AUTEUR
        defaultArticleNumeriqueShouldBeFound("auteur.contains=" + DEFAULT_AUTEUR);

        // Get all the articleNumeriqueList where auteur contains UPDATED_AUTEUR
        defaultArticleNumeriqueShouldNotBeFound("auteur.contains=" + UPDATED_AUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByAuteurNotContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where auteur does not contain DEFAULT_AUTEUR
        defaultArticleNumeriqueShouldNotBeFound("auteur.doesNotContain=" + DEFAULT_AUTEUR);

        // Get all the articleNumeriqueList where auteur does not contain UPDATED_AUTEUR
        defaultArticleNumeriqueShouldBeFound("auteur.doesNotContain=" + UPDATED_AUTEUR);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByCollectionIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where collection equals to DEFAULT_COLLECTION
        defaultArticleNumeriqueShouldBeFound("collection.equals=" + DEFAULT_COLLECTION);

        // Get all the articleNumeriqueList where collection equals to UPDATED_COLLECTION
        defaultArticleNumeriqueShouldNotBeFound("collection.equals=" + UPDATED_COLLECTION);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByCollectionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where collection not equals to DEFAULT_COLLECTION
        defaultArticleNumeriqueShouldNotBeFound("collection.notEquals=" + DEFAULT_COLLECTION);

        // Get all the articleNumeriqueList where collection not equals to UPDATED_COLLECTION
        defaultArticleNumeriqueShouldBeFound("collection.notEquals=" + UPDATED_COLLECTION);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByCollectionIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where collection in DEFAULT_COLLECTION or UPDATED_COLLECTION
        defaultArticleNumeriqueShouldBeFound("collection.in=" + DEFAULT_COLLECTION + "," + UPDATED_COLLECTION);

        // Get all the articleNumeriqueList where collection equals to UPDATED_COLLECTION
        defaultArticleNumeriqueShouldNotBeFound("collection.in=" + UPDATED_COLLECTION);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByCollectionIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where collection is not null
        defaultArticleNumeriqueShouldBeFound("collection.specified=true");

        // Get all the articleNumeriqueList where collection is null
        defaultArticleNumeriqueShouldNotBeFound("collection.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticleNumeriquesByCollectionContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where collection contains DEFAULT_COLLECTION
        defaultArticleNumeriqueShouldBeFound("collection.contains=" + DEFAULT_COLLECTION);

        // Get all the articleNumeriqueList where collection contains UPDATED_COLLECTION
        defaultArticleNumeriqueShouldNotBeFound("collection.contains=" + UPDATED_COLLECTION);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByCollectionNotContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where collection does not contain DEFAULT_COLLECTION
        defaultArticleNumeriqueShouldNotBeFound("collection.doesNotContain=" + DEFAULT_COLLECTION);

        // Get all the articleNumeriqueList where collection does not contain UPDATED_COLLECTION
        defaultArticleNumeriqueShouldBeFound("collection.doesNotContain=" + UPDATED_COLLECTION);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByDistributeurIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where distributeur equals to DEFAULT_DISTRIBUTEUR
        defaultArticleNumeriqueShouldBeFound("distributeur.equals=" + DEFAULT_DISTRIBUTEUR);

        // Get all the articleNumeriqueList where distributeur equals to UPDATED_DISTRIBUTEUR
        defaultArticleNumeriqueShouldNotBeFound("distributeur.equals=" + UPDATED_DISTRIBUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDistributeurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where distributeur not equals to DEFAULT_DISTRIBUTEUR
        defaultArticleNumeriqueShouldNotBeFound("distributeur.notEquals=" + DEFAULT_DISTRIBUTEUR);

        // Get all the articleNumeriqueList where distributeur not equals to UPDATED_DISTRIBUTEUR
        defaultArticleNumeriqueShouldBeFound("distributeur.notEquals=" + UPDATED_DISTRIBUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDistributeurIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where distributeur in DEFAULT_DISTRIBUTEUR or UPDATED_DISTRIBUTEUR
        defaultArticleNumeriqueShouldBeFound("distributeur.in=" + DEFAULT_DISTRIBUTEUR + "," + UPDATED_DISTRIBUTEUR);

        // Get all the articleNumeriqueList where distributeur equals to UPDATED_DISTRIBUTEUR
        defaultArticleNumeriqueShouldNotBeFound("distributeur.in=" + UPDATED_DISTRIBUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDistributeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where distributeur is not null
        defaultArticleNumeriqueShouldBeFound("distributeur.specified=true");

        // Get all the articleNumeriqueList where distributeur is null
        defaultArticleNumeriqueShouldNotBeFound("distributeur.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticleNumeriquesByDistributeurContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where distributeur contains DEFAULT_DISTRIBUTEUR
        defaultArticleNumeriqueShouldBeFound("distributeur.contains=" + DEFAULT_DISTRIBUTEUR);

        // Get all the articleNumeriqueList where distributeur contains UPDATED_DISTRIBUTEUR
        defaultArticleNumeriqueShouldNotBeFound("distributeur.contains=" + UPDATED_DISTRIBUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDistributeurNotContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where distributeur does not contain DEFAULT_DISTRIBUTEUR
        defaultArticleNumeriqueShouldNotBeFound("distributeur.doesNotContain=" + DEFAULT_DISTRIBUTEUR);

        // Get all the articleNumeriqueList where distributeur does not contain UPDATED_DISTRIBUTEUR
        defaultArticleNumeriqueShouldBeFound("distributeur.doesNotContain=" + UPDATED_DISTRIBUTEUR);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlCouvertureIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlCouverture equals to DEFAULT_URL_COUVERTURE
        defaultArticleNumeriqueShouldBeFound("urlCouverture.equals=" + DEFAULT_URL_COUVERTURE);

        // Get all the articleNumeriqueList where urlCouverture equals to UPDATED_URL_COUVERTURE
        defaultArticleNumeriqueShouldNotBeFound("urlCouverture.equals=" + UPDATED_URL_COUVERTURE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlCouvertureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlCouverture not equals to DEFAULT_URL_COUVERTURE
        defaultArticleNumeriqueShouldNotBeFound("urlCouverture.notEquals=" + DEFAULT_URL_COUVERTURE);

        // Get all the articleNumeriqueList where urlCouverture not equals to UPDATED_URL_COUVERTURE
        defaultArticleNumeriqueShouldBeFound("urlCouverture.notEquals=" + UPDATED_URL_COUVERTURE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlCouvertureIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlCouverture in DEFAULT_URL_COUVERTURE or UPDATED_URL_COUVERTURE
        defaultArticleNumeriqueShouldBeFound("urlCouverture.in=" + DEFAULT_URL_COUVERTURE + "," + UPDATED_URL_COUVERTURE);

        // Get all the articleNumeriqueList where urlCouverture equals to UPDATED_URL_COUVERTURE
        defaultArticleNumeriqueShouldNotBeFound("urlCouverture.in=" + UPDATED_URL_COUVERTURE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlCouvertureIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlCouverture is not null
        defaultArticleNumeriqueShouldBeFound("urlCouverture.specified=true");

        // Get all the articleNumeriqueList where urlCouverture is null
        defaultArticleNumeriqueShouldNotBeFound("urlCouverture.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlCouvertureContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlCouverture contains DEFAULT_URL_COUVERTURE
        defaultArticleNumeriqueShouldBeFound("urlCouverture.contains=" + DEFAULT_URL_COUVERTURE);

        // Get all the articleNumeriqueList where urlCouverture contains UPDATED_URL_COUVERTURE
        defaultArticleNumeriqueShouldNotBeFound("urlCouverture.contains=" + UPDATED_URL_COUVERTURE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlCouvertureNotContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlCouverture does not contain DEFAULT_URL_COUVERTURE
        defaultArticleNumeriqueShouldNotBeFound("urlCouverture.doesNotContain=" + DEFAULT_URL_COUVERTURE);

        // Get all the articleNumeriqueList where urlCouverture does not contain UPDATED_URL_COUVERTURE
        defaultArticleNumeriqueShouldBeFound("urlCouverture.doesNotContain=" + UPDATED_URL_COUVERTURE);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlDemoIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlDemo equals to DEFAULT_URL_DEMO
        defaultArticleNumeriqueShouldBeFound("urlDemo.equals=" + DEFAULT_URL_DEMO);

        // Get all the articleNumeriqueList where urlDemo equals to UPDATED_URL_DEMO
        defaultArticleNumeriqueShouldNotBeFound("urlDemo.equals=" + UPDATED_URL_DEMO);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlDemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlDemo not equals to DEFAULT_URL_DEMO
        defaultArticleNumeriqueShouldNotBeFound("urlDemo.notEquals=" + DEFAULT_URL_DEMO);

        // Get all the articleNumeriqueList where urlDemo not equals to UPDATED_URL_DEMO
        defaultArticleNumeriqueShouldBeFound("urlDemo.notEquals=" + UPDATED_URL_DEMO);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlDemoIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlDemo in DEFAULT_URL_DEMO or UPDATED_URL_DEMO
        defaultArticleNumeriqueShouldBeFound("urlDemo.in=" + DEFAULT_URL_DEMO + "," + UPDATED_URL_DEMO);

        // Get all the articleNumeriqueList where urlDemo equals to UPDATED_URL_DEMO
        defaultArticleNumeriqueShouldNotBeFound("urlDemo.in=" + UPDATED_URL_DEMO);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlDemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlDemo is not null
        defaultArticleNumeriqueShouldBeFound("urlDemo.specified=true");

        // Get all the articleNumeriqueList where urlDemo is null
        defaultArticleNumeriqueShouldNotBeFound("urlDemo.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlDemoContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlDemo contains DEFAULT_URL_DEMO
        defaultArticleNumeriqueShouldBeFound("urlDemo.contains=" + DEFAULT_URL_DEMO);

        // Get all the articleNumeriqueList where urlDemo contains UPDATED_URL_DEMO
        defaultArticleNumeriqueShouldNotBeFound("urlDemo.contains=" + UPDATED_URL_DEMO);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByUrlDemoNotContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where urlDemo does not contain DEFAULT_URL_DEMO
        defaultArticleNumeriqueShouldNotBeFound("urlDemo.doesNotContain=" + DEFAULT_URL_DEMO);

        // Get all the articleNumeriqueList where urlDemo does not contain UPDATED_URL_DEMO
        defaultArticleNumeriqueShouldBeFound("urlDemo.doesNotContain=" + UPDATED_URL_DEMO);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByDateParutionIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where dateParution equals to DEFAULT_DATE_PARUTION
        defaultArticleNumeriqueShouldBeFound("dateParution.equals=" + DEFAULT_DATE_PARUTION);

        // Get all the articleNumeriqueList where dateParution equals to UPDATED_DATE_PARUTION
        defaultArticleNumeriqueShouldNotBeFound("dateParution.equals=" + UPDATED_DATE_PARUTION);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDateParutionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where dateParution not equals to DEFAULT_DATE_PARUTION
        defaultArticleNumeriqueShouldNotBeFound("dateParution.notEquals=" + DEFAULT_DATE_PARUTION);

        // Get all the articleNumeriqueList where dateParution not equals to UPDATED_DATE_PARUTION
        defaultArticleNumeriqueShouldBeFound("dateParution.notEquals=" + UPDATED_DATE_PARUTION);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDateParutionIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where dateParution in DEFAULT_DATE_PARUTION or UPDATED_DATE_PARUTION
        defaultArticleNumeriqueShouldBeFound("dateParution.in=" + DEFAULT_DATE_PARUTION + "," + UPDATED_DATE_PARUTION);

        // Get all the articleNumeriqueList where dateParution equals to UPDATED_DATE_PARUTION
        defaultArticleNumeriqueShouldNotBeFound("dateParution.in=" + UPDATED_DATE_PARUTION);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDateParutionIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where dateParution is not null
        defaultArticleNumeriqueShouldBeFound("dateParution.specified=true");

        // Get all the articleNumeriqueList where dateParution is null
        defaultArticleNumeriqueShouldNotBeFound("dateParution.specified=false");
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByCompatibleGARIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where compatibleGAR equals to DEFAULT_COMPATIBLE_GAR
        defaultArticleNumeriqueShouldBeFound("compatibleGAR.equals=" + DEFAULT_COMPATIBLE_GAR);

        // Get all the articleNumeriqueList where compatibleGAR equals to UPDATED_COMPATIBLE_GAR
        defaultArticleNumeriqueShouldNotBeFound("compatibleGAR.equals=" + UPDATED_COMPATIBLE_GAR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByCompatibleGARIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where compatibleGAR not equals to DEFAULT_COMPATIBLE_GAR
        defaultArticleNumeriqueShouldNotBeFound("compatibleGAR.notEquals=" + DEFAULT_COMPATIBLE_GAR);

        // Get all the articleNumeriqueList where compatibleGAR not equals to UPDATED_COMPATIBLE_GAR
        defaultArticleNumeriqueShouldBeFound("compatibleGAR.notEquals=" + UPDATED_COMPATIBLE_GAR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByCompatibleGARIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where compatibleGAR in DEFAULT_COMPATIBLE_GAR or UPDATED_COMPATIBLE_GAR
        defaultArticleNumeriqueShouldBeFound("compatibleGAR.in=" + DEFAULT_COMPATIBLE_GAR + "," + UPDATED_COMPATIBLE_GAR);

        // Get all the articleNumeriqueList where compatibleGAR equals to UPDATED_COMPATIBLE_GAR
        defaultArticleNumeriqueShouldNotBeFound("compatibleGAR.in=" + UPDATED_COMPATIBLE_GAR);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByCompatibleGARIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where compatibleGAR is not null
        defaultArticleNumeriqueShouldBeFound("compatibleGAR.specified=true");

        // Get all the articleNumeriqueList where compatibleGAR is null
        defaultArticleNumeriqueShouldNotBeFound("compatibleGAR.specified=false");
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByAccessibleENTIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where accessibleENT equals to DEFAULT_ACCESSIBLE_ENT
        defaultArticleNumeriqueShouldBeFound("accessibleENT.equals=" + DEFAULT_ACCESSIBLE_ENT);

        // Get all the articleNumeriqueList where accessibleENT equals to UPDATED_ACCESSIBLE_ENT
        defaultArticleNumeriqueShouldNotBeFound("accessibleENT.equals=" + UPDATED_ACCESSIBLE_ENT);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByAccessibleENTIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where accessibleENT not equals to DEFAULT_ACCESSIBLE_ENT
        defaultArticleNumeriqueShouldNotBeFound("accessibleENT.notEquals=" + DEFAULT_ACCESSIBLE_ENT);

        // Get all the articleNumeriqueList where accessibleENT not equals to UPDATED_ACCESSIBLE_ENT
        defaultArticleNumeriqueShouldBeFound("accessibleENT.notEquals=" + UPDATED_ACCESSIBLE_ENT);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByAccessibleENTIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where accessibleENT in DEFAULT_ACCESSIBLE_ENT or UPDATED_ACCESSIBLE_ENT
        defaultArticleNumeriqueShouldBeFound("accessibleENT.in=" + DEFAULT_ACCESSIBLE_ENT + "," + UPDATED_ACCESSIBLE_ENT);

        // Get all the articleNumeriqueList where accessibleENT equals to UPDATED_ACCESSIBLE_ENT
        defaultArticleNumeriqueShouldNotBeFound("accessibleENT.in=" + UPDATED_ACCESSIBLE_ENT);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByAccessibleENTIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where accessibleENT is not null
        defaultArticleNumeriqueShouldBeFound("accessibleENT.specified=true");

        // Get all the articleNumeriqueList where accessibleENT is null
        defaultArticleNumeriqueShouldNotBeFound("accessibleENT.specified=false");
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEanPapierIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where eanPapier equals to DEFAULT_EAN_PAPIER
        defaultArticleNumeriqueShouldBeFound("eanPapier.equals=" + DEFAULT_EAN_PAPIER);

        // Get all the articleNumeriqueList where eanPapier equals to UPDATED_EAN_PAPIER
        defaultArticleNumeriqueShouldNotBeFound("eanPapier.equals=" + UPDATED_EAN_PAPIER);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEanPapierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where eanPapier not equals to DEFAULT_EAN_PAPIER
        defaultArticleNumeriqueShouldNotBeFound("eanPapier.notEquals=" + DEFAULT_EAN_PAPIER);

        // Get all the articleNumeriqueList where eanPapier not equals to UPDATED_EAN_PAPIER
        defaultArticleNumeriqueShouldBeFound("eanPapier.notEquals=" + UPDATED_EAN_PAPIER);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEanPapierIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where eanPapier in DEFAULT_EAN_PAPIER or UPDATED_EAN_PAPIER
        defaultArticleNumeriqueShouldBeFound("eanPapier.in=" + DEFAULT_EAN_PAPIER + "," + UPDATED_EAN_PAPIER);

        // Get all the articleNumeriqueList where eanPapier equals to UPDATED_EAN_PAPIER
        defaultArticleNumeriqueShouldNotBeFound("eanPapier.in=" + UPDATED_EAN_PAPIER);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEanPapierIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where eanPapier is not null
        defaultArticleNumeriqueShouldBeFound("eanPapier.specified=true");

        // Get all the articleNumeriqueList where eanPapier is null
        defaultArticleNumeriqueShouldNotBeFound("eanPapier.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticleNumeriquesByEanPapierContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where eanPapier contains DEFAULT_EAN_PAPIER
        defaultArticleNumeriqueShouldBeFound("eanPapier.contains=" + DEFAULT_EAN_PAPIER);

        // Get all the articleNumeriqueList where eanPapier contains UPDATED_EAN_PAPIER
        defaultArticleNumeriqueShouldNotBeFound("eanPapier.contains=" + UPDATED_EAN_PAPIER);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByEanPapierNotContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where eanPapier does not contain DEFAULT_EAN_PAPIER
        defaultArticleNumeriqueShouldNotBeFound("eanPapier.doesNotContain=" + DEFAULT_EAN_PAPIER);

        // Get all the articleNumeriqueList where eanPapier does not contain UPDATED_EAN_PAPIER
        defaultArticleNumeriqueShouldBeFound("eanPapier.doesNotContain=" + UPDATED_EAN_PAPIER);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where description equals to DEFAULT_DESCRIPTION
        defaultArticleNumeriqueShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the articleNumeriqueList where description equals to UPDATED_DESCRIPTION
        defaultArticleNumeriqueShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where description not equals to DEFAULT_DESCRIPTION
        defaultArticleNumeriqueShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the articleNumeriqueList where description not equals to UPDATED_DESCRIPTION
        defaultArticleNumeriqueShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultArticleNumeriqueShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the articleNumeriqueList where description equals to UPDATED_DESCRIPTION
        defaultArticleNumeriqueShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where description is not null
        defaultArticleNumeriqueShouldBeFound("description.specified=true");

        // Get all the articleNumeriqueList where description is null
        defaultArticleNumeriqueShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticleNumeriquesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where description contains DEFAULT_DESCRIPTION
        defaultArticleNumeriqueShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the articleNumeriqueList where description contains UPDATED_DESCRIPTION
        defaultArticleNumeriqueShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where description does not contain DEFAULT_DESCRIPTION
        defaultArticleNumeriqueShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the articleNumeriqueList where description does not contain UPDATED_DESCRIPTION
        defaultArticleNumeriqueShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where type equals to DEFAULT_TYPE
        defaultArticleNumeriqueShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the articleNumeriqueList where type equals to UPDATED_TYPE
        defaultArticleNumeriqueShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where type not equals to DEFAULT_TYPE
        defaultArticleNumeriqueShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the articleNumeriqueList where type not equals to UPDATED_TYPE
        defaultArticleNumeriqueShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultArticleNumeriqueShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the articleNumeriqueList where type equals to UPDATED_TYPE
        defaultArticleNumeriqueShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where type is not null
        defaultArticleNumeriqueShouldBeFound("type.specified=true");

        // Get all the articleNumeriqueList where type is null
        defaultArticleNumeriqueShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByPublicCibleIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where publicCible equals to DEFAULT_PUBLIC_CIBLE
        defaultArticleNumeriqueShouldBeFound("publicCible.equals=" + DEFAULT_PUBLIC_CIBLE);

        // Get all the articleNumeriqueList where publicCible equals to UPDATED_PUBLIC_CIBLE
        defaultArticleNumeriqueShouldNotBeFound("publicCible.equals=" + UPDATED_PUBLIC_CIBLE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByPublicCibleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where publicCible not equals to DEFAULT_PUBLIC_CIBLE
        defaultArticleNumeriqueShouldNotBeFound("publicCible.notEquals=" + DEFAULT_PUBLIC_CIBLE);

        // Get all the articleNumeriqueList where publicCible not equals to UPDATED_PUBLIC_CIBLE
        defaultArticleNumeriqueShouldBeFound("publicCible.notEquals=" + UPDATED_PUBLIC_CIBLE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByPublicCibleIsInShouldWork() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where publicCible in DEFAULT_PUBLIC_CIBLE or UPDATED_PUBLIC_CIBLE
        defaultArticleNumeriqueShouldBeFound("publicCible.in=" + DEFAULT_PUBLIC_CIBLE + "," + UPDATED_PUBLIC_CIBLE);

        // Get all the articleNumeriqueList where publicCible equals to UPDATED_PUBLIC_CIBLE
        defaultArticleNumeriqueShouldNotBeFound("publicCible.in=" + UPDATED_PUBLIC_CIBLE);
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByPublicCibleIsNullOrNotNull() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        // Get all the articleNumeriqueList where publicCible is not null
        defaultArticleNumeriqueShouldBeFound("publicCible.specified=true");

        // Get all the articleNumeriqueList where publicCible is null
        defaultArticleNumeriqueShouldNotBeFound("publicCible.specified=false");
    }

    @Test
    @Transactional
    public void getAllArticleNumeriquesByDisciplineIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);
        Discipline discipline = DisciplineResourceIT.createEntity(em);
        em.persist(discipline);
        em.flush();
        articleNumerique.addDiscipline(discipline);
        articleNumeriqueRepository.saveAndFlush(articleNumerique);
        Long disciplineId = discipline.getId();

        // Get all the articleNumeriqueList where discipline equals to disciplineId
        defaultArticleNumeriqueShouldBeFound("disciplineId.equals=" + disciplineId);

        // Get all the articleNumeriqueList where discipline equals to disciplineId + 1
        defaultArticleNumeriqueShouldNotBeFound("disciplineId.equals=" + (disciplineId + 1));
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByNiveauIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);
        Niveau niveau = NiveauResourceIT.createEntity(em);
        em.persist(niveau);
        em.flush();
        articleNumerique.addNiveau(niveau);
        articleNumeriqueRepository.saveAndFlush(articleNumerique);
        Long niveauId = niveau.getId();

        // Get all the articleNumeriqueList where niveau equals to niveauId
        defaultArticleNumeriqueShouldBeFound("niveauId.equals=" + niveauId);

        // Get all the articleNumeriqueList where niveau equals to niveauId + 1
        defaultArticleNumeriqueShouldNotBeFound("niveauId.equals=" + (niveauId + 1));
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByOffreIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);
        Offre offre = OffreResourceIT.createEntity(em);
        em.persist(offre);
        em.flush();
        articleNumerique.addOffre(offre);
        articleNumeriqueRepository.saveAndFlush(articleNumerique);
        Long offreId = offre.getId();

        // Get all the articleNumeriqueList where offre equals to offreId
        defaultArticleNumeriqueShouldBeFound("offreId.equals=" + offreId);

        // Get all the articleNumeriqueList where offre equals to offreId + 1
        defaultArticleNumeriqueShouldNotBeFound("offreId.equals=" + (offreId + 1));
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByTechnoIsEqualToSomething() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);
        Techno techno = TechnoResourceIT.createEntity(em);
        em.persist(techno);
        em.flush();
        articleNumerique.addTechno(techno);
        articleNumeriqueRepository.saveAndFlush(articleNumerique);
        Long technoId = techno.getId();

        // Get all the articleNumeriqueList where techno equals to technoId
        defaultArticleNumeriqueShouldBeFound("technoId.equals=" + technoId);

        // Get all the articleNumeriqueList where techno equals to technoId + 1
        defaultArticleNumeriqueShouldNotBeFound("technoId.equals=" + (technoId + 1));
    }


    @Test
    @Transactional
    public void getAllArticleNumeriquesByDisponibiliteIsEqualToSomething() throws Exception {
        // Get already existing entity
        Disponibilite disponibilite = articleNumerique.getDisponibilite();
        articleNumeriqueRepository.saveAndFlush(articleNumerique);
        Long disponibiliteId = disponibilite.getId();

        // Get all the articleNumeriqueList where disponibilite equals to disponibiliteId
        defaultArticleNumeriqueShouldBeFound("disponibiliteId.equals=" + disponibiliteId);

        // Get all the articleNumeriqueList where disponibilite equals to disponibiliteId + 1
        defaultArticleNumeriqueShouldNotBeFound("disponibiliteId.equals=" + (disponibiliteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultArticleNumeriqueShouldBeFound(String filter) throws Exception {
        restArticleNumeriqueMockMvc.perform(get("/api/article-numeriques?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleNumerique.getId().intValue())))
            .andExpect(jsonPath("$.[*].ean").value(hasItem(DEFAULT_EAN)))
            .andExpect(jsonPath("$.[*].ark").value(hasItem(DEFAULT_ARK)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].editeur").value(hasItem(DEFAULT_EDITEUR)))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR)))
            .andExpect(jsonPath("$.[*].collection").value(hasItem(DEFAULT_COLLECTION)))
            .andExpect(jsonPath("$.[*].distributeur").value(hasItem(DEFAULT_DISTRIBUTEUR)))
            .andExpect(jsonPath("$.[*].urlCouverture").value(hasItem(DEFAULT_URL_COUVERTURE)))
            .andExpect(jsonPath("$.[*].urlDemo").value(hasItem(DEFAULT_URL_DEMO)))
            .andExpect(jsonPath("$.[*].dateParution").value(hasItem(DEFAULT_DATE_PARUTION.toString())))
            .andExpect(jsonPath("$.[*].compatibleGAR").value(hasItem(DEFAULT_COMPATIBLE_GAR.booleanValue())))
            .andExpect(jsonPath("$.[*].accessibleENT").value(hasItem(DEFAULT_ACCESSIBLE_ENT.booleanValue())))
            .andExpect(jsonPath("$.[*].eanPapier").value(hasItem(DEFAULT_EAN_PAPIER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].publicCible").value(hasItem(DEFAULT_PUBLIC_CIBLE.toString())));

        // Check, that the count call also returns 1
        restArticleNumeriqueMockMvc.perform(get("/api/article-numeriques/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultArticleNumeriqueShouldNotBeFound(String filter) throws Exception {
        restArticleNumeriqueMockMvc.perform(get("/api/article-numeriques?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restArticleNumeriqueMockMvc.perform(get("/api/article-numeriques/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingArticleNumerique() throws Exception {
        // Get the articleNumerique
        restArticleNumeriqueMockMvc.perform(get("/api/article-numeriques/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticleNumerique() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        int databaseSizeBeforeUpdate = articleNumeriqueRepository.findAll().size();

        // Update the articleNumerique
        ArticleNumerique updatedArticleNumerique = articleNumeriqueRepository.findById(articleNumerique.getId()).get();
        // Disconnect from session so that the updates on updatedArticleNumerique are not directly saved in db
        em.detach(updatedArticleNumerique);
        updatedArticleNumerique
            .ean(UPDATED_EAN)
            .ark(UPDATED_ARK)
            .titre(UPDATED_TITRE)
            .editeur(UPDATED_EDITEUR)
            .auteur(UPDATED_AUTEUR)
            .collection(UPDATED_COLLECTION)
            .distributeur(UPDATED_DISTRIBUTEUR)
            .urlCouverture(UPDATED_URL_COUVERTURE)
            .urlDemo(UPDATED_URL_DEMO)
            .dateParution(UPDATED_DATE_PARUTION)
            .compatibleGAR(UPDATED_COMPATIBLE_GAR)
            .accessibleENT(UPDATED_ACCESSIBLE_ENT)
            .eanPapier(UPDATED_EAN_PAPIER)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .publicCible(UPDATED_PUBLIC_CIBLE);
        ArticleNumeriqueDTO articleNumeriqueDTO = articleNumeriqueMapper.toDto(updatedArticleNumerique);

        restArticleNumeriqueMockMvc.perform(put("/api/article-numeriques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleNumeriqueDTO)))
            .andExpect(status().isOk());

        // Validate the ArticleNumerique in the database
        List<ArticleNumerique> articleNumeriqueList = articleNumeriqueRepository.findAll();
        assertThat(articleNumeriqueList).hasSize(databaseSizeBeforeUpdate);
        ArticleNumerique testArticleNumerique = articleNumeriqueList.get(articleNumeriqueList.size() - 1);
        assertThat(testArticleNumerique.getEan()).isEqualTo(UPDATED_EAN);
        assertThat(testArticleNumerique.getArk()).isEqualTo(UPDATED_ARK);
        assertThat(testArticleNumerique.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testArticleNumerique.getEditeur()).isEqualTo(UPDATED_EDITEUR);
        assertThat(testArticleNumerique.getAuteur()).isEqualTo(UPDATED_AUTEUR);
        assertThat(testArticleNumerique.getCollection()).isEqualTo(UPDATED_COLLECTION);
        assertThat(testArticleNumerique.getDistributeur()).isEqualTo(UPDATED_DISTRIBUTEUR);
        assertThat(testArticleNumerique.getUrlCouverture()).isEqualTo(UPDATED_URL_COUVERTURE);
        assertThat(testArticleNumerique.getUrlDemo()).isEqualTo(UPDATED_URL_DEMO);
        assertThat(testArticleNumerique.getDateParution()).isEqualTo(UPDATED_DATE_PARUTION);
        assertThat(testArticleNumerique.isCompatibleGAR()).isEqualTo(UPDATED_COMPATIBLE_GAR);
        assertThat(testArticleNumerique.isAccessibleENT()).isEqualTo(UPDATED_ACCESSIBLE_ENT);
        assertThat(testArticleNumerique.getEanPapier()).isEqualTo(UPDATED_EAN_PAPIER);
        assertThat(testArticleNumerique.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testArticleNumerique.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testArticleNumerique.getPublicCible()).isEqualTo(UPDATED_PUBLIC_CIBLE);

        // Validate the ArticleNumerique in Elasticsearch
        verify(mockArticleNumeriqueSearchRepository, times(1)).save(testArticleNumerique);
    }

    @Test
    @Transactional
    public void updateNonExistingArticleNumerique() throws Exception {
        int databaseSizeBeforeUpdate = articleNumeriqueRepository.findAll().size();

        // Create the ArticleNumerique
        ArticleNumeriqueDTO articleNumeriqueDTO = articleNumeriqueMapper.toDto(articleNumerique);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticleNumeriqueMockMvc.perform(put("/api/article-numeriques")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articleNumeriqueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticleNumerique in the database
        List<ArticleNumerique> articleNumeriqueList = articleNumeriqueRepository.findAll();
        assertThat(articleNumeriqueList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ArticleNumerique in Elasticsearch
        verify(mockArticleNumeriqueSearchRepository, times(0)).save(articleNumerique);
    }

    @Test
    @Transactional
    public void deleteArticleNumerique() throws Exception {
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);

        int databaseSizeBeforeDelete = articleNumeriqueRepository.findAll().size();

        // Delete the articleNumerique
        restArticleNumeriqueMockMvc.perform(delete("/api/article-numeriques/{id}", articleNumerique.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticleNumerique> articleNumeriqueList = articleNumeriqueRepository.findAll();
        assertThat(articleNumeriqueList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ArticleNumerique in Elasticsearch
        verify(mockArticleNumeriqueSearchRepository, times(1)).deleteById(articleNumerique.getId());
    }

    @Test
    @Transactional
    public void searchArticleNumerique() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        articleNumeriqueRepository.saveAndFlush(articleNumerique);
        when(mockArticleNumeriqueSearchRepository.search(queryStringQuery("id:" + articleNumerique.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(articleNumerique), PageRequest.of(0, 1), 1));

        // Search the articleNumerique
        restArticleNumeriqueMockMvc.perform(get("/api/_search/article-numeriques?query=id:" + articleNumerique.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articleNumerique.getId().intValue())))
            .andExpect(jsonPath("$.[*].ean").value(hasItem(DEFAULT_EAN)))
            .andExpect(jsonPath("$.[*].ark").value(hasItem(DEFAULT_ARK)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].editeur").value(hasItem(DEFAULT_EDITEUR)))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR)))
            .andExpect(jsonPath("$.[*].collection").value(hasItem(DEFAULT_COLLECTION)))
            .andExpect(jsonPath("$.[*].distributeur").value(hasItem(DEFAULT_DISTRIBUTEUR)))
            .andExpect(jsonPath("$.[*].urlCouverture").value(hasItem(DEFAULT_URL_COUVERTURE)))
            .andExpect(jsonPath("$.[*].urlDemo").value(hasItem(DEFAULT_URL_DEMO)))
            .andExpect(jsonPath("$.[*].dateParution").value(hasItem(DEFAULT_DATE_PARUTION.toString())))
            .andExpect(jsonPath("$.[*].compatibleGAR").value(hasItem(DEFAULT_COMPATIBLE_GAR.booleanValue())))
            .andExpect(jsonPath("$.[*].accessibleENT").value(hasItem(DEFAULT_ACCESSIBLE_ENT.booleanValue())))
            .andExpect(jsonPath("$.[*].eanPapier").value(hasItem(DEFAULT_EAN_PAPIER)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].publicCible").value(hasItem(DEFAULT_PUBLIC_CIBLE.toString())));
    }
}
