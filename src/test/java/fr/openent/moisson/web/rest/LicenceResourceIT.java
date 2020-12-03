package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.Licence;
import fr.openent.moisson.domain.Offre;
import fr.openent.moisson.domain.Lep;
import fr.openent.moisson.repository.LicenceRepository;
import fr.openent.moisson.repository.search.LicenceSearchRepository;
import fr.openent.moisson.service.LicenceService;
import fr.openent.moisson.service.dto.LicenceDTO;
import fr.openent.moisson.service.mapper.LicenceMapper;
import fr.openent.moisson.service.dto.LicenceCriteria;
import fr.openent.moisson.service.LicenceQueryService;

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
 * Integration tests for the {@link LicenceResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LicenceResourceIT {

    private static final String DEFAULT_VALEUR = "AAAAAAAAAA";
    private static final String UPDATED_VALEUR = "BBBBBBBBBB";

    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private LicenceMapper licenceMapper;

    @Autowired
    private LicenceService licenceService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.LicenceSearchRepositoryMockConfiguration
     */
    @Autowired
    private LicenceSearchRepository mockLicenceSearchRepository;

    @Autowired
    private LicenceQueryService licenceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLicenceMockMvc;

    private Licence licence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Licence createEntity(EntityManager em) {
        Licence licence = new Licence()
            .valeur(DEFAULT_VALEUR);
        return licence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Licence createUpdatedEntity(EntityManager em) {
        Licence licence = new Licence()
            .valeur(UPDATED_VALEUR);
        return licence;
    }

    @BeforeEach
    public void initTest() {
        licence = createEntity(em);
    }

    @Test
    @Transactional
    public void createLicence() throws Exception {
        int databaseSizeBeforeCreate = licenceRepository.findAll().size();
        // Create the Licence
        LicenceDTO licenceDTO = licenceMapper.toDto(licence);
        restLicenceMockMvc.perform(post("/api/licences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeCreate + 1);
        Licence testLicence = licenceList.get(licenceList.size() - 1);
        assertThat(testLicence.getValeur()).isEqualTo(DEFAULT_VALEUR);

        // Validate the Licence in Elasticsearch
        verify(mockLicenceSearchRepository, times(1)).save(testLicence);
    }

    @Test
    @Transactional
    public void createLicenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = licenceRepository.findAll().size();

        // Create the Licence with an existing ID
        licence.setId(1L);
        LicenceDTO licenceDTO = licenceMapper.toDto(licence);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenceMockMvc.perform(post("/api/licences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeCreate);

        // Validate the Licence in Elasticsearch
        verify(mockLicenceSearchRepository, times(0)).save(licence);
    }


    @Test
    @Transactional
    public void getAllLicences() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get all the licenceList
        restLicenceMockMvc.perform(get("/api/licences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licence.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR)));
    }
    
    @Test
    @Transactional
    public void getLicence() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get the licence
        restLicenceMockMvc.perform(get("/api/licences/{id}", licence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(licence.getId().intValue()))
            .andExpect(jsonPath("$.valeur").value(DEFAULT_VALEUR));
    }


    @Test
    @Transactional
    public void getLicencesByIdFiltering() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        Long id = licence.getId();

        defaultLicenceShouldBeFound("id.equals=" + id);
        defaultLicenceShouldNotBeFound("id.notEquals=" + id);

        defaultLicenceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLicenceShouldNotBeFound("id.greaterThan=" + id);

        defaultLicenceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLicenceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLicencesByValeurIsEqualToSomething() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get all the licenceList where valeur equals to DEFAULT_VALEUR
        defaultLicenceShouldBeFound("valeur.equals=" + DEFAULT_VALEUR);

        // Get all the licenceList where valeur equals to UPDATED_VALEUR
        defaultLicenceShouldNotBeFound("valeur.equals=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllLicencesByValeurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get all the licenceList where valeur not equals to DEFAULT_VALEUR
        defaultLicenceShouldNotBeFound("valeur.notEquals=" + DEFAULT_VALEUR);

        // Get all the licenceList where valeur not equals to UPDATED_VALEUR
        defaultLicenceShouldBeFound("valeur.notEquals=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllLicencesByValeurIsInShouldWork() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get all the licenceList where valeur in DEFAULT_VALEUR or UPDATED_VALEUR
        defaultLicenceShouldBeFound("valeur.in=" + DEFAULT_VALEUR + "," + UPDATED_VALEUR);

        // Get all the licenceList where valeur equals to UPDATED_VALEUR
        defaultLicenceShouldNotBeFound("valeur.in=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllLicencesByValeurIsNullOrNotNull() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get all the licenceList where valeur is not null
        defaultLicenceShouldBeFound("valeur.specified=true");

        // Get all the licenceList where valeur is null
        defaultLicenceShouldNotBeFound("valeur.specified=false");
    }
                @Test
    @Transactional
    public void getAllLicencesByValeurContainsSomething() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get all the licenceList where valeur contains DEFAULT_VALEUR
        defaultLicenceShouldBeFound("valeur.contains=" + DEFAULT_VALEUR);

        // Get all the licenceList where valeur contains UPDATED_VALEUR
        defaultLicenceShouldNotBeFound("valeur.contains=" + UPDATED_VALEUR);
    }

    @Test
    @Transactional
    public void getAllLicencesByValeurNotContainsSomething() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get all the licenceList where valeur does not contain DEFAULT_VALEUR
        defaultLicenceShouldNotBeFound("valeur.doesNotContain=" + DEFAULT_VALEUR);

        // Get all the licenceList where valeur does not contain UPDATED_VALEUR
        defaultLicenceShouldBeFound("valeur.doesNotContain=" + UPDATED_VALEUR);
    }


    @Test
    @Transactional
    public void getAllLicencesByOffreIsEqualToSomething() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);
        Offre offre = OffreResourceIT.createEntity(em);
        em.persist(offre);
        em.flush();
        licence.setOffre(offre);
        offre.setLicence(licence);
        licenceRepository.saveAndFlush(licence);
        Long offreId = offre.getId();

        // Get all the licenceList where offre equals to offreId
        defaultLicenceShouldBeFound("offreId.equals=" + offreId);

        // Get all the licenceList where offre equals to offreId + 1
        defaultLicenceShouldNotBeFound("offreId.equals=" + (offreId + 1));
    }


    @Test
    @Transactional
    public void getAllLicencesByLepIsEqualToSomething() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);
        Lep lep = LepResourceIT.createEntity(em);
        em.persist(lep);
        em.flush();
        licence.setLep(lep);
        lep.setLicence(licence);
        licenceRepository.saveAndFlush(licence);
        Long lepId = lep.getId();

        // Get all the licenceList where lep equals to lepId
        defaultLicenceShouldBeFound("lepId.equals=" + lepId);

        // Get all the licenceList where lep equals to lepId + 1
        defaultLicenceShouldNotBeFound("lepId.equals=" + (lepId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLicenceShouldBeFound(String filter) throws Exception {
        restLicenceMockMvc.perform(get("/api/licences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licence.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR)));

        // Check, that the count call also returns 1
        restLicenceMockMvc.perform(get("/api/licences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLicenceShouldNotBeFound(String filter) throws Exception {
        restLicenceMockMvc.perform(get("/api/licences?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLicenceMockMvc.perform(get("/api/licences/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLicence() throws Exception {
        // Get the licence
        restLicenceMockMvc.perform(get("/api/licences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicence() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        int databaseSizeBeforeUpdate = licenceRepository.findAll().size();

        // Update the licence
        Licence updatedLicence = licenceRepository.findById(licence.getId()).get();
        // Disconnect from session so that the updates on updatedLicence are not directly saved in db
        em.detach(updatedLicence);
        updatedLicence
            .valeur(UPDATED_VALEUR);
        LicenceDTO licenceDTO = licenceMapper.toDto(updatedLicence);

        restLicenceMockMvc.perform(put("/api/licences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenceDTO)))
            .andExpect(status().isOk());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeUpdate);
        Licence testLicence = licenceList.get(licenceList.size() - 1);
        assertThat(testLicence.getValeur()).isEqualTo(UPDATED_VALEUR);

        // Validate the Licence in Elasticsearch
        verify(mockLicenceSearchRepository, times(1)).save(testLicence);
    }

    @Test
    @Transactional
    public void updateNonExistingLicence() throws Exception {
        int databaseSizeBeforeUpdate = licenceRepository.findAll().size();

        // Create the Licence
        LicenceDTO licenceDTO = licenceMapper.toDto(licence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenceMockMvc.perform(put("/api/licences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(licenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Licence in Elasticsearch
        verify(mockLicenceSearchRepository, times(0)).save(licence);
    }

    @Test
    @Transactional
    public void deleteLicence() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        int databaseSizeBeforeDelete = licenceRepository.findAll().size();

        // Delete the licence
        restLicenceMockMvc.perform(delete("/api/licences/{id}", licence.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Licence in Elasticsearch
        verify(mockLicenceSearchRepository, times(1)).deleteById(licence.getId());
    }

    @Test
    @Transactional
    public void searchLicence() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        licenceRepository.saveAndFlush(licence);
        when(mockLicenceSearchRepository.search(queryStringQuery("id:" + licence.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(licence), PageRequest.of(0, 1), 1));

        // Search the licence
        restLicenceMockMvc.perform(get("/api/_search/licences?query=id:" + licence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licence.getId().intValue())))
            .andExpect(jsonPath("$.[*].valeur").value(hasItem(DEFAULT_VALEUR)));
    }
}
