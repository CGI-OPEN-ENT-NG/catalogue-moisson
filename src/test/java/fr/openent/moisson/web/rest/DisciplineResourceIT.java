package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.Discipline;
import fr.openent.moisson.repository.DisciplineRepository;
import fr.openent.moisson.repository.search.DisciplineSearchRepository;
import fr.openent.moisson.service.DisciplineService;
import fr.openent.moisson.service.dto.DisciplineDTO;
import fr.openent.moisson.service.mapper.DisciplineMapper;
import fr.openent.moisson.service.dto.DisciplineCriteria;
import fr.openent.moisson.service.DisciplineQueryService;

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
 * Integration tests for the {@link DisciplineResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DisciplineResourceIT {

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final String DEFAULT_TERME = "AAAAAAAAAA";
    private static final String UPDATED_TERME = "BBBBBBBBBB";

    private static final String DEFAULT_CONCEPT = "AAAAAAAAAA";
    private static final String UPDATED_CONCEPT = "BBBBBBBBBB";

    @Autowired
    private DisciplineRepository disciplineRepository;

    @Autowired
    private DisciplineMapper disciplineMapper;

    @Autowired
    private DisciplineService disciplineService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.DisciplineSearchRepositoryMockConfiguration
     */
    @Autowired
    private DisciplineSearchRepository mockDisciplineSearchRepository;

    @Autowired
    private DisciplineQueryService disciplineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDisciplineMockMvc;

    private Discipline discipline;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discipline createEntity(EntityManager em) {
        Discipline discipline = new Discipline()
            .libelle(DEFAULT_LIBELLE)
            .terme(DEFAULT_TERME)
            .concept(DEFAULT_CONCEPT);
        return discipline;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Discipline createUpdatedEntity(EntityManager em) {
        Discipline discipline = new Discipline()
            .libelle(UPDATED_LIBELLE)
            .terme(UPDATED_TERME)
            .concept(UPDATED_CONCEPT);
        return discipline;
    }

    @BeforeEach
    public void initTest() {
        discipline = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscipline() throws Exception {
        int databaseSizeBeforeCreate = disciplineRepository.findAll().size();
        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);
        restDisciplineMockMvc.perform(post("/api/disciplines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isCreated());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeCreate + 1);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testDiscipline.getTerme()).isEqualTo(DEFAULT_TERME);
        assertThat(testDiscipline.getConcept()).isEqualTo(DEFAULT_CONCEPT);

        // Validate the Discipline in Elasticsearch
        verify(mockDisciplineSearchRepository, times(1)).save(testDiscipline);
    }

    @Test
    @Transactional
    public void createDisciplineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disciplineRepository.findAll().size();

        // Create the Discipline with an existing ID
        discipline.setId(1L);
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisciplineMockMvc.perform(post("/api/disciplines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeCreate);

        // Validate the Discipline in Elasticsearch
        verify(mockDisciplineSearchRepository, times(0)).save(discipline);
    }


    @Test
    @Transactional
    public void getAllDisciplines() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList
        restDisciplineMockMvc.perform(get("/api/disciplines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discipline.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].terme").value(hasItem(DEFAULT_TERME)))
            .andExpect(jsonPath("$.[*].concept").value(hasItem(DEFAULT_CONCEPT)));
    }
    
    @Test
    @Transactional
    public void getDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get the discipline
        restDisciplineMockMvc.perform(get("/api/disciplines/{id}", discipline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(discipline.getId().intValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.terme").value(DEFAULT_TERME))
            .andExpect(jsonPath("$.concept").value(DEFAULT_CONCEPT));
    }


    @Test
    @Transactional
    public void getDisciplinesByIdFiltering() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        Long id = discipline.getId();

        defaultDisciplineShouldBeFound("id.equals=" + id);
        defaultDisciplineShouldNotBeFound("id.notEquals=" + id);

        defaultDisciplineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDisciplineShouldNotBeFound("id.greaterThan=" + id);

        defaultDisciplineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDisciplineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDisciplinesByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where libelle equals to DEFAULT_LIBELLE
        defaultDisciplineShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the disciplineList where libelle equals to UPDATED_LIBELLE
        defaultDisciplineShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where libelle not equals to DEFAULT_LIBELLE
        defaultDisciplineShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the disciplineList where libelle not equals to UPDATED_LIBELLE
        defaultDisciplineShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultDisciplineShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the disciplineList where libelle equals to UPDATED_LIBELLE
        defaultDisciplineShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where libelle is not null
        defaultDisciplineShouldBeFound("libelle.specified=true");

        // Get all the disciplineList where libelle is null
        defaultDisciplineShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllDisciplinesByLibelleContainsSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where libelle contains DEFAULT_LIBELLE
        defaultDisciplineShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the disciplineList where libelle contains UPDATED_LIBELLE
        defaultDisciplineShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where libelle does not contain DEFAULT_LIBELLE
        defaultDisciplineShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the disciplineList where libelle does not contain UPDATED_LIBELLE
        defaultDisciplineShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }


    @Test
    @Transactional
    public void getAllDisciplinesByTermeIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where terme equals to DEFAULT_TERME
        defaultDisciplineShouldBeFound("terme.equals=" + DEFAULT_TERME);

        // Get all the disciplineList where terme equals to UPDATED_TERME
        defaultDisciplineShouldNotBeFound("terme.equals=" + UPDATED_TERME);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByTermeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where terme not equals to DEFAULT_TERME
        defaultDisciplineShouldNotBeFound("terme.notEquals=" + DEFAULT_TERME);

        // Get all the disciplineList where terme not equals to UPDATED_TERME
        defaultDisciplineShouldBeFound("terme.notEquals=" + UPDATED_TERME);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByTermeIsInShouldWork() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where terme in DEFAULT_TERME or UPDATED_TERME
        defaultDisciplineShouldBeFound("terme.in=" + DEFAULT_TERME + "," + UPDATED_TERME);

        // Get all the disciplineList where terme equals to UPDATED_TERME
        defaultDisciplineShouldNotBeFound("terme.in=" + UPDATED_TERME);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByTermeIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where terme is not null
        defaultDisciplineShouldBeFound("terme.specified=true");

        // Get all the disciplineList where terme is null
        defaultDisciplineShouldNotBeFound("terme.specified=false");
    }
                @Test
    @Transactional
    public void getAllDisciplinesByTermeContainsSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where terme contains DEFAULT_TERME
        defaultDisciplineShouldBeFound("terme.contains=" + DEFAULT_TERME);

        // Get all the disciplineList where terme contains UPDATED_TERME
        defaultDisciplineShouldNotBeFound("terme.contains=" + UPDATED_TERME);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByTermeNotContainsSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where terme does not contain DEFAULT_TERME
        defaultDisciplineShouldNotBeFound("terme.doesNotContain=" + DEFAULT_TERME);

        // Get all the disciplineList where terme does not contain UPDATED_TERME
        defaultDisciplineShouldBeFound("terme.doesNotContain=" + UPDATED_TERME);
    }


    @Test
    @Transactional
    public void getAllDisciplinesByConceptIsEqualToSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where concept equals to DEFAULT_CONCEPT
        defaultDisciplineShouldBeFound("concept.equals=" + DEFAULT_CONCEPT);

        // Get all the disciplineList where concept equals to UPDATED_CONCEPT
        defaultDisciplineShouldNotBeFound("concept.equals=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByConceptIsNotEqualToSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where concept not equals to DEFAULT_CONCEPT
        defaultDisciplineShouldNotBeFound("concept.notEquals=" + DEFAULT_CONCEPT);

        // Get all the disciplineList where concept not equals to UPDATED_CONCEPT
        defaultDisciplineShouldBeFound("concept.notEquals=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByConceptIsInShouldWork() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where concept in DEFAULT_CONCEPT or UPDATED_CONCEPT
        defaultDisciplineShouldBeFound("concept.in=" + DEFAULT_CONCEPT + "," + UPDATED_CONCEPT);

        // Get all the disciplineList where concept equals to UPDATED_CONCEPT
        defaultDisciplineShouldNotBeFound("concept.in=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByConceptIsNullOrNotNull() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where concept is not null
        defaultDisciplineShouldBeFound("concept.specified=true");

        // Get all the disciplineList where concept is null
        defaultDisciplineShouldNotBeFound("concept.specified=false");
    }
                @Test
    @Transactional
    public void getAllDisciplinesByConceptContainsSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where concept contains DEFAULT_CONCEPT
        defaultDisciplineShouldBeFound("concept.contains=" + DEFAULT_CONCEPT);

        // Get all the disciplineList where concept contains UPDATED_CONCEPT
        defaultDisciplineShouldNotBeFound("concept.contains=" + UPDATED_CONCEPT);
    }

    @Test
    @Transactional
    public void getAllDisciplinesByConceptNotContainsSomething() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        // Get all the disciplineList where concept does not contain DEFAULT_CONCEPT
        defaultDisciplineShouldNotBeFound("concept.doesNotContain=" + DEFAULT_CONCEPT);

        // Get all the disciplineList where concept does not contain UPDATED_CONCEPT
        defaultDisciplineShouldBeFound("concept.doesNotContain=" + UPDATED_CONCEPT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDisciplineShouldBeFound(String filter) throws Exception {
        restDisciplineMockMvc.perform(get("/api/disciplines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discipline.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].terme").value(hasItem(DEFAULT_TERME)))
            .andExpect(jsonPath("$.[*].concept").value(hasItem(DEFAULT_CONCEPT)));

        // Check, that the count call also returns 1
        restDisciplineMockMvc.perform(get("/api/disciplines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDisciplineShouldNotBeFound(String filter) throws Exception {
        restDisciplineMockMvc.perform(get("/api/disciplines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDisciplineMockMvc.perform(get("/api/disciplines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingDiscipline() throws Exception {
        // Get the discipline
        restDisciplineMockMvc.perform(get("/api/disciplines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();

        // Update the discipline
        Discipline updatedDiscipline = disciplineRepository.findById(discipline.getId()).get();
        // Disconnect from session so that the updates on updatedDiscipline are not directly saved in db
        em.detach(updatedDiscipline);
        updatedDiscipline
            .libelle(UPDATED_LIBELLE)
            .terme(UPDATED_TERME)
            .concept(UPDATED_CONCEPT);
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(updatedDiscipline);

        restDisciplineMockMvc.perform(put("/api/disciplines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isOk());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);
        Discipline testDiscipline = disciplineList.get(disciplineList.size() - 1);
        assertThat(testDiscipline.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testDiscipline.getTerme()).isEqualTo(UPDATED_TERME);
        assertThat(testDiscipline.getConcept()).isEqualTo(UPDATED_CONCEPT);

        // Validate the Discipline in Elasticsearch
        verify(mockDisciplineSearchRepository, times(1)).save(testDiscipline);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscipline() throws Exception {
        int databaseSizeBeforeUpdate = disciplineRepository.findAll().size();

        // Create the Discipline
        DisciplineDTO disciplineDTO = disciplineMapper.toDto(discipline);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisciplineMockMvc.perform(put("/api/disciplines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(disciplineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Discipline in the database
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Discipline in Elasticsearch
        verify(mockDisciplineSearchRepository, times(0)).save(discipline);
    }

    @Test
    @Transactional
    public void deleteDiscipline() throws Exception {
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);

        int databaseSizeBeforeDelete = disciplineRepository.findAll().size();

        // Delete the discipline
        restDisciplineMockMvc.perform(delete("/api/disciplines/{id}", discipline.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Discipline> disciplineList = disciplineRepository.findAll();
        assertThat(disciplineList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Discipline in Elasticsearch
        verify(mockDisciplineSearchRepository, times(1)).deleteById(discipline.getId());
    }

    @Test
    @Transactional
    public void searchDiscipline() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        disciplineRepository.saveAndFlush(discipline);
        when(mockDisciplineSearchRepository.search(queryStringQuery("id:" + discipline.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(discipline), PageRequest.of(0, 1), 1));

        // Search the discipline
        restDisciplineMockMvc.perform(get("/api/_search/disciplines?query=id:" + discipline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discipline.getId().intValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].terme").value(hasItem(DEFAULT_TERME)))
            .andExpect(jsonPath("$.[*].concept").value(hasItem(DEFAULT_CONCEPT)));
    }
}
