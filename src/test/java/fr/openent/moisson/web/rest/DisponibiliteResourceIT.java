package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.Disponibilite;
import fr.openent.moisson.domain.ArticlePapier;
import fr.openent.moisson.domain.ArticleNumerique;
import fr.openent.moisson.repository.DisponibiliteRepository;
import fr.openent.moisson.repository.search.DisponibiliteSearchRepository;
import fr.openent.moisson.service.DisponibiliteService;
import fr.openent.moisson.service.dto.DisponibiliteDTO;
import fr.openent.moisson.service.mapper.DisponibiliteMapper;
import fr.openent.moisson.service.dto.DisponibiliteCriteria;
import fr.openent.moisson.service.DisponibiliteQueryService;

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

import fr.openent.moisson.domain.enumeration.DisponibiliteEnum;
/**
 * Integration tests for the {@link DisponibiliteResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DisponibiliteResourceIT {

    private static final String DEFAULT_COMMENTAIRE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTAIRE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_DISPONIBILITE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_DISPONIBILITE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_COMMANDABLE = false;
    private static final Boolean UPDATED_COMMANDABLE = true;

    private static final DisponibiliteEnum DEFAULT_VALEUR = DisponibiliteEnum.DISPONIBLE;
    private static final DisponibiliteEnum UPDATED_VALEUR = DisponibiliteEnum.EN_COURS_D_IMPRESSION;

    @Autowired
    private DisponibiliteRepository disponibiliteRepository;

    @Autowired
    private DisponibiliteMapper disponibiliteMapper;

    @Autowired
    private DisponibiliteService disponibiliteService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.DisponibiliteSearchRepositoryMockConfiguration
     */
    @Autowired
    private DisponibiliteSearchRepository mockDisponibiliteSearchRepository;

    @Autowired
    private DisponibiliteQueryService disponibiliteQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisponibiliteMockMvc;

    private Disponibilite disponibilite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disponibilite createEntity(EntityManager em) {
        Disponibilite disponibilite = new Disponibilite()
            .commentaire(DEFAULT_COMMENTAIRE)
            .dateDisponibilite(DEFAULT_DATE_DISPONIBILITE)
            .commandable(DEFAULT_COMMANDABLE)
            .valeur(DEFAULT_VALEUR);
        return disponibilite;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Disponibilite createUpdatedEntity(EntityManager em) {
        Disponibilite disponibilite = new Disponibilite()
            .commentaire(UPDATED_COMMENTAIRE)
            .dateDisponibilite(UPDATED_DATE_DISPONIBILITE)
            .commandable(UPDATED_COMMANDABLE)
            .valeur(UPDATED_VALEUR);
        return disponibilite;
    }

    @BeforeEach
    public void initTest() {
        disponibilite = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisponibilite() throws Exception {
        int databaseSizeBeforeCreate = disponibiliteRepository.findAll().size();
        // Create the Disponibilite
        DisponibiliteDTO disponibiliteDTO = disponibiliteMapper.toDto(disponibilite);
        restDisponibiliteMockMvc.perform(post("/api/disponibilites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disponibiliteDTO)))
            .andExpect(status().isCreated());

        // Validate the Disponibilite in the database
        List<Disponibilite> disponibiliteList = disponibiliteRepository.findAll();
        assertThat(disponibiliteList).hasSize(databaseSizeBeforeCreate + 1);
        Disponibilite testDisponibilite = disponibiliteList.get(disponibiliteList.size() - 1);
        assertThat(testDisponibilite.getCommentaire()).isEqualTo(DEFAULT_COMMENTAIRE);
        assertThat(testDisponibilite.getDateDisponibilite()).isEqualTo(DEFAULT_DATE_DISPONIBILITE);
        assertThat(testDisponibilite.isCommandable()).isEqualTo(DEFAULT_COMMANDABLE);
        assertThat(testDisponibilite.getValeur()).isEqualTo(DEFAULT_VALEUR);

        // Validate the Disponibilite in Elasticsearch
        verify(mockDisponibiliteSearchRepository, times(1)).save(testDisponibilite);
    }

    @Test
    @Transactional
    public void createDisponibiliteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disponibiliteRepository.findAll().size();

        // Create the Disponibilite with an existing ID
        disponibilite.setId(1L);
        DisponibiliteDTO disponibiliteDTO = disponibiliteMapper.toDto(disponibilite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisponibiliteMockMvc.perform(post("/api/disponibilites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disponibiliteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disponibilite in the database
        List<Disponibilite> disponibiliteList = disponibiliteRepository.findAll();
        assertThat(disponibiliteList).hasSize(databaseSizeBeforeCreate);

        // Validate the Disponibilite in Elasticsearch
        verify(mockDisponibiliteSearchRepository, times(0)).save(disponibilite);
    }


    @Test
    @Transactional
    public void getAllDisponibilites() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList
        restDisponibiliteMockMvc.perform(get("/api/disponibilites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disponibilite.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].dateDisponibilite").value(hasItem(DEFAULT_DATE_DISPONIBILITE.toString())))
            .andExpect(jsonPath("$.[*].commandable").value(hasItem(DEFAULT_COMMANDABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.toString())));
    }
    
    @Test
    @Transactional
    public void getDisponibilite() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get the disponibilite
        restDisponibiliteMockMvc.perform(get("/api/disponibilites/{id}", disponibilite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(disponibilite.getId().intValue()))
            .andExpect(jsonPath("$.commentaire").value(DEFAULT_COMMENTAIRE))
            .andExpect(jsonPath("$.dateDisponibilite").value(DEFAULT_DATE_DISPONIBILITE.toString()))
            .andExpect(jsonPath("$.commandable").value(DEFAULT_COMMANDABLE.booleanValue()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR.toString()));
    }


    @Test
    @Transactional
    public void getDisponibilitesByIdFiltering() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        Long id = disponibilite.getId();

        defaultDisponibiliteShouldBeFound("id.equals=" + id);
        defaultDisponibiliteShouldNotBeFound("id.notEquals=" + id);

        defaultDisponibiliteShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDisponibiliteShouldNotBeFound("id.greaterThan=" + id);

        defaultDisponibiliteShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDisponibiliteShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDisponibilitesByCommentaireIsEqualToSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where commentaire equals to DEFAULT_COMMENTAIRE
        defaultDisponibiliteShouldBeFound("commentaire.equals=" + DEFAULT_COMMENTAIRE);

        // Get all the disponibiliteList where commentaire equals to UPDATED_COMMENTAIRE
        defaultDisponibiliteShouldNotBeFound("commentaire.equals=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByCommentaireIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where commentaire not equals to DEFAULT_COMMENTAIRE
        defaultDisponibiliteShouldNotBeFound("commentaire.notEquals=" + DEFAULT_COMMENTAIRE);

        // Get all the disponibiliteList where commentaire not equals to UPDATED_COMMENTAIRE
        defaultDisponibiliteShouldBeFound("commentaire.notEquals=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByCommentaireIsInShouldWork() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where commentaire in DEFAULT_COMMENTAIRE or UPDATED_COMMENTAIRE
        defaultDisponibiliteShouldBeFound("commentaire.in=" + DEFAULT_COMMENTAIRE + "," + UPDATED_COMMENTAIRE);

        // Get all the disponibiliteList where commentaire equals to UPDATED_COMMENTAIRE
        defaultDisponibiliteShouldNotBeFound("commentaire.in=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByCommentaireIsNullOrNotNull() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where commentaire is not null
        defaultDisponibiliteShouldBeFound("commentaire.specified=true");

        // Get all the disponibiliteList where commentaire is null
        defaultDisponibiliteShouldNotBeFound("commentaire.specified=false");
    }
                @Test
    @Transactional
    public void getAllDisponibilitesByCommentaireContainsSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where commentaire contains DEFAULT_COMMENTAIRE
        defaultDisponibiliteShouldBeFound("commentaire.contains=" + DEFAULT_COMMENTAIRE);

        // Get all the disponibiliteList where commentaire contains UPDATED_COMMENTAIRE
        defaultDisponibiliteShouldNotBeFound("commentaire.contains=" + UPDATED_COMMENTAIRE);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByCommentaireNotContainsSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where commentaire does not contain DEFAULT_COMMENTAIRE
        defaultDisponibiliteShouldNotBeFound("commentaire.doesNotContain=" + DEFAULT_COMMENTAIRE);

        // Get all the disponibiliteList where commentaire does not contain UPDATED_COMMENTAIRE
        defaultDisponibiliteShouldBeFound("commentaire.doesNotContain=" + UPDATED_COMMENTAIRE);
    }


    @Test
    @Transactional
    public void getAllDisponibilitesByDateDisponibiliteIsEqualToSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where dateDisponibilite equals to DEFAULT_DATE_DISPONIBILITE
        defaultDisponibiliteShouldBeFound("dateDisponibilite.equals=" + DEFAULT_DATE_DISPONIBILITE);

        // Get all the disponibiliteList where dateDisponibilite equals to UPDATED_DATE_DISPONIBILITE
        defaultDisponibiliteShouldNotBeFound("dateDisponibilite.equals=" + UPDATED_DATE_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByDateDisponibiliteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where dateDisponibilite not equals to DEFAULT_DATE_DISPONIBILITE
        defaultDisponibiliteShouldNotBeFound("dateDisponibilite.notEquals=" + DEFAULT_DATE_DISPONIBILITE);

        // Get all the disponibiliteList where dateDisponibilite not equals to UPDATED_DATE_DISPONIBILITE
        defaultDisponibiliteShouldBeFound("dateDisponibilite.notEquals=" + UPDATED_DATE_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByDateDisponibiliteIsInShouldWork() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where dateDisponibilite in DEFAULT_DATE_DISPONIBILITE or UPDATED_DATE_DISPONIBILITE
        defaultDisponibiliteShouldBeFound("dateDisponibilite.in=" + DEFAULT_DATE_DISPONIBILITE + "," + UPDATED_DATE_DISPONIBILITE);

        // Get all the disponibiliteList where dateDisponibilite equals to UPDATED_DATE_DISPONIBILITE
        defaultDisponibiliteShouldNotBeFound("dateDisponibilite.in=" + UPDATED_DATE_DISPONIBILITE);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByDateDisponibiliteIsNullOrNotNull() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where dateDisponibilite is not null
        defaultDisponibiliteShouldBeFound("dateDisponibilite.specified=true");

        // Get all the disponibiliteList where dateDisponibilite is null
        defaultDisponibiliteShouldNotBeFound("dateDisponibilite.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByCommandableIsEqualToSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where commandable equals to DEFAULT_COMMANDABLE
        defaultDisponibiliteShouldBeFound("commandable.equals=" + DEFAULT_COMMANDABLE);

        // Get all the disponibiliteList where commandable equals to UPDATED_COMMANDABLE
        defaultDisponibiliteShouldNotBeFound("commandable.equals=" + UPDATED_COMMANDABLE);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByCommandableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where commandable not equals to DEFAULT_COMMANDABLE
        defaultDisponibiliteShouldNotBeFound("commandable.notEquals=" + DEFAULT_COMMANDABLE);

        // Get all the disponibiliteList where commandable not equals to UPDATED_COMMANDABLE
        defaultDisponibiliteShouldBeFound("commandable.notEquals=" + UPDATED_COMMANDABLE);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByCommandableIsInShouldWork() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where commandable in DEFAULT_COMMANDABLE or UPDATED_COMMANDABLE
        defaultDisponibiliteShouldBeFound("commandable.in=" + DEFAULT_COMMANDABLE + "," + UPDATED_COMMANDABLE);

        // Get all the disponibiliteList where commandable equals to UPDATED_COMMANDABLE
        defaultDisponibiliteShouldNotBeFound("commandable.in=" + UPDATED_COMMANDABLE);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByCommandableIsNullOrNotNull() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where commandable is not null
        defaultDisponibiliteShouldBeFound("commandable.specified=true");

        // Get all the disponibiliteList where commandable is null
        defaultDisponibiliteShouldNotBeFound("commandable.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByValeurIsEqualToSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where valeur equals to DEFAULT_VALEUR
        defaultDisponibiliteShouldBeFound("valeur.equals=" + DEFAULT_VALEUR);

        // Get all the disponibiliteList where valeur equals to UPDATED_VALEUR
        defaultDisponibiliteShouldNotBeFound("valeur.equals=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByValeurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where valeur not equals to DEFAULT_VALEUR
        defaultDisponibiliteShouldNotBeFound("valeur.notEquals=" + DEFAULT_VALEUR);

        // Get all the disponibiliteList where valeur not equals to UPDATED_VALEUR
        defaultDisponibiliteShouldBeFound("valeur.notEquals=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByValeurIsInShouldWork() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where valeur in DEFAULT_VALEUR or UPDATED_VALEUR
        defaultDisponibiliteShouldBeFound("valeur.in=" + DEFAULT_VALEUR + "," + UPDATED_VALEUR);

        // Get all the disponibiliteList where valeur equals to UPDATED_VALEUR
        defaultDisponibiliteShouldNotBeFound("valeur.in=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByValeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        // Get all the disponibiliteList where valeur is not null
        defaultDisponibiliteShouldBeFound("valeur.specified=true");

        // Get all the disponibiliteList where valeur is null
        defaultDisponibiliteShouldNotBeFound("valeur.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisponibilitesByArticlePapierIsEqualToSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);
        ArticlePapier articlePapier = ArticlePapierResourceIT.createEntity(em);
        em.persist(articlePapier);
        em.flush();
        disponibilite.setArticlePapier(articlePapier);
        articlePapier.setDisponibilite(disponibilite);
        disponibiliteRepository.saveAndFlush(disponibilite);
        Long articlePapierId = articlePapier.getId();

        // Get all the disponibiliteList where articlePapier equals to articlePapierId
        defaultDisponibiliteShouldBeFound("articlePapierId.equals=" + articlePapierId);

        // Get all the disponibiliteList where articlePapier equals to articlePapierId + 1
        defaultDisponibiliteShouldNotBeFound("articlePapierId.equals=" + (articlePapierId + 1));
    }


    @Test
    @Transactional
    public void getAllDisponibilitesByArticleNumeriqueIsEqualToSomething() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);
        ArticleNumerique articleNumerique = ArticleNumeriqueResourceIT.createEntity(em);
        em.persist(articleNumerique);
        em.flush();
        disponibilite.setArticleNumerique(articleNumerique);
        articleNumerique.setDisponibilite(disponibilite);
        disponibiliteRepository.saveAndFlush(disponibilite);
        Long articleNumeriqueId = articleNumerique.getId();

        // Get all the disponibiliteList where articleNumerique equals to articleNumeriqueId
        defaultDisponibiliteShouldBeFound("articleNumeriqueId.equals=" + articleNumeriqueId);

        // Get all the disponibiliteList where articleNumerique equals to articleNumeriqueId + 1
        defaultDisponibiliteShouldNotBeFound("articleNumeriqueId.equals=" + (articleNumeriqueId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDisponibiliteShouldBeFound(String filter) throws Exception {
        restDisponibiliteMockMvc.perform(get("/api/disponibilites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disponibilite.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].dateDisponibilite").value(hasItem(DEFAULT_DATE_DISPONIBILITE.toString())))
            .andExpect(jsonPath("$.[*].commandable").value(hasItem(DEFAULT_COMMANDABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.toString())));

        // Check, that the count call also returns 1
        restDisponibiliteMockMvc.perform(get("/api/disponibilites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDisponibiliteShouldNotBeFound(String filter) throws Exception {
        restDisponibiliteMockMvc.perform(get("/api/disponibilites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDisponibiliteMockMvc.perform(get("/api/disponibilites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDisponibilite() throws Exception {
        // Get the disponibilite
        restDisponibiliteMockMvc.perform(get("/api/disponibilites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisponibilite() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        int databaseSizeBeforeUpdate = disponibiliteRepository.findAll().size();

        // Update the disponibilite
        Disponibilite updatedDisponibilite = disponibiliteRepository.findById(disponibilite.getId()).get();
        // Disconnect from session so that the updates on updatedDisponibilite are not directly saved in db
        em.detach(updatedDisponibilite);
        updatedDisponibilite
            .commentaire(UPDATED_COMMENTAIRE)
            .dateDisponibilite(UPDATED_DATE_DISPONIBILITE)
            .commandable(UPDATED_COMMANDABLE)
            .valeur(UPDATED_VALEUR);
        DisponibiliteDTO disponibiliteDTO = disponibiliteMapper.toDto(updatedDisponibilite);

        restDisponibiliteMockMvc.perform(put("/api/disponibilites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disponibiliteDTO)))
            .andExpect(status().isOk());

        // Validate the Disponibilite in the database
        List<Disponibilite> disponibiliteList = disponibiliteRepository.findAll();
        assertThat(disponibiliteList).hasSize(databaseSizeBeforeUpdate);
        Disponibilite testDisponibilite = disponibiliteList.get(disponibiliteList.size() - 1);
        assertThat(testDisponibilite.getCommentaire()).isEqualTo(UPDATED_COMMENTAIRE);
        assertThat(testDisponibilite.getDateDisponibilite()).isEqualTo(UPDATED_DATE_DISPONIBILITE);
        assertThat(testDisponibilite.isCommandable()).isEqualTo(UPDATED_COMMANDABLE);
        assertThat(testDisponibilite.getValeur()).isEqualTo(UPDATED_VALEUR);

        // Validate the Disponibilite in Elasticsearch
        verify(mockDisponibiliteSearchRepository, times(1)).save(testDisponibilite);
    }

    @Test
    @Transactional
    public void updateNonExistingDisponibilite() throws Exception {
        int databaseSizeBeforeUpdate = disponibiliteRepository.findAll().size();

        // Create the Disponibilite
        DisponibiliteDTO disponibiliteDTO = disponibiliteMapper.toDto(disponibilite);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisponibiliteMockMvc.perform(put("/api/disponibilites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disponibiliteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Disponibilite in the database
        List<Disponibilite> disponibiliteList = disponibiliteRepository.findAll();
        assertThat(disponibiliteList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Disponibilite in Elasticsearch
        verify(mockDisponibiliteSearchRepository, times(0)).save(disponibilite);
    }

    @Test
    @Transactional
    public void deleteDisponibilite() throws Exception {
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);

        int databaseSizeBeforeDelete = disponibiliteRepository.findAll().size();

        // Delete the disponibilite
        restDisponibiliteMockMvc.perform(delete("/api/disponibilites/{id}", disponibilite.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Disponibilite> disponibiliteList = disponibiliteRepository.findAll();
        assertThat(disponibiliteList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Disponibilite in Elasticsearch
        verify(mockDisponibiliteSearchRepository, times(1)).deleteById(disponibilite.getId());
    }

    @Test
    @Transactional
    public void searchDisponibilite() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        disponibiliteRepository.saveAndFlush(disponibilite);
        when(mockDisponibiliteSearchRepository.search(queryStringQuery("id:" + disponibilite.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(disponibilite), PageRequest.of(0, 1), 1));

        // Search the disponibilite
        restDisponibiliteMockMvc.perform(get("/api/_search/disponibilites?query=id:" + disponibilite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disponibilite.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentaire").value(hasItem(DEFAULT_COMMENTAIRE)))
            .andExpect(jsonPath("$.[*].dateDisponibilite").value(hasItem(DEFAULT_DATE_DISPONIBILITE.toString())))
            .andExpect(jsonPath("$.[*].commandable").value(hasItem(DEFAULT_COMMANDABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR.toString())));
    }
}
