package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.Lep;
import fr.openent.moisson.domain.Condition;
import fr.openent.moisson.domain.Offre;
import fr.openent.moisson.domain.Licence;
import fr.openent.moisson.repository.LepRepository;
import fr.openent.moisson.repository.search.LepSearchRepository;
import fr.openent.moisson.service.LepService;
import fr.openent.moisson.service.dto.LepDTO;
import fr.openent.moisson.service.mapper.LepMapper;
import fr.openent.moisson.service.dto.LepCriteria;
import fr.openent.moisson.service.LepQueryService;

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
 * Integration tests for the {@link LepResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LepResourceIT {

    private static final String DEFAULT_EAN = "AAAAAAAAAAAAA";
    private static final String UPDATED_EAN = "BBBBBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TITRE = "AAAAAAAAAA";
    private static final String UPDATED_TITRE = "BBBBBBBBBB";

    private static final String DEFAULT_DUREE = "AAAAAAAAAA";
    private static final String UPDATED_DUREE = "BBBBBBBBBB";

    @Autowired
    private LepRepository lepRepository;

    @Autowired
    private LepMapper lepMapper;

    @Autowired
    private LepService lepService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.LepSearchRepositoryMockConfiguration
     */
    @Autowired
    private LepSearchRepository mockLepSearchRepository;

    @Autowired
    private LepQueryService lepQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLepMockMvc;

    private Lep lep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lep createEntity(EntityManager em) {
        Lep lep = new Lep()
            .ean(DEFAULT_EAN)
            .description(DEFAULT_DESCRIPTION)
            .titre(DEFAULT_TITRE)
            .duree(DEFAULT_DUREE);
        // Add required entity
        Licence licence;
        if (TestUtil.findAll(em, Licence.class).isEmpty()) {
            licence = LicenceResourceIT.createEntity(em);
            em.persist(licence);
            em.flush();
        } else {
            licence = TestUtil.findAll(em, Licence.class).get(0);
        }
        lep.setLicence(licence);
        return lep;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Lep createUpdatedEntity(EntityManager em) {
        Lep lep = new Lep()
            .ean(UPDATED_EAN)
            .description(UPDATED_DESCRIPTION)
            .titre(UPDATED_TITRE)
            .duree(UPDATED_DUREE);
        // Add required entity
        Licence licence;
        if (TestUtil.findAll(em, Licence.class).isEmpty()) {
            licence = LicenceResourceIT.createUpdatedEntity(em);
            em.persist(licence);
            em.flush();
        } else {
            licence = TestUtil.findAll(em, Licence.class).get(0);
        }
        lep.setLicence(licence);
        return lep;
    }

    @BeforeEach
    public void initTest() {
        lep = createEntity(em);
    }

    @Test
    @Transactional
    public void createLep() throws Exception {
        int databaseSizeBeforeCreate = lepRepository.findAll().size();
        // Create the Lep
        LepDTO lepDTO = lepMapper.toDto(lep);
        restLepMockMvc.perform(post("/api/leps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lepDTO)))
            .andExpect(status().isCreated());

        // Validate the Lep in the database
        List<Lep> lepList = lepRepository.findAll();
        assertThat(lepList).hasSize(databaseSizeBeforeCreate + 1);
        Lep testLep = lepList.get(lepList.size() - 1);
        assertThat(testLep.getEan()).isEqualTo(DEFAULT_EAN);
        assertThat(testLep.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLep.getTitre()).isEqualTo(DEFAULT_TITRE);
        assertThat(testLep.getDuree()).isEqualTo(DEFAULT_DUREE);

        // Validate the id for MapsId, the ids must be same
        assertThat(testLep.getId()).isEqualTo(testLep.getLicence().getId());

        // Validate the Lep in Elasticsearch
        verify(mockLepSearchRepository, times(1)).save(testLep);
    }

    @Test
    @Transactional
    public void createLepWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lepRepository.findAll().size();

        // Create the Lep with an existing ID
        lep.setId(1L);
        LepDTO lepDTO = lepMapper.toDto(lep);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLepMockMvc.perform(post("/api/leps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lep in the database
        List<Lep> lepList = lepRepository.findAll();
        assertThat(lepList).hasSize(databaseSizeBeforeCreate);

        // Validate the Lep in Elasticsearch
        verify(mockLepSearchRepository, times(0)).save(lep);
    }

    @Test
    @Transactional
    public void updateLepMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);
        int databaseSizeBeforeCreate = lepRepository.findAll().size();


        // Load the lep
        Lep updatedLep = lepRepository.findById(lep.getId()).get();
        // Disconnect from session so that the updates on updatedLep are not directly saved in db
        em.detach(updatedLep);

        // Update the Licence with new association value
        updatedLep.setLicence(lep.getLicence());
        LepDTO updatedLepDTO = lepMapper.toDto(updatedLep);

        // Update the entity
        restLepMockMvc.perform(put("/api/leps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLepDTO)))
            .andExpect(status().isOk());

        // Validate the Lep in the database
        List<Lep> lepList = lepRepository.findAll();
        assertThat(lepList).hasSize(databaseSizeBeforeCreate);
        Lep testLep = lepList.get(lepList.size() - 1);

        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testLep.getId()).isEqualTo(testLep.getLicence().getId());

        // Validate the Lep in Elasticsearch
        verify(mockLepSearchRepository, times(1)).save(lep);
    }

    @Test
    @Transactional
    public void getAllLeps() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList
        restLepMockMvc.perform(get("/api/leps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lep.getId().intValue())))
            .andExpect(jsonPath("$.[*].ean").value(hasItem(DEFAULT_EAN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)));
    }

    @Test
    @Transactional
    public void getLep() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get the lep
        restLepMockMvc.perform(get("/api/leps/{id}", lep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(lep.getId().intValue()))
            .andExpect(jsonPath("$.ean").value(DEFAULT_EAN))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.titre").value(DEFAULT_TITRE))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE));
    }


    @Test
    @Transactional
    public void getLepsByIdFiltering() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        Long id = lep.getId();

        defaultLepShouldBeFound("id.equals=" + id);
        defaultLepShouldNotBeFound("id.notEquals=" + id);

        defaultLepShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLepShouldNotBeFound("id.greaterThan=" + id);

        defaultLepShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLepShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLepsByEanIsEqualToSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where ean equals to DEFAULT_EAN
        defaultLepShouldBeFound("ean.equals=" + DEFAULT_EAN);

        // Get all the lepList where ean equals to UPDATED_EAN
        defaultLepShouldNotBeFound("ean.equals=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllLepsByEanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where ean not equals to DEFAULT_EAN
        defaultLepShouldNotBeFound("ean.notEquals=" + DEFAULT_EAN);

        // Get all the lepList where ean not equals to UPDATED_EAN
        defaultLepShouldBeFound("ean.notEquals=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllLepsByEanIsInShouldWork() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where ean in DEFAULT_EAN or UPDATED_EAN
        defaultLepShouldBeFound("ean.in=" + DEFAULT_EAN + "," + UPDATED_EAN);

        // Get all the lepList where ean equals to UPDATED_EAN
        defaultLepShouldNotBeFound("ean.in=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllLepsByEanIsNullOrNotNull() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where ean is not null
        defaultLepShouldBeFound("ean.specified=true");

        // Get all the lepList where ean is null
        defaultLepShouldNotBeFound("ean.specified=false");
    }
                @Test
    @Transactional
    public void getAllLepsByEanContainsSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where ean contains DEFAULT_EAN
        defaultLepShouldBeFound("ean.contains=" + DEFAULT_EAN);

        // Get all the lepList where ean contains UPDATED_EAN
        defaultLepShouldNotBeFound("ean.contains=" + UPDATED_EAN);
    }

    @Test
    @Transactional
    public void getAllLepsByEanNotContainsSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where ean does not contain DEFAULT_EAN
        defaultLepShouldNotBeFound("ean.doesNotContain=" + DEFAULT_EAN);

        // Get all the lepList where ean does not contain UPDATED_EAN
        defaultLepShouldBeFound("ean.doesNotContain=" + UPDATED_EAN);
    }


    @Test
    @Transactional
    public void getAllLepsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where description equals to DEFAULT_DESCRIPTION
        defaultLepShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the lepList where description equals to UPDATED_DESCRIPTION
        defaultLepShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLepsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where description not equals to DEFAULT_DESCRIPTION
        defaultLepShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the lepList where description not equals to UPDATED_DESCRIPTION
        defaultLepShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLepsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultLepShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the lepList where description equals to UPDATED_DESCRIPTION
        defaultLepShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLepsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where description is not null
        defaultLepShouldBeFound("description.specified=true");

        // Get all the lepList where description is null
        defaultLepShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllLepsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where description contains DEFAULT_DESCRIPTION
        defaultLepShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the lepList where description contains UPDATED_DESCRIPTION
        defaultLepShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllLepsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where description does not contain DEFAULT_DESCRIPTION
        defaultLepShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the lepList where description does not contain UPDATED_DESCRIPTION
        defaultLepShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllLepsByTitreIsEqualToSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where titre equals to DEFAULT_TITRE
        defaultLepShouldBeFound("titre.equals=" + DEFAULT_TITRE);

        // Get all the lepList where titre equals to UPDATED_TITRE
        defaultLepShouldNotBeFound("titre.equals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllLepsByTitreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where titre not equals to DEFAULT_TITRE
        defaultLepShouldNotBeFound("titre.notEquals=" + DEFAULT_TITRE);

        // Get all the lepList where titre not equals to UPDATED_TITRE
        defaultLepShouldBeFound("titre.notEquals=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllLepsByTitreIsInShouldWork() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where titre in DEFAULT_TITRE or UPDATED_TITRE
        defaultLepShouldBeFound("titre.in=" + DEFAULT_TITRE + "," + UPDATED_TITRE);

        // Get all the lepList where titre equals to UPDATED_TITRE
        defaultLepShouldNotBeFound("titre.in=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllLepsByTitreIsNullOrNotNull() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where titre is not null
        defaultLepShouldBeFound("titre.specified=true");

        // Get all the lepList where titre is null
        defaultLepShouldNotBeFound("titre.specified=false");
    }
                @Test
    @Transactional
    public void getAllLepsByTitreContainsSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where titre contains DEFAULT_TITRE
        defaultLepShouldBeFound("titre.contains=" + DEFAULT_TITRE);

        // Get all the lepList where titre contains UPDATED_TITRE
        defaultLepShouldNotBeFound("titre.contains=" + UPDATED_TITRE);
    }

    @Test
    @Transactional
    public void getAllLepsByTitreNotContainsSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where titre does not contain DEFAULT_TITRE
        defaultLepShouldNotBeFound("titre.doesNotContain=" + DEFAULT_TITRE);

        // Get all the lepList where titre does not contain UPDATED_TITRE
        defaultLepShouldBeFound("titre.doesNotContain=" + UPDATED_TITRE);
    }


    @Test
    @Transactional
    public void getAllLepsByDureeIsEqualToSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where duree equals to DEFAULT_DUREE
        defaultLepShouldBeFound("duree.equals=" + DEFAULT_DUREE);

        // Get all the lepList where duree equals to UPDATED_DUREE
        defaultLepShouldNotBeFound("duree.equals=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllLepsByDureeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where duree not equals to DEFAULT_DUREE
        defaultLepShouldNotBeFound("duree.notEquals=" + DEFAULT_DUREE);

        // Get all the lepList where duree not equals to UPDATED_DUREE
        defaultLepShouldBeFound("duree.notEquals=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllLepsByDureeIsInShouldWork() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where duree in DEFAULT_DUREE or UPDATED_DUREE
        defaultLepShouldBeFound("duree.in=" + DEFAULT_DUREE + "," + UPDATED_DUREE);

        // Get all the lepList where duree equals to UPDATED_DUREE
        defaultLepShouldNotBeFound("duree.in=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllLepsByDureeIsNullOrNotNull() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where duree is not null
        defaultLepShouldBeFound("duree.specified=true");

        // Get all the lepList where duree is null
        defaultLepShouldNotBeFound("duree.specified=false");
    }
                @Test
    @Transactional
    public void getAllLepsByDureeContainsSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where duree contains DEFAULT_DUREE
        defaultLepShouldBeFound("duree.contains=" + DEFAULT_DUREE);

        // Get all the lepList where duree contains UPDATED_DUREE
        defaultLepShouldNotBeFound("duree.contains=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllLepsByDureeNotContainsSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        // Get all the lepList where duree does not contain DEFAULT_DUREE
        defaultLepShouldNotBeFound("duree.doesNotContain=" + DEFAULT_DUREE);

        // Get all the lepList where duree does not contain UPDATED_DUREE
        defaultLepShouldBeFound("duree.doesNotContain=" + UPDATED_DUREE);
    }


    @Test
    @Transactional
    public void getAllLepsByConditionIsEqualToSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);
        Condition condition = ConditionResourceIT.createEntity(em);
        em.persist(condition);
        em.flush();
        lep.addCondition(condition);
        lepRepository.saveAndFlush(lep);
        Long conditionId = condition.getId();

        // Get all the lepList where condition equals to conditionId
        defaultLepShouldBeFound("conditionId.equals=" + conditionId);

        // Get all the lepList where condition equals to conditionId + 1
        defaultLepShouldNotBeFound("conditionId.equals=" + (conditionId + 1));
    }


    @Test
    @Transactional
    public void getAllLepsByOffreIsEqualToSomething() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);
        Offre offre = OffreResourceIT.createEntity(em);
        em.persist(offre);
        em.flush();
        lep.setOffre(offre);
        lepRepository.saveAndFlush(lep);
        Long offreId = offre.getId();

        // Get all the lepList where offre equals to offreId
        defaultLepShouldBeFound("offreId.equals=" + offreId);

        // Get all the lepList where offre equals to offreId + 1
        defaultLepShouldNotBeFound("offreId.equals=" + (offreId + 1));
    }


    @Test
    @Transactional
    public void getAllLepsByLicenceIsEqualToSomething() throws Exception {
        // Get already existing entity
        Licence licence = lep.getLicence();
        lepRepository.saveAndFlush(lep);
        Long licenceId = licence.getId();

        // Get all the lepList where licence equals to licenceId
        defaultLepShouldBeFound("licenceId.equals=" + licenceId);

        // Get all the lepList where licence equals to licenceId + 1
        defaultLepShouldNotBeFound("licenceId.equals=" + (licenceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLepShouldBeFound(String filter) throws Exception {
        restLepMockMvc.perform(get("/api/leps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lep.getId().intValue())))
            .andExpect(jsonPath("$.[*].ean").value(hasItem(DEFAULT_EAN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)));

        // Check, that the count call also returns 1
        restLepMockMvc.perform(get("/api/leps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLepShouldNotBeFound(String filter) throws Exception {
        restLepMockMvc.perform(get("/api/leps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLepMockMvc.perform(get("/api/leps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingLep() throws Exception {
        // Get the lep
        restLepMockMvc.perform(get("/api/leps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLep() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        int databaseSizeBeforeUpdate = lepRepository.findAll().size();

        // Update the lep
        Lep updatedLep = lepRepository.findById(lep.getId()).get();
        // Disconnect from session so that the updates on updatedLep are not directly saved in db
        em.detach(updatedLep);
        updatedLep
            .ean(UPDATED_EAN)
            .description(UPDATED_DESCRIPTION)
            .titre(UPDATED_TITRE)
            .duree(UPDATED_DUREE);
        LepDTO lepDTO = lepMapper.toDto(updatedLep);

        restLepMockMvc.perform(put("/api/leps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lepDTO)))
            .andExpect(status().isOk());

        // Validate the Lep in the database
        List<Lep> lepList = lepRepository.findAll();
        assertThat(lepList).hasSize(databaseSizeBeforeUpdate);
        Lep testLep = lepList.get(lepList.size() - 1);
        assertThat(testLep.getEan()).isEqualTo(UPDATED_EAN);
        assertThat(testLep.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLep.getTitre()).isEqualTo(UPDATED_TITRE);
        assertThat(testLep.getDuree()).isEqualTo(UPDATED_DUREE);

        // Validate the Lep in Elasticsearch
        verify(mockLepSearchRepository, times(1)).save(testLep);
    }

    @Test
    @Transactional
    public void updateNonExistingLep() throws Exception {
        int databaseSizeBeforeUpdate = lepRepository.findAll().size();

        // Create the Lep
        LepDTO lepDTO = lepMapper.toDto(lep);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLepMockMvc.perform(put("/api/leps")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(lepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Lep in the database
        List<Lep> lepList = lepRepository.findAll();
        assertThat(lepList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Lep in Elasticsearch
        verify(mockLepSearchRepository, times(0)).save(lep);
    }

    @Test
    @Transactional
    public void deleteLep() throws Exception {
        // Initialize the database
        lepRepository.saveAndFlush(lep);

        int databaseSizeBeforeDelete = lepRepository.findAll().size();

        // Delete the lep
        restLepMockMvc.perform(delete("/api/leps/{id}", lep.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Lep> lepList = lepRepository.findAll();
        assertThat(lepList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Lep in Elasticsearch
        verify(mockLepSearchRepository, times(1)).deleteById(lep.getId());
    }

    @Test
    @Transactional
    public void searchLep() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        lepRepository.saveAndFlush(lep);
        when(mockLepSearchRepository.search(queryStringQuery("id:" + lep.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(lep), PageRequest.of(0, 1), 1));

        // Search the lep
        restLepMockMvc.perform(get("/api/_search/leps?query=id:" + lep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lep.getId().intValue())))
            .andExpect(jsonPath("$.[*].ean").value(hasItem(DEFAULT_EAN)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].titre").value(hasItem(DEFAULT_TITRE)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)));
    }
}
