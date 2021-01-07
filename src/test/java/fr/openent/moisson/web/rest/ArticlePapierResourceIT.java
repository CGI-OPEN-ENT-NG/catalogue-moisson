package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.ArticlePapier;
import fr.openent.moisson.domain.Tva;
import fr.openent.moisson.domain.Disponibilite;
import fr.openent.moisson.domain.Discipline;
import fr.openent.moisson.domain.Niveau;
import fr.openent.moisson.repository.ArticlePapierRepository;
import fr.openent.moisson.repository.search.ArticlePapierSearchRepository;
import fr.openent.moisson.service.ArticlePapierService;
import fr.openent.moisson.service.dto.ArticlePapierDTO;
import fr.openent.moisson.service.mapper.ArticlePapierMapper;
import fr.openent.moisson.service.dto.ArticlePapierCriteria;
import fr.openent.moisson.service.ArticlePapierQueryService;

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
import java.math.BigDecimal;
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

/**
 * Integration tests for the {@link ArticlePapierResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArticlePapierResourceIT {

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

    private static final String DEFAULT_REFERENCE_EDITEUR = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_EDITEUR = "BBBBBBBBBB";

    private static final String DEFAULT_COLLECTION = "AAAAAAAAAA";
    private static final String UPDATED_COLLECTION = "BBBBBBBBBB";

    private static final String DEFAULT_DISTRIBUTEUR = "AAAAAAAAAA";
    private static final String UPDATED_DISTRIBUTEUR = "BBBBBBBBBB";

    private static final String DEFAULT_URL_COUVERTURE = "AAAAAAAAAA";
    private static final String UPDATED_URL_COUVERTURE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_PARUTION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_PARUTION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_PRIX_HT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX_HT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRIX_HT = new BigDecimal(1 - 1);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ArticlePapierRepository articlePapierRepository;

    @Autowired
    private ArticlePapierMapper articlePapierMapper;

    @Autowired
    private ArticlePapierService articlePapierService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.ArticlePapierSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArticlePapierSearchRepository mockArticlePapierSearchRepository;

    @Autowired
    private ArticlePapierQueryService articlePapierQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArticlePapierMockMvc;

    private ArticlePapier articlePapier;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticlePapier createEntity(EntityManager em) {
        ArticlePapier articlePapier = new ArticlePapier()
            .ean(DEFAULT_EAN)
            .ark(DEFAULT_ARK)
            .titre(DEFAULT_TITRE)
            .editeur(DEFAULT_EDITEUR)
            .auteur(DEFAULT_AUTEUR)
            .referenceEditeur(DEFAULT_REFERENCE_EDITEUR)
            .collection(DEFAULT_COLLECTION)
            .distributeur(DEFAULT_DISTRIBUTEUR)
            .urlCouverture(DEFAULT_URL_COUVERTURE)
            .dateParution(DEFAULT_DATE_PARUTION)
            .prixHT(DEFAULT_PRIX_HT)
            .description(DEFAULT_DESCRIPTION);
        // Add required entity
        Disponibilite disponibilite;
        if (TestUtil.findAll(em, Disponibilite.class).isEmpty()) {
            disponibilite = DisponibiliteResourceIT.createEntity(em);
            em.persist(disponibilite);
            em.flush();
        } else {
            disponibilite = TestUtil.findAll(em, Disponibilite.class).get(0);
        }
        articlePapier.setDisponibilite(disponibilite);
        return articlePapier;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ArticlePapier createUpdatedEntity(EntityManager em) {
        ArticlePapier articlePapier = new ArticlePapier()
            .ean(UPDATED_EAN)
            .ark(UPDATED_ARK)
            .titre(UPDATED_TITRE)
            .editeur(UPDATED_EDITEUR)
            .auteur(UPDATED_AUTEUR)
            .referenceEditeur(UPDATED_REFERENCE_EDITEUR)
            .collection(UPDATED_COLLECTION)
            .distributeur(UPDATED_DISTRIBUTEUR)
            .urlCouverture(UPDATED_URL_COUVERTURE)
            .dateParution(UPDATED_DATE_PARUTION)
            .prixHT(UPDATED_PRIX_HT)
            .description(UPDATED_DESCRIPTION);
        // Add required entity
        Disponibilite disponibilite;
        if (TestUtil.findAll(em, Disponibilite.class).isEmpty()) {
            disponibilite = DisponibiliteResourceIT.createUpdatedEntity(em);
            em.persist(disponibilite);
            em.flush();
        } else {
            disponibilite = TestUtil.findAll(em, Disponibilite.class).get(0);
        }
        articlePapier.setDisponibilite(disponibilite);
        return articlePapier;
    }

    @BeforeEach
    public void initTest() {
        articlePapier = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticlePapier() throws Exception {
        int databaseSizeBeforeCreate = articlePapierRepository.findAll().size();
        // Create the ArticlePapier
        ArticlePapierDTO articlePapierDTO = articlePapierMapper.toDto(articlePapier);
        restArticlePapierMockMvc.perform(post("/api/article-papiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlePapierDTO)))
            .andExpect(status().isCreated());

        // Validate the ArticlePapier in the database
        List<ArticlePapier> articlePapierList = articlePapierRepository.findAll();
        assertThat(articlePapierList).hasSize(databaseSizeBeforeCreate + 1);
        ArticlePapier testArticlePapier = articlePapierList.get(articlePapierList.size() - 1);
        assertThat(testArticlePapier.getEan()).isEqualTo(DEFAULT_EAN);
        assertThat(testArticlePapier.getArk()).isEqualTo(DEFAULT_ARK);
        assertThat(testArticlePapier.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testArticlePapier.getEditeur()).isEqualTo(DEFAULT_EDITEUR);
        assertThat(testArticlePapier.getAuteur()).isEqualTo(DEFAULT_AUTEUR);
        assertThat(testArticlePapier.getReferenceEditeur()).isEqualTo(DEFAULT_REFERENCE_EDITEUR);
        assertThat(testArticlePapier.getCollection()).isEqualTo(DEFAULT_COLLECTION);
        assertThat(testArticlePapier.getDistributeur()).isEqualTo(DEFAULT_DISTRIBUTEUR);
        assertThat(testArticlePapier.getUrlCouverture()).isEqualTo(DEFAULT_URL_COUVERTURE);
        assertThat(testArticlePapier.getDateParution()).isEqualTo(DEFAULT_DATE_PARUTION);
        assertThat(testArticlePapier.getPrixHT()).isEqualTo(DEFAULT_PRIX_HT);
        assertThat(testArticlePapier.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the id for MapsId, the ids must be same
        assertThat(testArticlePapier.getId()).isEqualTo(testArticlePapier.getDisponibilite().getId());

        // Validate the ArticlePapier in Elasticsearch
        verify(mockArticlePapierSearchRepository, times(1)).save(testArticlePapier);
    }

    @Test
    @Transactional
    public void createArticlePapierWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articlePapierRepository.findAll().size();

        // Create the ArticlePapier with an existing ID
        articlePapier.setId(1L);
        ArticlePapierDTO articlePapierDTO = articlePapierMapper.toDto(articlePapier);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticlePapierMockMvc.perform(post("/api/article-papiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlePapierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticlePapier in the database
        List<ArticlePapier> articlePapierList = articlePapierRepository.findAll();
        assertThat(articlePapierList).hasSize(databaseSizeBeforeCreate);

        // Validate the ArticlePapier in Elasticsearch
        verify(mockArticlePapierSearchRepository, times(0)).save(articlePapier);
    }

    @Test
    @Transactional
    public void updateArticlePapierMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);
        int databaseSizeBeforeCreate = articlePapierRepository.findAll().size();


        // Load the articlePapier
        ArticlePapier updatedArticlePapier = articlePapierRepository.findById(articlePapier.getId()).get();
        // Disconnect from session so that the updates on updatedArticlePapier are not directly saved in db
        em.detach(updatedArticlePapier);

        // Update the Disponibilite with new association value
        updatedArticlePapier.setDisponibilite(articlePapier.getDisponibilite());
        ArticlePapierDTO updatedArticlePapierDTO = articlePapierMapper.toDto(updatedArticlePapier);

        // Update the entity
        restArticlePapierMockMvc.perform(put("/api/article-papiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticlePapierDTO)))
            .andExpect(status().isOk());

        // Validate the ArticlePapier in the database
        List<ArticlePapier> articlePapierList = articlePapierRepository.findAll();
        assertThat(articlePapierList).hasSize(databaseSizeBeforeCreate);
        ArticlePapier testArticlePapier = articlePapierList.get(articlePapierList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testArticlePapier.getId()).isEqualTo(testArticlePapier.getDisponibilite().getId());

        // Validate the ArticlePapier in Elasticsearch
        verify(mockArticlePapierSearchRepository, times(1)).save(articlePapier);
    }

    @Test
    @Transactional
    public void getAllArticlePapiers() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList
        restArticlePapierMockMvc.perform(get("/api/article-papiers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articlePapier.getId().intValue())))
            .andExpect(jsonPath("$.[*].ean").value(hasItem(DEFAULT_EAN)))
            .andExpect(jsonPath("$.[*].ark").value(hasItem(DEFAULT_ARK)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].editeur").value(hasItem(DEFAULT_EDITEUR)))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR)))
            .andExpect(jsonPath("$.[*].referenceEditeur").value(hasItem(DEFAULT_REFERENCE_EDITEUR)))
            .andExpect(jsonPath("$.[*].collection").value(hasItem(DEFAULT_COLLECTION)))
            .andExpect(jsonPath("$.[*].distributeur").value(hasItem(DEFAULT_DISTRIBUTEUR)))
            .andExpect(jsonPath("$.[*].urlCouverture").value(hasItem(DEFAULT_URL_COUVERTURE)))
            .andExpect(jsonPath("$.[*].dateParution").value(hasItem(DEFAULT_DATE_PARUTION.toString())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void getArticlePapier() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get the articlePapier
        restArticlePapierMockMvc.perform(get("/api/article-papiers/{id}", articlePapier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(articlePapier.getId().intValue()))
            .andExpect(jsonPath("$.ean").value(DEFAULT_EAN))
            .andExpect(jsonPath("$.ark").value(DEFAULT_ARK))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.editeur").value(DEFAULT_EDITEUR))
            .andExpect(jsonPath("$.auteur").value(DEFAULT_AUTEUR))
            .andExpect(jsonPath("$.referenceEditeur").value(DEFAULT_REFERENCE_EDITEUR))
            .andExpect(jsonPath("$.collection").value(DEFAULT_COLLECTION))
            .andExpect(jsonPath("$.distributeur").value(DEFAULT_DISTRIBUTEUR))
            .andExpect(jsonPath("$.urlCouverture").value(DEFAULT_URL_COUVERTURE))
            .andExpect(jsonPath("$.dateParution").value(DEFAULT_DATE_PARUTION.toString()))
            .andExpect(jsonPath("$.prixHT").value(DEFAULT_PRIX_HT.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getArticlePapiersByIdFiltering() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        Long id = articlePapier.getId();

        defaultArticlePapierShouldBeFound("id.equals=" + id);
        defaultArticlePapierShouldNotBeFound("id.notEquals=" + id);

        defaultArticlePapierShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultArticlePapierShouldNotBeFound("id.greaterThan=" + id);

        defaultArticlePapierShouldBeFound("id.lessThanOrEqual=" + id);
        defaultArticlePapierShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByEanIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ean equals to DEFAULT_EAN
        defaultArticlePapierShouldBeFound("ean.equals=" + DEFAULT_EAN);

        // Get all the articlePapierList where ean equals to UPDATED_EAN
        defaultArticlePapierShouldNotBeFound("ean.equals=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByEanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ean not equals to DEFAULT_EAN
        defaultArticlePapierShouldNotBeFound("ean.notEquals=" + DEFAULT_EAN);

        // Get all the articlePapierList where ean not equals to UPDATED_EAN
        defaultArticlePapierShouldBeFound("ean.notEquals=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByEanIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ean in DEFAULT_EAN or UPDATED_EAN
        defaultArticlePapierShouldBeFound("ean.in=" + DEFAULT_EAN + "," + UPDATED_EAN);

        // Get all the articlePapierList where ean equals to UPDATED_EAN
        defaultArticlePapierShouldNotBeFound("ean.in=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByEanIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ean is not null
        defaultArticlePapierShouldBeFound("ean.specified=true");

        // Get all the articlePapierList where ean is null
        defaultArticlePapierShouldNotBeFound("ean.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticlePapiersByEanContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ean contains DEFAULT_EAN
        defaultArticlePapierShouldBeFound("ean.contains=" + DEFAULT_EAN);

        // Get all the articlePapierList where ean contains UPDATED_EAN
        defaultArticlePapierShouldNotBeFound("ean.contains=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByEanNotContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ean does not contain DEFAULT_EAN
        defaultArticlePapierShouldNotBeFound("ean.doesNotContain=" + DEFAULT_EAN);

        // Get all the articlePapierList where ean does not contain UPDATED_EAN
        defaultArticlePapierShouldBeFound("ean.doesNotContain=" + UPDATED_EAN);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByArkIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ark equals to DEFAULT_ARK
        defaultArticlePapierShouldBeFound("ark.equals=" + DEFAULT_ARK);

        // Get all the articlePapierList where ark equals to UPDATED_ARK
        defaultArticlePapierShouldNotBeFound("ark.equals=" + UPDATED_ARK);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByArkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ark not equals to DEFAULT_ARK
        defaultArticlePapierShouldNotBeFound("ark.notEquals=" + DEFAULT_ARK);

        // Get all the articlePapierList where ark not equals to UPDATED_ARK
        defaultArticlePapierShouldBeFound("ark.notEquals=" + UPDATED_ARK);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByArkIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ark in DEFAULT_ARK or UPDATED_ARK
        defaultArticlePapierShouldBeFound("ark.in=" + DEFAULT_ARK + "," + UPDATED_ARK);

        // Get all the articlePapierList where ark equals to UPDATED_ARK
        defaultArticlePapierShouldNotBeFound("ark.in=" + UPDATED_ARK);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByArkIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ark is not null
        defaultArticlePapierShouldBeFound("ark.specified=true");

        // Get all the articlePapierList where ark is null
        defaultArticlePapierShouldNotBeFound("ark.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticlePapiersByArkContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ark contains DEFAULT_ARK
        defaultArticlePapierShouldBeFound("ark.contains=" + DEFAULT_ARK);

        // Get all the articlePapierList where ark contains UPDATED_ARK
        defaultArticlePapierShouldNotBeFound("ark.contains=" + UPDATED_ARK);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByArkNotContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where ark does not contain DEFAULT_ARK
        defaultArticlePapierShouldNotBeFound("ark.doesNotContain=" + DEFAULT_ARK);

        // Get all the articlePapierList where ark does not contain UPDATED_ARK
        defaultArticlePapierShouldBeFound("ark.doesNotContain=" + UPDATED_ARK);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByTitreIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where titre equals to DEFAULT_TITRE
        defaultArticlePapierShouldBeFound("titre.equals=" + DEFAULT_TITRE);

        // Get all the articlePapierList where titre equals to UPDATED_TITRE
        defaultArticlePapierShouldNotBeFound("titre.equals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByTitreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where titre not equals to DEFAULT_TITRE
        defaultArticlePapierShouldNotBeFound("titre.notEquals=" + DEFAULT_TITRE);

        // Get all the articlePapierList where titre not equals to UPDATED_TITRE
        defaultArticlePapierShouldBeFound("titre.notEquals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByTitreIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where titre in DEFAULT_TITRE or UPDATED_TITRE
        defaultArticlePapierShouldBeFound("titre.in=" + DEFAULT_TITRE + "," + UPDATED_TITRE);

        // Get all the articlePapierList where titre equals to UPDATED_TITRE
        defaultArticlePapierShouldNotBeFound("titre.in=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByTitreIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where titre is not null
        defaultArticlePapierShouldBeFound("titre.specified=true");

        // Get all the articlePapierList where titre is null
        defaultArticlePapierShouldNotBeFound("titre.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticlePapiersByTitreContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where titre contains DEFAULT_TITRE
        defaultArticlePapierShouldBeFound("titre.contains=" + DEFAULT_TITRE);

        // Get all the articlePapierList where titre contains UPDATED_TITRE
        defaultArticlePapierShouldNotBeFound("titre.contains=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByTitreNotContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where titre does not contain DEFAULT_TITRE
        defaultArticlePapierShouldNotBeFound("titre.doesNotContain=" + DEFAULT_TITRE);

        // Get all the articlePapierList where titre does not contain UPDATED_TITRE
        defaultArticlePapierShouldBeFound("titre.doesNotContain=" + UPDATED_TITRE);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByEditeurIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where editeur equals to DEFAULT_EDITEUR
        defaultArticlePapierShouldBeFound("editeur.equals=" + DEFAULT_EDITEUR);

        // Get all the articlePapierList where editeur equals to UPDATED_EDITEUR
        defaultArticlePapierShouldNotBeFound("editeur.equals=" + UPDATED_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByEditeurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where editeur not equals to DEFAULT_EDITEUR
        defaultArticlePapierShouldNotBeFound("editeur.notEquals=" + DEFAULT_EDITEUR);

        // Get all the articlePapierList where editeur not equals to UPDATED_EDITEUR
        defaultArticlePapierShouldBeFound("editeur.notEquals=" + UPDATED_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByEditeurIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where editeur in DEFAULT_EDITEUR or UPDATED_EDITEUR
        defaultArticlePapierShouldBeFound("editeur.in=" + DEFAULT_EDITEUR + "," + UPDATED_EDITEUR);

        // Get all the articlePapierList where editeur equals to UPDATED_EDITEUR
        defaultArticlePapierShouldNotBeFound("editeur.in=" + UPDATED_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByEditeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where editeur is not null
        defaultArticlePapierShouldBeFound("editeur.specified=true");

        // Get all the articlePapierList where editeur is null
        defaultArticlePapierShouldNotBeFound("editeur.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticlePapiersByEditeurContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where editeur contains DEFAULT_EDITEUR
        defaultArticlePapierShouldBeFound("editeur.contains=" + DEFAULT_EDITEUR);

        // Get all the articlePapierList where editeur contains UPDATED_EDITEUR
        defaultArticlePapierShouldNotBeFound("editeur.contains=" + UPDATED_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByEditeurNotContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where editeur does not contain DEFAULT_EDITEUR
        defaultArticlePapierShouldNotBeFound("editeur.doesNotContain=" + DEFAULT_EDITEUR);

        // Get all the articlePapierList where editeur does not contain UPDATED_EDITEUR
        defaultArticlePapierShouldBeFound("editeur.doesNotContain=" + UPDATED_EDITEUR);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByAuteurIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where auteur equals to DEFAULT_AUTEUR
        defaultArticlePapierShouldBeFound("auteur.equals=" + DEFAULT_AUTEUR);

        // Get all the articlePapierList where auteur equals to UPDATED_AUTEUR
        defaultArticlePapierShouldNotBeFound("auteur.equals=" + UPDATED_AUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByAuteurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where auteur not equals to DEFAULT_AUTEUR
        defaultArticlePapierShouldNotBeFound("auteur.notEquals=" + DEFAULT_AUTEUR);

        // Get all the articlePapierList where auteur not equals to UPDATED_AUTEUR
        defaultArticlePapierShouldBeFound("auteur.notEquals=" + UPDATED_AUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByAuteurIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where auteur in DEFAULT_AUTEUR or UPDATED_AUTEUR
        defaultArticlePapierShouldBeFound("auteur.in=" + DEFAULT_AUTEUR + "," + UPDATED_AUTEUR);

        // Get all the articlePapierList where auteur equals to UPDATED_AUTEUR
        defaultArticlePapierShouldNotBeFound("auteur.in=" + UPDATED_AUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByAuteurIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where auteur is not null
        defaultArticlePapierShouldBeFound("auteur.specified=true");

        // Get all the articlePapierList where auteur is null
        defaultArticlePapierShouldNotBeFound("auteur.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticlePapiersByAuteurContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where auteur contains DEFAULT_AUTEUR
        defaultArticlePapierShouldBeFound("auteur.contains=" + DEFAULT_AUTEUR);

        // Get all the articlePapierList where auteur contains UPDATED_AUTEUR
        defaultArticlePapierShouldNotBeFound("auteur.contains=" + UPDATED_AUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByAuteurNotContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where auteur does not contain DEFAULT_AUTEUR
        defaultArticlePapierShouldNotBeFound("auteur.doesNotContain=" + DEFAULT_AUTEUR);

        // Get all the articlePapierList where auteur does not contain UPDATED_AUTEUR
        defaultArticlePapierShouldBeFound("auteur.doesNotContain=" + UPDATED_AUTEUR);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByReferenceEditeurIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where referenceEditeur equals to DEFAULT_REFERENCE_EDITEUR
        defaultArticlePapierShouldBeFound("referenceEditeur.equals=" + DEFAULT_REFERENCE_EDITEUR);

        // Get all the articlePapierList where referenceEditeur equals to UPDATED_REFERENCE_EDITEUR
        defaultArticlePapierShouldNotBeFound("referenceEditeur.equals=" + UPDATED_REFERENCE_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByReferenceEditeurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where referenceEditeur not equals to DEFAULT_REFERENCE_EDITEUR
        defaultArticlePapierShouldNotBeFound("referenceEditeur.notEquals=" + DEFAULT_REFERENCE_EDITEUR);

        // Get all the articlePapierList where referenceEditeur not equals to UPDATED_REFERENCE_EDITEUR
        defaultArticlePapierShouldBeFound("referenceEditeur.notEquals=" + UPDATED_REFERENCE_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByReferenceEditeurIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where referenceEditeur in DEFAULT_REFERENCE_EDITEUR or UPDATED_REFERENCE_EDITEUR
        defaultArticlePapierShouldBeFound("referenceEditeur.in=" + DEFAULT_REFERENCE_EDITEUR + "," + UPDATED_REFERENCE_EDITEUR);

        // Get all the articlePapierList where referenceEditeur equals to UPDATED_REFERENCE_EDITEUR
        defaultArticlePapierShouldNotBeFound("referenceEditeur.in=" + UPDATED_REFERENCE_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByReferenceEditeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where referenceEditeur is not null
        defaultArticlePapierShouldBeFound("referenceEditeur.specified=true");

        // Get all the articlePapierList where referenceEditeur is null
        defaultArticlePapierShouldNotBeFound("referenceEditeur.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticlePapiersByReferenceEditeurContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where referenceEditeur contains DEFAULT_REFERENCE_EDITEUR
        defaultArticlePapierShouldBeFound("referenceEditeur.contains=" + DEFAULT_REFERENCE_EDITEUR);

        // Get all the articlePapierList where referenceEditeur contains UPDATED_REFERENCE_EDITEUR
        defaultArticlePapierShouldNotBeFound("referenceEditeur.contains=" + UPDATED_REFERENCE_EDITEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByReferenceEditeurNotContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where referenceEditeur does not contain DEFAULT_REFERENCE_EDITEUR
        defaultArticlePapierShouldNotBeFound("referenceEditeur.doesNotContain=" + DEFAULT_REFERENCE_EDITEUR);

        // Get all the articlePapierList where referenceEditeur does not contain UPDATED_REFERENCE_EDITEUR
        defaultArticlePapierShouldBeFound("referenceEditeur.doesNotContain=" + UPDATED_REFERENCE_EDITEUR);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByCollectionIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where collection equals to DEFAULT_COLLECTION
        defaultArticlePapierShouldBeFound("collection.equals=" + DEFAULT_COLLECTION);

        // Get all the articlePapierList where collection equals to UPDATED_COLLECTION
        defaultArticlePapierShouldNotBeFound("collection.equals=" + UPDATED_COLLECTION);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByCollectionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where collection not equals to DEFAULT_COLLECTION
        defaultArticlePapierShouldNotBeFound("collection.notEquals=" + DEFAULT_COLLECTION);

        // Get all the articlePapierList where collection not equals to UPDATED_COLLECTION
        defaultArticlePapierShouldBeFound("collection.notEquals=" + UPDATED_COLLECTION);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByCollectionIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where collection in DEFAULT_COLLECTION or UPDATED_COLLECTION
        defaultArticlePapierShouldBeFound("collection.in=" + DEFAULT_COLLECTION + "," + UPDATED_COLLECTION);

        // Get all the articlePapierList where collection equals to UPDATED_COLLECTION
        defaultArticlePapierShouldNotBeFound("collection.in=" + UPDATED_COLLECTION);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByCollectionIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where collection is not null
        defaultArticlePapierShouldBeFound("collection.specified=true");

        // Get all the articlePapierList where collection is null
        defaultArticlePapierShouldNotBeFound("collection.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticlePapiersByCollectionContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where collection contains DEFAULT_COLLECTION
        defaultArticlePapierShouldBeFound("collection.contains=" + DEFAULT_COLLECTION);

        // Get all the articlePapierList where collection contains UPDATED_COLLECTION
        defaultArticlePapierShouldNotBeFound("collection.contains=" + UPDATED_COLLECTION);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByCollectionNotContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where collection does not contain DEFAULT_COLLECTION
        defaultArticlePapierShouldNotBeFound("collection.doesNotContain=" + DEFAULT_COLLECTION);

        // Get all the articlePapierList where collection does not contain UPDATED_COLLECTION
        defaultArticlePapierShouldBeFound("collection.doesNotContain=" + UPDATED_COLLECTION);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByDistributeurIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where distributeur equals to DEFAULT_DISTRIBUTEUR
        defaultArticlePapierShouldBeFound("distributeur.equals=" + DEFAULT_DISTRIBUTEUR);

        // Get all the articlePapierList where distributeur equals to UPDATED_DISTRIBUTEUR
        defaultArticlePapierShouldNotBeFound("distributeur.equals=" + UPDATED_DISTRIBUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByDistributeurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where distributeur not equals to DEFAULT_DISTRIBUTEUR
        defaultArticlePapierShouldNotBeFound("distributeur.notEquals=" + DEFAULT_DISTRIBUTEUR);

        // Get all the articlePapierList where distributeur not equals to UPDATED_DISTRIBUTEUR
        defaultArticlePapierShouldBeFound("distributeur.notEquals=" + UPDATED_DISTRIBUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByDistributeurIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where distributeur in DEFAULT_DISTRIBUTEUR or UPDATED_DISTRIBUTEUR
        defaultArticlePapierShouldBeFound("distributeur.in=" + DEFAULT_DISTRIBUTEUR + "," + UPDATED_DISTRIBUTEUR);

        // Get all the articlePapierList where distributeur equals to UPDATED_DISTRIBUTEUR
        defaultArticlePapierShouldNotBeFound("distributeur.in=" + UPDATED_DISTRIBUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByDistributeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where distributeur is not null
        defaultArticlePapierShouldBeFound("distributeur.specified=true");

        // Get all the articlePapierList where distributeur is null
        defaultArticlePapierShouldNotBeFound("distributeur.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticlePapiersByDistributeurContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where distributeur contains DEFAULT_DISTRIBUTEUR
        defaultArticlePapierShouldBeFound("distributeur.contains=" + DEFAULT_DISTRIBUTEUR);

        // Get all the articlePapierList where distributeur contains UPDATED_DISTRIBUTEUR
        defaultArticlePapierShouldNotBeFound("distributeur.contains=" + UPDATED_DISTRIBUTEUR);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByDistributeurNotContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where distributeur does not contain DEFAULT_DISTRIBUTEUR
        defaultArticlePapierShouldNotBeFound("distributeur.doesNotContain=" + DEFAULT_DISTRIBUTEUR);

        // Get all the articlePapierList where distributeur does not contain UPDATED_DISTRIBUTEUR
        defaultArticlePapierShouldBeFound("distributeur.doesNotContain=" + UPDATED_DISTRIBUTEUR);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByUrlCouvertureIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where urlCouverture equals to DEFAULT_URL_COUVERTURE
        defaultArticlePapierShouldBeFound("urlCouverture.equals=" + DEFAULT_URL_COUVERTURE);

        // Get all the articlePapierList where urlCouverture equals to UPDATED_URL_COUVERTURE
        defaultArticlePapierShouldNotBeFound("urlCouverture.equals=" + UPDATED_URL_COUVERTURE);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByUrlCouvertureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where urlCouverture not equals to DEFAULT_URL_COUVERTURE
        defaultArticlePapierShouldNotBeFound("urlCouverture.notEquals=" + DEFAULT_URL_COUVERTURE);

        // Get all the articlePapierList where urlCouverture not equals to UPDATED_URL_COUVERTURE
        defaultArticlePapierShouldBeFound("urlCouverture.notEquals=" + UPDATED_URL_COUVERTURE);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByUrlCouvertureIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where urlCouverture in DEFAULT_URL_COUVERTURE or UPDATED_URL_COUVERTURE
        defaultArticlePapierShouldBeFound("urlCouverture.in=" + DEFAULT_URL_COUVERTURE + "," + UPDATED_URL_COUVERTURE);

        // Get all the articlePapierList where urlCouverture equals to UPDATED_URL_COUVERTURE
        defaultArticlePapierShouldNotBeFound("urlCouverture.in=" + UPDATED_URL_COUVERTURE);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByUrlCouvertureIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where urlCouverture is not null
        defaultArticlePapierShouldBeFound("urlCouverture.specified=true");

        // Get all the articlePapierList where urlCouverture is null
        defaultArticlePapierShouldNotBeFound("urlCouverture.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticlePapiersByUrlCouvertureContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where urlCouverture contains DEFAULT_URL_COUVERTURE
        defaultArticlePapierShouldBeFound("urlCouverture.contains=" + DEFAULT_URL_COUVERTURE);

        // Get all the articlePapierList where urlCouverture contains UPDATED_URL_COUVERTURE
        defaultArticlePapierShouldNotBeFound("urlCouverture.contains=" + UPDATED_URL_COUVERTURE);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByUrlCouvertureNotContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where urlCouverture does not contain DEFAULT_URL_COUVERTURE
        defaultArticlePapierShouldNotBeFound("urlCouverture.doesNotContain=" + DEFAULT_URL_COUVERTURE);

        // Get all the articlePapierList where urlCouverture does not contain UPDATED_URL_COUVERTURE
        defaultArticlePapierShouldBeFound("urlCouverture.doesNotContain=" + UPDATED_URL_COUVERTURE);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByDateParutionIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where dateParution equals to DEFAULT_DATE_PARUTION
        defaultArticlePapierShouldBeFound("dateParution.equals=" + DEFAULT_DATE_PARUTION);

        // Get all the articlePapierList where dateParution equals to UPDATED_DATE_PARUTION
        defaultArticlePapierShouldNotBeFound("dateParution.equals=" + UPDATED_DATE_PARUTION);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByDateParutionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where dateParution not equals to DEFAULT_DATE_PARUTION
        defaultArticlePapierShouldNotBeFound("dateParution.notEquals=" + DEFAULT_DATE_PARUTION);

        // Get all the articlePapierList where dateParution not equals to UPDATED_DATE_PARUTION
        defaultArticlePapierShouldBeFound("dateParution.notEquals=" + UPDATED_DATE_PARUTION);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByDateParutionIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where dateParution in DEFAULT_DATE_PARUTION or UPDATED_DATE_PARUTION
        defaultArticlePapierShouldBeFound("dateParution.in=" + DEFAULT_DATE_PARUTION + "," + UPDATED_DATE_PARUTION);

        // Get all the articlePapierList where dateParution equals to UPDATED_DATE_PARUTION
        defaultArticlePapierShouldNotBeFound("dateParution.in=" + UPDATED_DATE_PARUTION);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByDateParutionIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where dateParution is not null
        defaultArticlePapierShouldBeFound("dateParution.specified=true");

        // Get all the articlePapierList where dateParution is null
        defaultArticlePapierShouldNotBeFound("dateParution.specified=false");
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByPrixHTIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where prixHT equals to DEFAULT_PRIX_HT
        defaultArticlePapierShouldBeFound("prixHT.equals=" + DEFAULT_PRIX_HT);

        // Get all the articlePapierList where prixHT equals to UPDATED_PRIX_HT
        defaultArticlePapierShouldNotBeFound("prixHT.equals=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByPrixHTIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where prixHT not equals to DEFAULT_PRIX_HT
        defaultArticlePapierShouldNotBeFound("prixHT.notEquals=" + DEFAULT_PRIX_HT);

        // Get all the articlePapierList where prixHT not equals to UPDATED_PRIX_HT
        defaultArticlePapierShouldBeFound("prixHT.notEquals=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByPrixHTIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where prixHT in DEFAULT_PRIX_HT or UPDATED_PRIX_HT
        defaultArticlePapierShouldBeFound("prixHT.in=" + DEFAULT_PRIX_HT + "," + UPDATED_PRIX_HT);

        // Get all the articlePapierList where prixHT equals to UPDATED_PRIX_HT
        defaultArticlePapierShouldNotBeFound("prixHT.in=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByPrixHTIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where prixHT is not null
        defaultArticlePapierShouldBeFound("prixHT.specified=true");

        // Get all the articlePapierList where prixHT is null
        defaultArticlePapierShouldNotBeFound("prixHT.specified=false");
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByPrixHTIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where prixHT is greater than or equal to DEFAULT_PRIX_HT
        defaultArticlePapierShouldBeFound("prixHT.greaterThanOrEqual=" + DEFAULT_PRIX_HT);

        // Get all the articlePapierList where prixHT is greater than or equal to UPDATED_PRIX_HT
        defaultArticlePapierShouldNotBeFound("prixHT.greaterThanOrEqual=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByPrixHTIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where prixHT is less than or equal to DEFAULT_PRIX_HT
        defaultArticlePapierShouldBeFound("prixHT.lessThanOrEqual=" + DEFAULT_PRIX_HT);

        // Get all the articlePapierList where prixHT is less than or equal to SMALLER_PRIX_HT
        defaultArticlePapierShouldNotBeFound("prixHT.lessThanOrEqual=" + SMALLER_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByPrixHTIsLessThanSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where prixHT is less than DEFAULT_PRIX_HT
        defaultArticlePapierShouldNotBeFound("prixHT.lessThan=" + DEFAULT_PRIX_HT);

        // Get all the articlePapierList where prixHT is less than UPDATED_PRIX_HT
        defaultArticlePapierShouldBeFound("prixHT.lessThan=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByPrixHTIsGreaterThanSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where prixHT is greater than DEFAULT_PRIX_HT
        defaultArticlePapierShouldNotBeFound("prixHT.greaterThan=" + DEFAULT_PRIX_HT);

        // Get all the articlePapierList where prixHT is greater than SMALLER_PRIX_HT
        defaultArticlePapierShouldBeFound("prixHT.greaterThan=" + SMALLER_PRIX_HT);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where description equals to DEFAULT_DESCRIPTION
        defaultArticlePapierShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the articlePapierList where description equals to UPDATED_DESCRIPTION
        defaultArticlePapierShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where description not equals to DEFAULT_DESCRIPTION
        defaultArticlePapierShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the articlePapierList where description not equals to UPDATED_DESCRIPTION
        defaultArticlePapierShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultArticlePapierShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the articlePapierList where description equals to UPDATED_DESCRIPTION
        defaultArticlePapierShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where description is not null
        defaultArticlePapierShouldBeFound("description.specified=true");

        // Get all the articlePapierList where description is null
        defaultArticlePapierShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllArticlePapiersByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where description contains DEFAULT_DESCRIPTION
        defaultArticlePapierShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the articlePapierList where description contains UPDATED_DESCRIPTION
        defaultArticlePapierShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllArticlePapiersByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        // Get all the articlePapierList where description does not contain DEFAULT_DESCRIPTION
        defaultArticlePapierShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the articlePapierList where description does not contain UPDATED_DESCRIPTION
        defaultArticlePapierShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByTvaIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);
        Tva tva = TvaResourceIT.createEntity(em);
        em.persist(tva);
        em.flush();
        articlePapier.addTva(tva);
        articlePapierRepository.saveAndFlush(articlePapier);
        Long tvaId = tva.getId();

        // Get all the articlePapierList where tva equals to tvaId
        defaultArticlePapierShouldBeFound("tvaId.equals=" + tvaId);

        // Get all the articlePapierList where tva equals to tvaId + 1
        defaultArticlePapierShouldNotBeFound("tvaId.equals=" + (tvaId + 1));
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByDisponibiliteIsEqualToSomething() throws Exception {
        // Get already existing entity
        Disponibilite disponibilite = articlePapier.getDisponibilite();
        articlePapierRepository.saveAndFlush(articlePapier);
        Long disponibiliteId = disponibilite.getId();

        // Get all the articlePapierList where disponibilite equals to disponibiliteId
        defaultArticlePapierShouldBeFound("disponibiliteId.equals=" + disponibiliteId);

        // Get all the articlePapierList where disponibilite equals to disponibiliteId + 1
        defaultArticlePapierShouldNotBeFound("disponibiliteId.equals=" + (disponibiliteId + 1));
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByDisciplineIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);
        Discipline discipline = DisciplineResourceIT.createEntity(em);
        em.persist(discipline);
        em.flush();
        articlePapier.addDiscipline(discipline);
        articlePapierRepository.saveAndFlush(articlePapier);
        Long disciplineId = discipline.getId();

        // Get all the articlePapierList where discipline equals to disciplineId
        defaultArticlePapierShouldBeFound("disciplineId.equals=" + disciplineId);

        // Get all the articlePapierList where discipline equals to disciplineId + 1
        defaultArticlePapierShouldNotBeFound("disciplineId.equals=" + (disciplineId + 1));
    }


    @Test
    @Transactional
    public void getAllArticlePapiersByNiveauIsEqualToSomething() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);
        Niveau niveau = NiveauResourceIT.createEntity(em);
        em.persist(niveau);
        em.flush();
        articlePapier.addNiveau(niveau);
        articlePapierRepository.saveAndFlush(articlePapier);
        Long niveauId = niveau.getId();

        // Get all the articlePapierList where niveau equals to niveauId
        defaultArticlePapierShouldBeFound("niveauId.equals=" + niveauId);

        // Get all the articlePapierList where niveau equals to niveauId + 1
        defaultArticlePapierShouldNotBeFound("niveauId.equals=" + (niveauId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultArticlePapierShouldBeFound(String filter) throws Exception {
        restArticlePapierMockMvc.perform(get("/api/article-papiers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articlePapier.getId().intValue())))
            .andExpect(jsonPath("$.[*].ean").value(hasItem(DEFAULT_EAN)))
            .andExpect(jsonPath("$.[*].ark").value(hasItem(DEFAULT_ARK)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].editeur").value(hasItem(DEFAULT_EDITEUR)))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR)))
            .andExpect(jsonPath("$.[*].referenceEditeur").value(hasItem(DEFAULT_REFERENCE_EDITEUR)))
            .andExpect(jsonPath("$.[*].collection").value(hasItem(DEFAULT_COLLECTION)))
            .andExpect(jsonPath("$.[*].distributeur").value(hasItem(DEFAULT_DISTRIBUTEUR)))
            .andExpect(jsonPath("$.[*].urlCouverture").value(hasItem(DEFAULT_URL_COUVERTURE)))
            .andExpect(jsonPath("$.[*].dateParution").value(hasItem(DEFAULT_DATE_PARUTION.toString())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restArticlePapierMockMvc.perform(get("/api/article-papiers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultArticlePapierShouldNotBeFound(String filter) throws Exception {
        restArticlePapierMockMvc.perform(get("/api/article-papiers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restArticlePapierMockMvc.perform(get("/api/article-papiers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingArticlePapier() throws Exception {
        // Get the articlePapier
        restArticlePapierMockMvc.perform(get("/api/article-papiers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticlePapier() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        int databaseSizeBeforeUpdate = articlePapierRepository.findAll().size();

        // Update the articlePapier
        ArticlePapier updatedArticlePapier = articlePapierRepository.findById(articlePapier.getId()).get();
        // Disconnect from session so that the updates on updatedArticlePapier are not directly saved in db
        em.detach(updatedArticlePapier);
        updatedArticlePapier
            .ean(UPDATED_EAN)
            .ark(UPDATED_ARK)
            .titre(UPDATED_TITRE)
            .editeur(UPDATED_EDITEUR)
            .auteur(UPDATED_AUTEUR)
            .referenceEditeur(UPDATED_REFERENCE_EDITEUR)
            .collection(UPDATED_COLLECTION)
            .distributeur(UPDATED_DISTRIBUTEUR)
            .urlCouverture(UPDATED_URL_COUVERTURE)
            .dateParution(UPDATED_DATE_PARUTION)
            .prixHT(UPDATED_PRIX_HT)
            .description(UPDATED_DESCRIPTION);
        ArticlePapierDTO articlePapierDTO = articlePapierMapper.toDto(updatedArticlePapier);

        restArticlePapierMockMvc.perform(put("/api/article-papiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlePapierDTO)))
            .andExpect(status().isOk());

        // Validate the ArticlePapier in the database
        List<ArticlePapier> articlePapierList = articlePapierRepository.findAll();
        assertThat(articlePapierList).hasSize(databaseSizeBeforeUpdate);
        ArticlePapier testArticlePapier = articlePapierList.get(articlePapierList.size() - 1);
        assertThat(testArticlePapier.getEan()).isEqualTo(UPDATED_EAN);
        assertThat(testArticlePapier.getArk()).isEqualTo(UPDATED_ARK);
        assertThat(testArticlePapier.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testArticlePapier.getEditeur()).isEqualTo(UPDATED_EDITEUR);
        assertThat(testArticlePapier.getAuteur()).isEqualTo(UPDATED_AUTEUR);
        assertThat(testArticlePapier.getReferenceEditeur()).isEqualTo(UPDATED_REFERENCE_EDITEUR);
        assertThat(testArticlePapier.getCollection()).isEqualTo(UPDATED_COLLECTION);
        assertThat(testArticlePapier.getDistributeur()).isEqualTo(UPDATED_DISTRIBUTEUR);
        assertThat(testArticlePapier.getUrlCouverture()).isEqualTo(UPDATED_URL_COUVERTURE);
        assertThat(testArticlePapier.getDateParution()).isEqualTo(UPDATED_DATE_PARUTION);
        assertThat(testArticlePapier.getPrixHT()).isEqualTo(UPDATED_PRIX_HT);
        assertThat(testArticlePapier.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the ArticlePapier in Elasticsearch
        verify(mockArticlePapierSearchRepository, times(1)).save(testArticlePapier);
    }

    @Test
    @Transactional
    public void updateNonExistingArticlePapier() throws Exception {
        int databaseSizeBeforeUpdate = articlePapierRepository.findAll().size();

        // Create the ArticlePapier
        ArticlePapierDTO articlePapierDTO = articlePapierMapper.toDto(articlePapier);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticlePapierMockMvc.perform(put("/api/article-papiers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(articlePapierDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ArticlePapier in the database
        List<ArticlePapier> articlePapierList = articlePapierRepository.findAll();
        assertThat(articlePapierList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ArticlePapier in Elasticsearch
        verify(mockArticlePapierSearchRepository, times(0)).save(articlePapier);
    }

    @Test
    @Transactional
    public void deleteArticlePapier() throws Exception {
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);

        int databaseSizeBeforeDelete = articlePapierRepository.findAll().size();

        // Delete the articlePapier
        restArticlePapierMockMvc.perform(delete("/api/article-papiers/{id}", articlePapier.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ArticlePapier> articlePapierList = articlePapierRepository.findAll();
        assertThat(articlePapierList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ArticlePapier in Elasticsearch
        verify(mockArticlePapierSearchRepository, times(1)).deleteById(articlePapier.getId());
    }

    @Test
    @Transactional
    public void searchArticlePapier() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        articlePapierRepository.saveAndFlush(articlePapier);
        when(mockArticlePapierSearchRepository.search(queryStringQuery("id:" + articlePapier.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(articlePapier), PageRequest.of(0, 1), 1));

        // Search the articlePapier
        restArticlePapierMockMvc.perform(get("/api/_search/article-papiers?query=id:" + articlePapier.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articlePapier.getId().intValue())))
            .andExpect(jsonPath("$.[*].ean").value(hasItem(DEFAULT_EAN)))
            .andExpect(jsonPath("$.[*].ark").value(hasItem(DEFAULT_ARK)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].editeur").value(hasItem(DEFAULT_EDITEUR)))
            .andExpect(jsonPath("$.[*].auteur").value(hasItem(DEFAULT_AUTEUR)))
            .andExpect(jsonPath("$.[*].referenceEditeur").value(hasItem(DEFAULT_REFERENCE_EDITEUR)))
            .andExpect(jsonPath("$.[*].collection").value(hasItem(DEFAULT_COLLECTION)))
            .andExpect(jsonPath("$.[*].distributeur").value(hasItem(DEFAULT_DISTRIBUTEUR)))
            .andExpect(jsonPath("$.[*].urlCouverture").value(hasItem(DEFAULT_URL_COUVERTURE)))
            .andExpect(jsonPath("$.[*].dateParution").value(hasItem(DEFAULT_DATE_PARUTION.toString())))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
}
