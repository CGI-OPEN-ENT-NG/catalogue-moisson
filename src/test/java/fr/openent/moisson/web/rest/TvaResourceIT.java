package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.Tva;
import fr.openent.moisson.domain.Offre;
import fr.openent.moisson.repository.TvaRepository;
import fr.openent.moisson.repository.search.TvaSearchRepository;
import fr.openent.moisson.service.TvaService;
import fr.openent.moisson.service.dto.TvaDTO;
import fr.openent.moisson.service.mapper.TvaMapper;
import fr.openent.moisson.service.dto.TvaCriteria;
import fr.openent.moisson.service.TvaQueryService;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TvaResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class TvaResourceIT {

    private static final BigDecimal DEFAULT_TAUX = new BigDecimal(1);
    private static final BigDecimal UPDATED_TAUX = new BigDecimal(2);
    private static final BigDecimal SMALLER_TAUX = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_POURCENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_POURCENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_POURCENT = new BigDecimal(1 - 1);

    @Autowired
    private TvaRepository tvaRepository;

    @Autowired
    private TvaMapper tvaMapper;

    @Autowired
    private TvaService tvaService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.TvaSearchRepositoryMockConfiguration
     */
    @Autowired
    private TvaSearchRepository mockTvaSearchRepository;

    @Autowired
    private TvaQueryService tvaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTvaMockMvc;

    private Tva tva;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tva createEntity(EntityManager em) {
        Tva tva = new Tva()
            .taux(DEFAULT_TAUX)
            .pourcent(DEFAULT_POURCENT);
        return tva;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tva createUpdatedEntity(EntityManager em) {
        Tva tva = new Tva()
            .taux(UPDATED_TAUX)
            .pourcent(UPDATED_POURCENT);
        return tva;
    }

    @BeforeEach
    public void initTest() {
        tva = createEntity(em);
    }

    @Test
    @Transactional
    public void createTva() throws Exception {
        int databaseSizeBeforeCreate = tvaRepository.findAll().size();
        // Create the Tva
        TvaDTO tvaDTO = tvaMapper.toDto(tva);
        restTvaMockMvc.perform(post("/api/tvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isCreated());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeCreate + 1);
        Tva testTva = tvaList.get(tvaList.size() - 1);
        assertThat(testTva.getTaux()).isEqualTo(DEFAULT_TAUX);
        assertThat(testTva.getPourcent()).isEqualTo(DEFAULT_POURCENT);

        // Validate the Tva in Elasticsearch
        verify(mockTvaSearchRepository, times(1)).save(testTva);
    }

    @Test
    @Transactional
    public void createTvaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tvaRepository.findAll().size();

        // Create the Tva with an existing ID
        tva.setId(1L);
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTvaMockMvc.perform(post("/api/tvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeCreate);

        // Validate the Tva in Elasticsearch
        verify(mockTvaSearchRepository, times(0)).save(tva);
    }


    @Test
    @Transactional
    public void getAllTvas() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList
        restTvaMockMvc.perform(get("/api/tvas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tva.getId().intValue())))
            .andExpect(jsonPath("$.[*].taux").value(hasItem(DEFAULT_TAUX.intValue())))
            .andExpect(jsonPath("$.[*].pourcent").value(hasItem(DEFAULT_POURCENT.intValue())));
    }
    
    @Test
    @Transactional
    public void getTva() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get the tva
        restTvaMockMvc.perform(get("/api/tvas/{id}", tva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tva.getId().intValue()))
            .andExpect(jsonPath("$.taux").value(DEFAULT_TAUX.intValue()))
            .andExpect(jsonPath("$.pourcent").value(DEFAULT_POURCENT.intValue()));
    }


    @Test
    @Transactional
    public void getTvasByIdFiltering() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        Long id = tva.getId();

        defaultTvaShouldBeFound("id.equals=" + id);
        defaultTvaShouldNotBeFound("id.notEquals=" + id);

        defaultTvaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTvaShouldNotBeFound("id.greaterThan=" + id);

        defaultTvaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTvaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTvasByTauxIsEqualToSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where taux equals to DEFAULT_TAUX
        defaultTvaShouldBeFound("taux.equals=" + DEFAULT_TAUX);

        // Get all the tvaList where taux equals to UPDATED_TAUX
        defaultTvaShouldNotBeFound("taux.equals=" + UPDATED_TAUX);
    }

    @Test
    @Transactional
    public void getAllTvasByTauxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where taux not equals to DEFAULT_TAUX
        defaultTvaShouldNotBeFound("taux.notEquals=" + DEFAULT_TAUX);

        // Get all the tvaList where taux not equals to UPDATED_TAUX
        defaultTvaShouldBeFound("taux.notEquals=" + UPDATED_TAUX);
    }

    @Test
    @Transactional
    public void getAllTvasByTauxIsInShouldWork() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where taux in DEFAULT_TAUX or UPDATED_TAUX
        defaultTvaShouldBeFound("taux.in=" + DEFAULT_TAUX + "," + UPDATED_TAUX);

        // Get all the tvaList where taux equals to UPDATED_TAUX
        defaultTvaShouldNotBeFound("taux.in=" + UPDATED_TAUX);
    }

    @Test
    @Transactional
    public void getAllTvasByTauxIsNullOrNotNull() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where taux is not null
        defaultTvaShouldBeFound("taux.specified=true");

        // Get all the tvaList where taux is null
        defaultTvaShouldNotBeFound("taux.specified=false");
    }

    @Test
    @Transactional
    public void getAllTvasByTauxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where taux is greater than or equal to DEFAULT_TAUX
        defaultTvaShouldBeFound("taux.greaterThanOrEqual=" + DEFAULT_TAUX);

        // Get all the tvaList where taux is greater than or equal to UPDATED_TAUX
        defaultTvaShouldNotBeFound("taux.greaterThanOrEqual=" + UPDATED_TAUX);
    }

    @Test
    @Transactional
    public void getAllTvasByTauxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where taux is less than or equal to DEFAULT_TAUX
        defaultTvaShouldBeFound("taux.lessThanOrEqual=" + DEFAULT_TAUX);

        // Get all the tvaList where taux is less than or equal to SMALLER_TAUX
        defaultTvaShouldNotBeFound("taux.lessThanOrEqual=" + SMALLER_TAUX);
    }

    @Test
    @Transactional
    public void getAllTvasByTauxIsLessThanSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where taux is less than DEFAULT_TAUX
        defaultTvaShouldNotBeFound("taux.lessThan=" + DEFAULT_TAUX);

        // Get all the tvaList where taux is less than UPDATED_TAUX
        defaultTvaShouldBeFound("taux.lessThan=" + UPDATED_TAUX);
    }

    @Test
    @Transactional
    public void getAllTvasByTauxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where taux is greater than DEFAULT_TAUX
        defaultTvaShouldNotBeFound("taux.greaterThan=" + DEFAULT_TAUX);

        // Get all the tvaList where taux is greater than SMALLER_TAUX
        defaultTvaShouldBeFound("taux.greaterThan=" + SMALLER_TAUX);
    }


    @Test
    @Transactional
    public void getAllTvasByPourcentIsEqualToSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where pourcent equals to DEFAULT_POURCENT
        defaultTvaShouldBeFound("pourcent.equals=" + DEFAULT_POURCENT);

        // Get all the tvaList where pourcent equals to UPDATED_POURCENT
        defaultTvaShouldNotBeFound("pourcent.equals=" + UPDATED_POURCENT);
    }

    @Test
    @Transactional
    public void getAllTvasByPourcentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where pourcent not equals to DEFAULT_POURCENT
        defaultTvaShouldNotBeFound("pourcent.notEquals=" + DEFAULT_POURCENT);

        // Get all the tvaList where pourcent not equals to UPDATED_POURCENT
        defaultTvaShouldBeFound("pourcent.notEquals=" + UPDATED_POURCENT);
    }

    @Test
    @Transactional
    public void getAllTvasByPourcentIsInShouldWork() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where pourcent in DEFAULT_POURCENT or UPDATED_POURCENT
        defaultTvaShouldBeFound("pourcent.in=" + DEFAULT_POURCENT + "," + UPDATED_POURCENT);

        // Get all the tvaList where pourcent equals to UPDATED_POURCENT
        defaultTvaShouldNotBeFound("pourcent.in=" + UPDATED_POURCENT);
    }

    @Test
    @Transactional
    public void getAllTvasByPourcentIsNullOrNotNull() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where pourcent is not null
        defaultTvaShouldBeFound("pourcent.specified=true");

        // Get all the tvaList where pourcent is null
        defaultTvaShouldNotBeFound("pourcent.specified=false");
    }

    @Test
    @Transactional
    public void getAllTvasByPourcentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where pourcent is greater than or equal to DEFAULT_POURCENT
        defaultTvaShouldBeFound("pourcent.greaterThanOrEqual=" + DEFAULT_POURCENT);

        // Get all the tvaList where pourcent is greater than or equal to UPDATED_POURCENT
        defaultTvaShouldNotBeFound("pourcent.greaterThanOrEqual=" + UPDATED_POURCENT);
    }

    @Test
    @Transactional
    public void getAllTvasByPourcentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where pourcent is less than or equal to DEFAULT_POURCENT
        defaultTvaShouldBeFound("pourcent.lessThanOrEqual=" + DEFAULT_POURCENT);

        // Get all the tvaList where pourcent is less than or equal to SMALLER_POURCENT
        defaultTvaShouldNotBeFound("pourcent.lessThanOrEqual=" + SMALLER_POURCENT);
    }

    @Test
    @Transactional
    public void getAllTvasByPourcentIsLessThanSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where pourcent is less than DEFAULT_POURCENT
        defaultTvaShouldNotBeFound("pourcent.lessThan=" + DEFAULT_POURCENT);

        // Get all the tvaList where pourcent is less than UPDATED_POURCENT
        defaultTvaShouldBeFound("pourcent.lessThan=" + UPDATED_POURCENT);
    }

    @Test
    @Transactional
    public void getAllTvasByPourcentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        // Get all the tvaList where pourcent is greater than DEFAULT_POURCENT
        defaultTvaShouldNotBeFound("pourcent.greaterThan=" + DEFAULT_POURCENT);

        // Get all the tvaList where pourcent is greater than SMALLER_POURCENT
        defaultTvaShouldBeFound("pourcent.greaterThan=" + SMALLER_POURCENT);
    }


    @Test
    @Transactional
    public void getAllTvasByOffreIsEqualToSomething() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);
        Offre offre = OffreResourceIT.createEntity(em);
        em.persist(offre);
        em.flush();
        tva.setOffre(offre);
        tvaRepository.saveAndFlush(tva);
        Long offreId = offre.getId();

        // Get all the tvaList where offre equals to offreId
        defaultTvaShouldBeFound("offreId.equals=" + offreId);

        // Get all the tvaList where offre equals to offreId + 1
        defaultTvaShouldNotBeFound("offreId.equals=" + (offreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTvaShouldBeFound(String filter) throws Exception {
        restTvaMockMvc.perform(get("/api/tvas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tva.getId().intValue())))
            .andExpect(jsonPath("$.[*].taux").value(hasItem(DEFAULT_TAUX.intValue())))
            .andExpect(jsonPath("$.[*].pourcent").value(hasItem(DEFAULT_POURCENT.intValue())));

        // Check, that the count call also returns 1
        restTvaMockMvc.perform(get("/api/tvas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTvaShouldNotBeFound(String filter) throws Exception {
        restTvaMockMvc.perform(get("/api/tvas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTvaMockMvc.perform(get("/api/tvas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTva() throws Exception {
        // Get the tva
        restTvaMockMvc.perform(get("/api/tvas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTva() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();

        // Update the tva
        Tva updatedTva = tvaRepository.findById(tva.getId()).get();
        // Disconnect from session so that the updates on updatedTva are not directly saved in db
        em.detach(updatedTva);
        updatedTva
            .taux(UPDATED_TAUX)
            .pourcent(UPDATED_POURCENT);
        TvaDTO tvaDTO = tvaMapper.toDto(updatedTva);

        restTvaMockMvc.perform(put("/api/tvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isOk());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);
        Tva testTva = tvaList.get(tvaList.size() - 1);
        assertThat(testTva.getTaux()).isEqualTo(UPDATED_TAUX);
        assertThat(testTva.getPourcent()).isEqualTo(UPDATED_POURCENT);

        // Validate the Tva in Elasticsearch
        verify(mockTvaSearchRepository, times(1)).save(testTva);
    }

    @Test
    @Transactional
    public void updateNonExistingTva() throws Exception {
        int databaseSizeBeforeUpdate = tvaRepository.findAll().size();

        // Create the Tva
        TvaDTO tvaDTO = tvaMapper.toDto(tva);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTvaMockMvc.perform(put("/api/tvas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tvaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tva in the database
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Tva in Elasticsearch
        verify(mockTvaSearchRepository, times(0)).save(tva);
    }

    @Test
    @Transactional
    public void deleteTva() throws Exception {
        // Initialize the database
        tvaRepository.saveAndFlush(tva);

        int databaseSizeBeforeDelete = tvaRepository.findAll().size();

        // Delete the tva
        restTvaMockMvc.perform(delete("/api/tvas/{id}", tva.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tva> tvaList = tvaRepository.findAll();
        assertThat(tvaList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Tva in Elasticsearch
        verify(mockTvaSearchRepository, times(1)).deleteById(tva.getId());
    }

    @Test
    @Transactional
    public void searchTva() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        tvaRepository.saveAndFlush(tva);
        when(mockTvaSearchRepository.search(queryStringQuery("id:" + tva.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(tva), PageRequest.of(0, 1), 1));

        // Search the tva
        restTvaMockMvc.perform(get("/api/_search/tvas?query=id:" + tva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tva.getId().intValue())))
            .andExpect(jsonPath("$.[*].taux").value(hasItem(DEFAULT_TAUX.intValue())))
            .andExpect(jsonPath("$.[*].pourcent").value(hasItem(DEFAULT_POURCENT.intValue())));
    }
}
