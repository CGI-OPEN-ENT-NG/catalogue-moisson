package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.ArticleNumerique;
import fr.openent.moisson.domain.ArticlePapier;
import fr.openent.moisson.domain.Niveau;
import fr.openent.moisson.repository.NiveauRepository;
import fr.openent.moisson.repository.search.NiveauSearchRepository;
import fr.openent.moisson.service.NiveauQueryService;
import fr.openent.moisson.service.NiveauService;
import fr.openent.moisson.service.dto.NiveauDTO;
import fr.openent.moisson.service.mapper.NiveauMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

/**
 * Integration tests for the {@link NiveauResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class NiveauResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_TERME = "AAAAAAAAAA";
    private static final String UPDATED_TERME = "BBBBBBBBBB";

    private static final String DEFAULT_CONCEPT = "AAAAAAAAAA";
    private static final String UPDATED_CONCEPT = "BBBBBBBBBB";

    @Autowired
    private NiveauRepository niveauRepository;

    @Autowired
    private NiveauMapper niveauMapper;

    @Autowired
    private NiveauService niveauService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.NiveauSearchRepositoryMockConfiguration
     */
    @Autowired
    private NiveauSearchRepository mockNiveauSearchRepository;

    @Autowired
    private NiveauQueryService niveauQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNiveauMockMvc;

    private Niveau niveau;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Niveau createEntity(EntityManager em) {
        Niveau niveau = new Niveau()
            .libelle(DEFAULT_LIBELLE)
            .terme(DEFAULT_TERME)
            .concept(DEFAULT_CONCEPT);
        return niveau;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Niveau createUpdatedEntity(EntityManager em) {
        Niveau niveau = new Niveau()
            .libelle(UPDATED_LIBELLE)
            .terme(UPDATED_TERME)
            .concept(UPDATED_CONCEPT);
        return niveau;
    }

    @BeforeEach
    public void initTest() {
        niveau = createEntity(em);
    }

    @Test
    @Transactional
    public void createNiveau() throws Exception {
        int databaseSizeBeforeCreate = niveauRepository.findAll().size();
        // Create the Niveau
        NiveauDTO niveauDTO = niveauMapper.toDto(niveau);
        restNiveauMockMvc.perform(post("/api/niveaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isCreated());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeCreate + 1);
        Niveau testNiveau = niveauList.get(niveauList.size() - 1);
        assertThat(testNiveau.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testNiveau.getTerme()).isEqualTo(DEFAULT_TERME);
        assertThat(testNiveau.getConcept()).isEqualTo(DEFAULT_CONCEPT);

        // Validate the Niveau in Elasticsearch
        verify(mockNiveauSearchRepository, times(1)).save(testNiveau);
    }

    @Test
    @Transactional
    public void createNiveauWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = niveauRepository.findAll().size();

        // Create the Niveau with an existing ID
        niveau.setId(1L);
        NiveauDTO niveauDTO = niveauMapper.toDto(niveau);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNiveauMockMvc.perform(post("/api/niveaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeCreate);

        // Validate the Niveau in Elasticsearch
        verify(mockNiveauSearchRepository, times(0)).save(niveau);
    }


    @Test
    @Transactional
    public void getAllNiveaus() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList
        restNiveauMockMvc.perform(get("/api/niveaus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niveau.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].terme").value(hasItem(DEFAULT_TERME)))
            .andExpect(jsonPath("$.[*].concept").value(hasItem(DEFAULT_CONCEPT)));
    }

    @Test
    @Transactional
    public void getNiveau() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get the niveau
        restNiveauMockMvc.perform(get("/api/niveaus/{id}", niveau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(niveau.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.terme").value(DEFAULT_TERME))
            .andExpect(jsonPath("$.concept").value(DEFAULT_CONCEPT));
    }


    @Test
    @Transactional
    public void getNiveausByIdFiltering() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        Long id = niveau.getId();

        defaultNiveauShouldBeFound("id.equals=" + id);
        defaultNiveauShouldNotBeFound("id.notEquals=" + id);

        defaultNiveauShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNiveauShouldNotBeFound("id.greaterThan=" + id);

        defaultNiveauShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNiveauShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNiveausByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where libelle equals to DEFAULT_LIBELLE
        defaultNiveauShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the niveauList where libelle equals to UPDATED_LIBELLE
        defaultNiveauShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllNiveausByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where libelle not equals to DEFAULT_LIBELLE
        defaultNiveauShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the niveauList where libelle not equals to UPDATED_LIBELLE
        defaultNiveauShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllNiveausByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultNiveauShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the niveauList where libelle equals to UPDATED_LIBELLE
        defaultNiveauShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllNiveausByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where libelle is not null
        defaultNiveauShouldBeFound("libelle.specified=true");

        // Get all the niveauList where libelle is null
        defaultNiveauShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllNiveausByLibelleContainsSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where libelle contains DEFAULT_LIBELLE
        defaultNiveauShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the niveauList where libelle contains UPDATED_LIBELLE
        defaultNiveauShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllNiveausByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where libelle does not contain DEFAULT_LIBELLE
        defaultNiveauShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the niveauList where libelle does not contain UPDATED_LIBELLE
        defaultNiveauShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }


    @Test
    @Transactional
    public void getAllNiveausByTermeIsEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where terme equals to DEFAULT_TERME
        defaultNiveauShouldBeFound("terme.equals=" + DEFAULT_TERME);

        // Get all the niveauList where terme equals to UPDATED_TERME
        defaultNiveauShouldNotBeFound("terme.equals=" + UPDATED_TERME);
    }

    @Test
    @Transactional
    public void getAllNiveausByTermeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where terme not equals to DEFAULT_TERME
        defaultNiveauShouldNotBeFound("terme.notEquals=" + DEFAULT_TERME);

        // Get all the niveauList where terme not equals to UPDATED_TERME
        defaultNiveauShouldBeFound("terme.notEquals=" + UPDATED_TERME);
    }

    @Test
    @Transactional
    public void getAllNiveausByTermeIsInShouldWork() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where terme in DEFAULT_TERME or UPDATED_TERME
        defaultNiveauShouldBeFound("terme.in=" + DEFAULT_TERME + "," + UPDATED_TERME);

        // Get all the niveauList where terme equals to UPDATED_TERME
        defaultNiveauShouldNotBeFound("terme.in=" + UPDATED_TERME);
    }

    @Test
    @Transactional
    public void getAllNiveausByTermeIsNullOrNotNull() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where terme is not null
        defaultNiveauShouldBeFound("terme.specified=true");

        // Get all the niveauList where terme is null
        defaultNiveauShouldNotBeFound("terme.specified=false");
    }
                @Test
    @Transactional
    public void getAllNiveausByTermeContainsSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where terme contains DEFAULT_TERME
        defaultNiveauShouldBeFound("terme.contains=" + DEFAULT_TERME);

        // Get all the niveauList where terme contains UPDATED_TERME
        defaultNiveauShouldNotBeFound("terme.contains=" + UPDATED_TERME);
    }

    @Test
    @Transactional
    public void getAllNiveausByTermeNotContainsSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where terme does not contain DEFAULT_TERME
        defaultNiveauShouldNotBeFound("terme.doesNotContain=" + DEFAULT_TERME);

        // Get all the niveauList where terme does not contain UPDATED_TERME
        defaultNiveauShouldBeFound("terme.doesNotContain=" + UPDATED_TERME);
    }


    @Test
    @Transactional
    public void getAllNiveausByConceptIsEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where concept equals to DEFAULT_CONCEPT
        defaultNiveauShouldBeFound("concept.equals=" + DEFAULT_CONCEPT);

        // Get all the niveauList where concept equals to UPDATED_CONCEPT
        defaultNiveauShouldNotBeFound("concept.equals=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllNiveausByConceptIsNotEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where concept not equals to DEFAULT_CONCEPT
        defaultNiveauShouldNotBeFound("concept.notEquals=" + DEFAULT_CONCEPT);

        // Get all the niveauList where concept not equals to UPDATED_CONCEPT
        defaultNiveauShouldBeFound("concept.notEquals=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllNiveausByConceptIsInShouldWork() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where concept in DEFAULT_CONCEPT or UPDATED_CONCEPT
        defaultNiveauShouldBeFound("concept.in=" + DEFAULT_CONCEPT + "," + UPDATED_CONCEPT);

        // Get all the niveauList where concept equals to UPDATED_CONCEPT
        defaultNiveauShouldNotBeFound("concept.in=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllNiveausByConceptIsNullOrNotNull() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where concept is not null
        defaultNiveauShouldBeFound("concept.specified=true");

        // Get all the niveauList where concept is null
        defaultNiveauShouldNotBeFound("concept.specified=false");
    }
                @Test
    @Transactional
    public void getAllNiveausByConceptContainsSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where concept contains DEFAULT_CONCEPT
        defaultNiveauShouldBeFound("concept.contains=" + DEFAULT_CONCEPT);

        // Get all the niveauList where concept contains UPDATED_CONCEPT
        defaultNiveauShouldNotBeFound("concept.contains=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllNiveausByConceptNotContainsSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        // Get all the niveauList where concept does not contain DEFAULT_CONCEPT
        defaultNiveauShouldNotBeFound("concept.doesNotContain=" + DEFAULT_CONCEPT);

        // Get all the niveauList where concept does not contain UPDATED_CONCEPT
        defaultNiveauShouldBeFound("concept.doesNotContain=" + UPDATED_CONCEPT);
    }


    @Test
    @Transactional
    public void getAllNiveausByArticleNumeriqueIsEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);
        ArticleNumerique articleNumerique = ArticleNumeriqueResourceIT.createEntity(em);
        em.persist(articleNumerique);
        em.flush();
        niveau.setArticleNumerique(articleNumerique);
        niveauRepository.saveAndFlush(niveau);
        Long articleNumeriqueId = articleNumerique.getId();

        // Get all the niveauList where articleNumerique equals to articleNumeriqueId
        defaultNiveauShouldBeFound("articleNumeriqueId.equals=" + articleNumeriqueId);

        // Get all the niveauList where articleNumerique equals to articleNumeriqueId + 1
        defaultNiveauShouldNotBeFound("articleNumeriqueId.equals=" + (articleNumeriqueId + 1));
    }


    @Test
    @Transactional
    public void getAllNiveausByArticlePapierIsEqualToSomething() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);
        ArticlePapier articlePapier = ArticlePapierResourceIT.createEntity(em);
        em.persist(articlePapier);
        em.flush();
        niveau.setArticlePapier(articlePapier);
        niveauRepository.saveAndFlush(niveau);
        Long articlePapierId = articlePapier.getId();

        // Get all the niveauList where articlePapier equals to articlePapierId
        defaultNiveauShouldBeFound("articlePapierId.equals=" + articlePapierId);

        // Get all the niveauList where articlePapier equals to articlePapierId + 1
        defaultNiveauShouldNotBeFound("articlePapierId.equals=" + (articlePapierId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNiveauShouldBeFound(String filter) throws Exception {
        restNiveauMockMvc.perform(get("/api/niveaus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niveau.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].terme").value(hasItem(DEFAULT_TERME)))
            .andExpect(jsonPath("$.[*].concept").value(hasItem(DEFAULT_CONCEPT)));

        // Check, that the count call also returns 1
        restNiveauMockMvc.perform(get("/api/niveaus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNiveauShouldNotBeFound(String filter) throws Exception {
        restNiveauMockMvc.perform(get("/api/niveaus?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNiveauMockMvc.perform(get("/api/niveaus/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNiveau() throws Exception {
        // Get the niveau
        restNiveauMockMvc.perform(get("/api/niveaus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNiveau() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        int databaseSizeBeforeUpdate = niveauRepository.findAll().size();

        // Update the niveau
        Niveau updatedNiveau = niveauRepository.findById(niveau.getId()).get();
        // Disconnect from session so that the updates on updatedNiveau are not directly saved in db
        em.detach(updatedNiveau);
        updatedNiveau
            .libelle(UPDATED_LIBELLE)
            .terme(UPDATED_TERME)
            .concept(UPDATED_CONCEPT);
        NiveauDTO niveauDTO = niveauMapper.toDto(updatedNiveau);

        restNiveauMockMvc.perform(put("/api/niveaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isOk());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeUpdate);
        Niveau testNiveau = niveauList.get(niveauList.size() - 1);
        assertThat(testNiveau.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testNiveau.getTerme()).isEqualTo(UPDATED_TERME);
        assertThat(testNiveau.getConcept()).isEqualTo(UPDATED_CONCEPT);

        // Validate the Niveau in Elasticsearch
        verify(mockNiveauSearchRepository, times(1)).save(testNiveau);
    }

    @Test
    @Transactional
    public void updateNonExistingNiveau() throws Exception {
        int databaseSizeBeforeUpdate = niveauRepository.findAll().size();

        // Create the Niveau
        NiveauDTO niveauDTO = niveauMapper.toDto(niveau);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNiveauMockMvc.perform(put("/api/niveaus")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(niveauDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Niveau in the database
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Niveau in Elasticsearch
        verify(mockNiveauSearchRepository, times(0)).save(niveau);
    }

    @Test
    @Transactional
    public void deleteNiveau() throws Exception {
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);

        int databaseSizeBeforeDelete = niveauRepository.findAll().size();

        // Delete the niveau
        restNiveauMockMvc.perform(delete("/api/niveaus/{id}", niveau.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Niveau> niveauList = niveauRepository.findAll();
        assertThat(niveauList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Niveau in Elasticsearch
        verify(mockNiveauSearchRepository, times(1)).deleteById(niveau.getId());
    }

    @Test
    @Transactional
    public void searchNiveau() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        niveauRepository.saveAndFlush(niveau);
        when(mockNiveauSearchRepository.search(queryStringQuery("id:" + niveau.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(niveau), PageRequest.of(0, 1), 1));

        // Search the niveau
        restNiveauMockMvc.perform(get("/api/_search/niveaus?query=id:" + niveau.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(niveau.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].terme").value(hasItem(DEFAULT_TERME)))
            .andExpect(jsonPath("$.[*].concept").value(hasItem(DEFAULT_CONCEPT)));
    }
}
