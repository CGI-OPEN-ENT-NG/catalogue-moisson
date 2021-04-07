package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.Classe;
import fr.openent.moisson.domain.ArticleNumerique;
import fr.openent.moisson.repository.ClasseRepository;
import fr.openent.moisson.repository.search.ClasseSearchRepository;
import fr.openent.moisson.service.ClasseService;
import fr.openent.moisson.service.dto.ClasseDTO;
import fr.openent.moisson.service.mapper.ClasseMapper;
import fr.openent.moisson.service.dto.ClasseCriteria;
import fr.openent.moisson.service.ClasseQueryService;

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

/**
 * Integration tests for the {@link ClasseResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClasseResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private ClasseMapper classeMapper;

    @Autowired
    private ClasseService classeService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.ClasseSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClasseSearchRepository mockClasseSearchRepository;

    @Autowired
    private ClasseQueryService classeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClasseMockMvc;

    private Classe classe;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classe createEntity(EntityManager em) {
        Classe classe = new Classe()
            .libelle(DEFAULT_LIBELLE);
        return classe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classe createUpdatedEntity(EntityManager em) {
        Classe classe = new Classe()
            .libelle(UPDATED_LIBELLE);
        return classe;
    }

    @BeforeEach
    public void initTest() {
        classe = createEntity(em);
    }

    @Test
    @Transactional
    public void createClasse() throws Exception {
        int databaseSizeBeforeCreate = classeRepository.findAll().size();
        // Create the Classe
        ClasseDTO classeDTO = classeMapper.toDto(classe);
        restClasseMockMvc.perform(post("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isCreated());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeCreate + 1);
        Classe testClasse = classeList.get(classeList.size() - 1);
        assertThat(testClasse.getLibelle()).isEqualTo(DEFAULT_LIBELLE);

        // Validate the Classe in Elasticsearch
        verify(mockClasseSearchRepository, times(1)).save(testClasse);
    }

    @Test
    @Transactional
    public void createClasseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classeRepository.findAll().size();

        // Create the Classe with an existing ID
        classe.setId(1L);
        ClasseDTO classeDTO = classeMapper.toDto(classe);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClasseMockMvc.perform(post("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Classe in Elasticsearch
        verify(mockClasseSearchRepository, times(0)).save(classe);
    }


    @Test
    @Transactional
    public void getAllClasses() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList
        restClasseMockMvc.perform(get("/api/classes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classe.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
    
    @Test
    @Transactional
    public void getClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get the classe
        restClasseMockMvc.perform(get("/api/classes/{id}", classe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classe.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE));
    }


    @Test
    @Transactional
    public void getClassesByIdFiltering() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        Long id = classe.getId();

        defaultClasseShouldBeFound("id.equals=" + id);
        defaultClasseShouldNotBeFound("id.notEquals=" + id);

        defaultClasseShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClasseShouldNotBeFound("id.greaterThan=" + id);

        defaultClasseShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClasseShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllClassesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where libelle equals to DEFAULT_LIBELLE
        defaultClasseShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the classeList where libelle equals to UPDATED_LIBELLE
        defaultClasseShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllClassesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where libelle not equals to DEFAULT_LIBELLE
        defaultClasseShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the classeList where libelle not equals to UPDATED_LIBELLE
        defaultClasseShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllClassesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultClasseShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the classeList where libelle equals to UPDATED_LIBELLE
        defaultClasseShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllClassesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where libelle is not null
        defaultClasseShouldBeFound("libelle.specified=true");

        // Get all the classeList where libelle is null
        defaultClasseShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllClassesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where libelle contains DEFAULT_LIBELLE
        defaultClasseShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the classeList where libelle contains UPDATED_LIBELLE
        defaultClasseShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllClassesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        // Get all the classeList where libelle does not contain DEFAULT_LIBELLE
        defaultClasseShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the classeList where libelle does not contain UPDATED_LIBELLE
        defaultClasseShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }


    @Test
    @Transactional
    public void getAllClassesByArticleNumeriqueIsEqualToSomething() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);
        ArticleNumerique articleNumerique = ArticleNumeriqueResourceIT.createEntity(em);
        em.persist(articleNumerique);
        em.flush();
        classe.setArticleNumerique(articleNumerique);
        classeRepository.saveAndFlush(classe);
        Long articleNumeriqueId = articleNumerique.getId();

        // Get all the classeList where articleNumerique equals to articleNumeriqueId
        defaultClasseShouldBeFound("articleNumeriqueId.equals=" + articleNumeriqueId);

        // Get all the classeList where articleNumerique equals to articleNumeriqueId + 1
        defaultClasseShouldNotBeFound("articleNumeriqueId.equals=" + (articleNumeriqueId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClasseShouldBeFound(String filter) throws Exception {
        restClasseMockMvc.perform(get("/api/classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classe.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));

        // Check, that the count call also returns 1
        restClasseMockMvc.perform(get("/api/classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClasseShouldNotBeFound(String filter) throws Exception {
        restClasseMockMvc.perform(get("/api/classes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClasseMockMvc.perform(get("/api/classes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingClasse() throws Exception {
        // Get the classe
        restClasseMockMvc.perform(get("/api/classes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        int databaseSizeBeforeUpdate = classeRepository.findAll().size();

        // Update the classe
        Classe updatedClasse = classeRepository.findById(classe.getId()).get();
        // Disconnect from session so that the updates on updatedClasse are not directly saved in db
        em.detach(updatedClasse);
        updatedClasse
            .libelle(UPDATED_LIBELLE);
        ClasseDTO classeDTO = classeMapper.toDto(updatedClasse);

        restClasseMockMvc.perform(put("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isOk());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeUpdate);
        Classe testClasse = classeList.get(classeList.size() - 1);
        assertThat(testClasse.getLibelle()).isEqualTo(UPDATED_LIBELLE);

        // Validate the Classe in Elasticsearch
        verify(mockClasseSearchRepository, times(1)).save(testClasse);
    }

    @Test
    @Transactional
    public void updateNonExistingClasse() throws Exception {
        int databaseSizeBeforeUpdate = classeRepository.findAll().size();

        // Create the Classe
        ClasseDTO classeDTO = classeMapper.toDto(classe);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClasseMockMvc.perform(put("/api/classes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(classeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Classe in the database
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Classe in Elasticsearch
        verify(mockClasseSearchRepository, times(0)).save(classe);
    }

    @Test
    @Transactional
    public void deleteClasse() throws Exception {
        // Initialize the database
        classeRepository.saveAndFlush(classe);

        int databaseSizeBeforeDelete = classeRepository.findAll().size();

        // Delete the classe
        restClasseMockMvc.perform(delete("/api/classes/{id}", classe.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Classe> classeList = classeRepository.findAll();
        assertThat(classeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Classe in Elasticsearch
        verify(mockClasseSearchRepository, times(1)).deleteById(classe.getId());
    }

    @Test
    @Transactional
    public void searchClasse() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        classeRepository.saveAndFlush(classe);
        when(mockClasseSearchRepository.search(queryStringQuery("id:" + classe.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(classe), PageRequest.of(0, 1), 1));

        // Search the classe
        restClasseMockMvc.perform(get("/api/_search/classes?query=id:" + classe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classe.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)));
    }
}
