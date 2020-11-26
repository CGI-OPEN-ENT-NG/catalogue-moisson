package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.Offre;
import fr.openent.moisson.domain.Tva;
import fr.openent.moisson.domain.Lep;
import fr.openent.moisson.repository.OffreRepository;
import fr.openent.moisson.repository.search.OffreSearchRepository;
import fr.openent.moisson.service.OffreService;
import fr.openent.moisson.service.dto.OffreDTO;
import fr.openent.moisson.service.mapper.OffreMapper;
import fr.openent.moisson.service.dto.OffreCriteria;
import fr.openent.moisson.service.OffreQueryService;

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
 * Integration tests for the {@link OffreResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class OffreResourceIT {

    private static final String DEFAULT_EAN_LIBRAIRE = "AAAAAAAAAAAAA";
    private static final String UPDATED_EAN_LIBRAIRE = "BBBBBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DUREE = 1;
    private static final Integer UPDATED_DUREE = 2;
    private static final Integer SMALLER_DUREE = 1 - 1;

    private static final Boolean DEFAULT_ADOPTION = false;
    private static final Boolean UPDATED_ADOPTION = true;

    private static final Integer DEFAULT_QUANTITE_MINIMALE_ACHAT = 1;
    private static final Integer UPDATED_QUANTITE_MINIMALE_ACHAT = 2;
    private static final Integer SMALLER_QUANTITE_MINIMALE_ACHAT = 1 - 1;

    private static final String DEFAULT_LICENCE = "AAAAAAAAAA";
    private static final String UPDATED_LICENCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRESCRIPTEUR = false;
    private static final Boolean UPDATED_PRESCRIPTEUR = true;

    private static final String DEFAULT_LIBELLE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRIX_HT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRIX_HT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRIX_HT = new BigDecimal(1 - 1);

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private OffreMapper offreMapper;

    @Autowired
    private OffreService offreService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.OffreSearchRepositoryMockConfiguration
     */
    @Autowired
    private OffreSearchRepository mockOffreSearchRepository;

    @Autowired
    private OffreQueryService offreQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOffreMockMvc;

    private Offre offre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offre createEntity(EntityManager em) {
        Offre offre = new Offre()
            .eanLibraire(DEFAULT_EAN_LIBRAIRE)
            .reference(DEFAULT_REFERENCE)
            .duree(DEFAULT_DUREE)
            .adoption(DEFAULT_ADOPTION)
            .quantiteMinimaleAchat(DEFAULT_QUANTITE_MINIMALE_ACHAT)
            .licence(DEFAULT_LICENCE)
            .prescripteur(DEFAULT_PRESCRIPTEUR)
            .libelle(DEFAULT_LIBELLE)
            .prixHT(DEFAULT_PRIX_HT);
        return offre;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offre createUpdatedEntity(EntityManager em) {
        Offre offre = new Offre()
            .eanLibraire(UPDATED_EAN_LIBRAIRE)
            .reference(UPDATED_REFERENCE)
            .duree(UPDATED_DUREE)
            .adoption(UPDATED_ADOPTION)
            .quantiteMinimaleAchat(UPDATED_QUANTITE_MINIMALE_ACHAT)
            .licence(UPDATED_LICENCE)
            .prescripteur(UPDATED_PRESCRIPTEUR)
            .libelle(UPDATED_LIBELLE)
            .prixHT(UPDATED_PRIX_HT);
        return offre;
    }

    @BeforeEach
    public void initTest() {
        offre = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffre() throws Exception {
        int databaseSizeBeforeCreate = offreRepository.findAll().size();
        // Create the Offre
        OffreDTO offreDTO = offreMapper.toDto(offre);
        restOffreMockMvc.perform(post("/api/offres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isCreated());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate + 1);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getEanLibraire()).isEqualTo(DEFAULT_EAN_LIBRAIRE);
        assertThat(testOffre.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testOffre.getDuree()).isEqualTo(DEFAULT_DUREE);
        assertThat(testOffre.isAdoption()).isEqualTo(DEFAULT_ADOPTION);
        assertThat(testOffre.getQuantiteMinimaleAchat()).isEqualTo(DEFAULT_QUANTITE_MINIMALE_ACHAT);
        assertThat(testOffre.getLicence()).isEqualTo(DEFAULT_LICENCE);
        assertThat(testOffre.isPrescripteur()).isEqualTo(DEFAULT_PRESCRIPTEUR);
        assertThat(testOffre.getLibelle()).isEqualTo(DEFAULT_LIBELLE);
        assertThat(testOffre.getPrixHT()).isEqualTo(DEFAULT_PRIX_HT);

        // Validate the Offre in Elasticsearch
        verify(mockOffreSearchRepository, times(1)).save(testOffre);
    }

    @Test
    @Transactional
    public void createOffreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = offreRepository.findAll().size();

        // Create the Offre with an existing ID
        offre.setId(1L);
        OffreDTO offreDTO = offreMapper.toDto(offre);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOffreMockMvc.perform(post("/api/offres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeCreate);

        // Validate the Offre in Elasticsearch
        verify(mockOffreSearchRepository, times(0)).save(offre);
    }


    @Test
    @Transactional
    public void getAllOffres() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList
        restOffreMockMvc.perform(get("/api/offres?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offre.getId().intValue())))
            .andExpect(jsonPath("$.[*].eanLibraire").value(hasItem(DEFAULT_EAN_LIBRAIRE)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].adoption").value(hasItem(DEFAULT_ADOPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].quantiteMinimaleAchat").value(hasItem(DEFAULT_QUANTITE_MINIMALE_ACHAT)))
            .andExpect(jsonPath("$.[*].licence").value(hasItem(DEFAULT_LICENCE)))
            .andExpect(jsonPath("$.[*].prescripteur").value(hasItem(DEFAULT_PRESCRIPTEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.intValue())));
    }
    
    @Test
    @Transactional
    public void getOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get the offre
        restOffreMockMvc.perform(get("/api/offres/{id}", offre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(offre.getId().intValue()))
            .andExpect(jsonPath("$.eanLibraire").value(DEFAULT_EAN_LIBRAIRE))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE))
            .andExpect(jsonPath("$.duree").value(DEFAULT_DUREE))
            .andExpect(jsonPath("$.adoption").value(DEFAULT_ADOPTION.booleanValue()))
            .andExpect(jsonPath("$.quantiteMinimaleAchat").value(DEFAULT_QUANTITE_MINIMALE_ACHAT))
            .andExpect(jsonPath("$.licence").value(DEFAULT_LICENCE))
            .andExpect(jsonPath("$.prescripteur").value(DEFAULT_PRESCRIPTEUR.booleanValue()))
            .andExpect(jsonPath("$.libelle").value(DEFAULT_LIBELLE))
            .andExpect(jsonPath("$.prixHT").value(DEFAULT_PRIX_HT.intValue()));
    }


    @Test
    @Transactional
    public void getOffresByIdFiltering() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        Long id = offre.getId();

        defaultOffreShouldBeFound("id.equals=" + id);
        defaultOffreShouldNotBeFound("id.notEquals=" + id);

        defaultOffreShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOffreShouldNotBeFound("id.greaterThan=" + id);

        defaultOffreShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOffreShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOffresByEanLibraireIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where eanLibraire equals to DEFAULT_EAN_LIBRAIRE
        defaultOffreShouldBeFound("eanLibraire.equals=" + DEFAULT_EAN_LIBRAIRE);

        // Get all the offreList where eanLibraire equals to UPDATED_EAN_LIBRAIRE
        defaultOffreShouldNotBeFound("eanLibraire.equals=" + UPDATED_EAN_LIBRAIRE);
    }

    @Test
    @Transactional
    public void getAllOffresByEanLibraireIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where eanLibraire not equals to DEFAULT_EAN_LIBRAIRE
        defaultOffreShouldNotBeFound("eanLibraire.notEquals=" + DEFAULT_EAN_LIBRAIRE);

        // Get all the offreList where eanLibraire not equals to UPDATED_EAN_LIBRAIRE
        defaultOffreShouldBeFound("eanLibraire.notEquals=" + UPDATED_EAN_LIBRAIRE);
    }

    @Test
    @Transactional
    public void getAllOffresByEanLibraireIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where eanLibraire in DEFAULT_EAN_LIBRAIRE or UPDATED_EAN_LIBRAIRE
        defaultOffreShouldBeFound("eanLibraire.in=" + DEFAULT_EAN_LIBRAIRE + "," + UPDATED_EAN_LIBRAIRE);

        // Get all the offreList where eanLibraire equals to UPDATED_EAN_LIBRAIRE
        defaultOffreShouldNotBeFound("eanLibraire.in=" + UPDATED_EAN_LIBRAIRE);
    }

    @Test
    @Transactional
    public void getAllOffresByEanLibraireIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where eanLibraire is not null
        defaultOffreShouldBeFound("eanLibraire.specified=true");

        // Get all the offreList where eanLibraire is null
        defaultOffreShouldNotBeFound("eanLibraire.specified=false");
    }
                @Test
    @Transactional
    public void getAllOffresByEanLibraireContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where eanLibraire contains DEFAULT_EAN_LIBRAIRE
        defaultOffreShouldBeFound("eanLibraire.contains=" + DEFAULT_EAN_LIBRAIRE);

        // Get all the offreList where eanLibraire contains UPDATED_EAN_LIBRAIRE
        defaultOffreShouldNotBeFound("eanLibraire.contains=" + UPDATED_EAN_LIBRAIRE);
    }

    @Test
    @Transactional
    public void getAllOffresByEanLibraireNotContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where eanLibraire does not contain DEFAULT_EAN_LIBRAIRE
        defaultOffreShouldNotBeFound("eanLibraire.doesNotContain=" + DEFAULT_EAN_LIBRAIRE);

        // Get all the offreList where eanLibraire does not contain UPDATED_EAN_LIBRAIRE
        defaultOffreShouldBeFound("eanLibraire.doesNotContain=" + UPDATED_EAN_LIBRAIRE);
    }


    @Test
    @Transactional
    public void getAllOffresByReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where reference equals to DEFAULT_REFERENCE
        defaultOffreShouldBeFound("reference.equals=" + DEFAULT_REFERENCE);

        // Get all the offreList where reference equals to UPDATED_REFERENCE
        defaultOffreShouldNotBeFound("reference.equals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllOffresByReferenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where reference not equals to DEFAULT_REFERENCE
        defaultOffreShouldNotBeFound("reference.notEquals=" + DEFAULT_REFERENCE);

        // Get all the offreList where reference not equals to UPDATED_REFERENCE
        defaultOffreShouldBeFound("reference.notEquals=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllOffresByReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where reference in DEFAULT_REFERENCE or UPDATED_REFERENCE
        defaultOffreShouldBeFound("reference.in=" + DEFAULT_REFERENCE + "," + UPDATED_REFERENCE);

        // Get all the offreList where reference equals to UPDATED_REFERENCE
        defaultOffreShouldNotBeFound("reference.in=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllOffresByReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where reference is not null
        defaultOffreShouldBeFound("reference.specified=true");

        // Get all the offreList where reference is null
        defaultOffreShouldNotBeFound("reference.specified=false");
    }
                @Test
    @Transactional
    public void getAllOffresByReferenceContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where reference contains DEFAULT_REFERENCE
        defaultOffreShouldBeFound("reference.contains=" + DEFAULT_REFERENCE);

        // Get all the offreList where reference contains UPDATED_REFERENCE
        defaultOffreShouldNotBeFound("reference.contains=" + UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllOffresByReferenceNotContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where reference does not contain DEFAULT_REFERENCE
        defaultOffreShouldNotBeFound("reference.doesNotContain=" + DEFAULT_REFERENCE);

        // Get all the offreList where reference does not contain UPDATED_REFERENCE
        defaultOffreShouldBeFound("reference.doesNotContain=" + UPDATED_REFERENCE);
    }


    @Test
    @Transactional
    public void getAllOffresByDureeIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where duree equals to DEFAULT_DUREE
        defaultOffreShouldBeFound("duree.equals=" + DEFAULT_DUREE);

        // Get all the offreList where duree equals to UPDATED_DUREE
        defaultOffreShouldNotBeFound("duree.equals=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllOffresByDureeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where duree not equals to DEFAULT_DUREE
        defaultOffreShouldNotBeFound("duree.notEquals=" + DEFAULT_DUREE);

        // Get all the offreList where duree not equals to UPDATED_DUREE
        defaultOffreShouldBeFound("duree.notEquals=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllOffresByDureeIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where duree in DEFAULT_DUREE or UPDATED_DUREE
        defaultOffreShouldBeFound("duree.in=" + DEFAULT_DUREE + "," + UPDATED_DUREE);

        // Get all the offreList where duree equals to UPDATED_DUREE
        defaultOffreShouldNotBeFound("duree.in=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllOffresByDureeIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where duree is not null
        defaultOffreShouldBeFound("duree.specified=true");

        // Get all the offreList where duree is null
        defaultOffreShouldNotBeFound("duree.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffresByDureeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where duree is greater than or equal to DEFAULT_DUREE
        defaultOffreShouldBeFound("duree.greaterThanOrEqual=" + DEFAULT_DUREE);

        // Get all the offreList where duree is greater than or equal to UPDATED_DUREE
        defaultOffreShouldNotBeFound("duree.greaterThanOrEqual=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllOffresByDureeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where duree is less than or equal to DEFAULT_DUREE
        defaultOffreShouldBeFound("duree.lessThanOrEqual=" + DEFAULT_DUREE);

        // Get all the offreList where duree is less than or equal to SMALLER_DUREE
        defaultOffreShouldNotBeFound("duree.lessThanOrEqual=" + SMALLER_DUREE);
    }

    @Test
    @Transactional
    public void getAllOffresByDureeIsLessThanSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where duree is less than DEFAULT_DUREE
        defaultOffreShouldNotBeFound("duree.lessThan=" + DEFAULT_DUREE);

        // Get all the offreList where duree is less than UPDATED_DUREE
        defaultOffreShouldBeFound("duree.lessThan=" + UPDATED_DUREE);
    }

    @Test
    @Transactional
    public void getAllOffresByDureeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where duree is greater than DEFAULT_DUREE
        defaultOffreShouldNotBeFound("duree.greaterThan=" + DEFAULT_DUREE);

        // Get all the offreList where duree is greater than SMALLER_DUREE
        defaultOffreShouldBeFound("duree.greaterThan=" + SMALLER_DUREE);
    }


    @Test
    @Transactional
    public void getAllOffresByAdoptionIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where adoption equals to DEFAULT_ADOPTION
        defaultOffreShouldBeFound("adoption.equals=" + DEFAULT_ADOPTION);

        // Get all the offreList where adoption equals to UPDATED_ADOPTION
        defaultOffreShouldNotBeFound("adoption.equals=" + UPDATED_ADOPTION);
    }

    @Test
    @Transactional
    public void getAllOffresByAdoptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where adoption not equals to DEFAULT_ADOPTION
        defaultOffreShouldNotBeFound("adoption.notEquals=" + DEFAULT_ADOPTION);

        // Get all the offreList where adoption not equals to UPDATED_ADOPTION
        defaultOffreShouldBeFound("adoption.notEquals=" + UPDATED_ADOPTION);
    }

    @Test
    @Transactional
    public void getAllOffresByAdoptionIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where adoption in DEFAULT_ADOPTION or UPDATED_ADOPTION
        defaultOffreShouldBeFound("adoption.in=" + DEFAULT_ADOPTION + "," + UPDATED_ADOPTION);

        // Get all the offreList where adoption equals to UPDATED_ADOPTION
        defaultOffreShouldNotBeFound("adoption.in=" + UPDATED_ADOPTION);
    }

    @Test
    @Transactional
    public void getAllOffresByAdoptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where adoption is not null
        defaultOffreShouldBeFound("adoption.specified=true");

        // Get all the offreList where adoption is null
        defaultOffreShouldNotBeFound("adoption.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffresByQuantiteMinimaleAchatIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where quantiteMinimaleAchat equals to DEFAULT_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldBeFound("quantiteMinimaleAchat.equals=" + DEFAULT_QUANTITE_MINIMALE_ACHAT);

        // Get all the offreList where quantiteMinimaleAchat equals to UPDATED_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldNotBeFound("quantiteMinimaleAchat.equals=" + UPDATED_QUANTITE_MINIMALE_ACHAT);
    }

    @Test
    @Transactional
    public void getAllOffresByQuantiteMinimaleAchatIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where quantiteMinimaleAchat not equals to DEFAULT_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldNotBeFound("quantiteMinimaleAchat.notEquals=" + DEFAULT_QUANTITE_MINIMALE_ACHAT);

        // Get all the offreList where quantiteMinimaleAchat not equals to UPDATED_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldBeFound("quantiteMinimaleAchat.notEquals=" + UPDATED_QUANTITE_MINIMALE_ACHAT);
    }

    @Test
    @Transactional
    public void getAllOffresByQuantiteMinimaleAchatIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where quantiteMinimaleAchat in DEFAULT_QUANTITE_MINIMALE_ACHAT or UPDATED_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldBeFound("quantiteMinimaleAchat.in=" + DEFAULT_QUANTITE_MINIMALE_ACHAT + "," + UPDATED_QUANTITE_MINIMALE_ACHAT);

        // Get all the offreList where quantiteMinimaleAchat equals to UPDATED_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldNotBeFound("quantiteMinimaleAchat.in=" + UPDATED_QUANTITE_MINIMALE_ACHAT);
    }

    @Test
    @Transactional
    public void getAllOffresByQuantiteMinimaleAchatIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where quantiteMinimaleAchat is not null
        defaultOffreShouldBeFound("quantiteMinimaleAchat.specified=true");

        // Get all the offreList where quantiteMinimaleAchat is null
        defaultOffreShouldNotBeFound("quantiteMinimaleAchat.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffresByQuantiteMinimaleAchatIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where quantiteMinimaleAchat is greater than or equal to DEFAULT_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldBeFound("quantiteMinimaleAchat.greaterThanOrEqual=" + DEFAULT_QUANTITE_MINIMALE_ACHAT);

        // Get all the offreList where quantiteMinimaleAchat is greater than or equal to UPDATED_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldNotBeFound("quantiteMinimaleAchat.greaterThanOrEqual=" + UPDATED_QUANTITE_MINIMALE_ACHAT);
    }

    @Test
    @Transactional
    public void getAllOffresByQuantiteMinimaleAchatIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where quantiteMinimaleAchat is less than or equal to DEFAULT_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldBeFound("quantiteMinimaleAchat.lessThanOrEqual=" + DEFAULT_QUANTITE_MINIMALE_ACHAT);

        // Get all the offreList where quantiteMinimaleAchat is less than or equal to SMALLER_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldNotBeFound("quantiteMinimaleAchat.lessThanOrEqual=" + SMALLER_QUANTITE_MINIMALE_ACHAT);
    }

    @Test
    @Transactional
    public void getAllOffresByQuantiteMinimaleAchatIsLessThanSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where quantiteMinimaleAchat is less than DEFAULT_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldNotBeFound("quantiteMinimaleAchat.lessThan=" + DEFAULT_QUANTITE_MINIMALE_ACHAT);

        // Get all the offreList where quantiteMinimaleAchat is less than UPDATED_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldBeFound("quantiteMinimaleAchat.lessThan=" + UPDATED_QUANTITE_MINIMALE_ACHAT);
    }

    @Test
    @Transactional
    public void getAllOffresByQuantiteMinimaleAchatIsGreaterThanSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where quantiteMinimaleAchat is greater than DEFAULT_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldNotBeFound("quantiteMinimaleAchat.greaterThan=" + DEFAULT_QUANTITE_MINIMALE_ACHAT);

        // Get all the offreList where quantiteMinimaleAchat is greater than SMALLER_QUANTITE_MINIMALE_ACHAT
        defaultOffreShouldBeFound("quantiteMinimaleAchat.greaterThan=" + SMALLER_QUANTITE_MINIMALE_ACHAT);
    }


    @Test
    @Transactional
    public void getAllOffresByLicenceIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where licence equals to DEFAULT_LICENCE
        defaultOffreShouldBeFound("licence.equals=" + DEFAULT_LICENCE);

        // Get all the offreList where licence equals to UPDATED_LICENCE
        defaultOffreShouldNotBeFound("licence.equals=" + UPDATED_LICENCE);
    }

    @Test
    @Transactional
    public void getAllOffresByLicenceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where licence not equals to DEFAULT_LICENCE
        defaultOffreShouldNotBeFound("licence.notEquals=" + DEFAULT_LICENCE);

        // Get all the offreList where licence not equals to UPDATED_LICENCE
        defaultOffreShouldBeFound("licence.notEquals=" + UPDATED_LICENCE);
    }

    @Test
    @Transactional
    public void getAllOffresByLicenceIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where licence in DEFAULT_LICENCE or UPDATED_LICENCE
        defaultOffreShouldBeFound("licence.in=" + DEFAULT_LICENCE + "," + UPDATED_LICENCE);

        // Get all the offreList where licence equals to UPDATED_LICENCE
        defaultOffreShouldNotBeFound("licence.in=" + UPDATED_LICENCE);
    }

    @Test
    @Transactional
    public void getAllOffresByLicenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where licence is not null
        defaultOffreShouldBeFound("licence.specified=true");

        // Get all the offreList where licence is null
        defaultOffreShouldNotBeFound("licence.specified=false");
    }
                @Test
    @Transactional
    public void getAllOffresByLicenceContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where licence contains DEFAULT_LICENCE
        defaultOffreShouldBeFound("licence.contains=" + DEFAULT_LICENCE);

        // Get all the offreList where licence contains UPDATED_LICENCE
        defaultOffreShouldNotBeFound("licence.contains=" + UPDATED_LICENCE);
    }

    @Test
    @Transactional
    public void getAllOffresByLicenceNotContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where licence does not contain DEFAULT_LICENCE
        defaultOffreShouldNotBeFound("licence.doesNotContain=" + DEFAULT_LICENCE);

        // Get all the offreList where licence does not contain UPDATED_LICENCE
        defaultOffreShouldBeFound("licence.doesNotContain=" + UPDATED_LICENCE);
    }


    @Test
    @Transactional
    public void getAllOffresByPrescripteurIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prescripteur equals to DEFAULT_PRESCRIPTEUR
        defaultOffreShouldBeFound("prescripteur.equals=" + DEFAULT_PRESCRIPTEUR);

        // Get all the offreList where prescripteur equals to UPDATED_PRESCRIPTEUR
        defaultOffreShouldNotBeFound("prescripteur.equals=" + UPDATED_PRESCRIPTEUR);
    }

    @Test
    @Transactional
    public void getAllOffresByPrescripteurIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prescripteur not equals to DEFAULT_PRESCRIPTEUR
        defaultOffreShouldNotBeFound("prescripteur.notEquals=" + DEFAULT_PRESCRIPTEUR);

        // Get all the offreList where prescripteur not equals to UPDATED_PRESCRIPTEUR
        defaultOffreShouldBeFound("prescripteur.notEquals=" + UPDATED_PRESCRIPTEUR);
    }

    @Test
    @Transactional
    public void getAllOffresByPrescripteurIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prescripteur in DEFAULT_PRESCRIPTEUR or UPDATED_PRESCRIPTEUR
        defaultOffreShouldBeFound("prescripteur.in=" + DEFAULT_PRESCRIPTEUR + "," + UPDATED_PRESCRIPTEUR);

        // Get all the offreList where prescripteur equals to UPDATED_PRESCRIPTEUR
        defaultOffreShouldNotBeFound("prescripteur.in=" + UPDATED_PRESCRIPTEUR);
    }

    @Test
    @Transactional
    public void getAllOffresByPrescripteurIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prescripteur is not null
        defaultOffreShouldBeFound("prescripteur.specified=true");

        // Get all the offreList where prescripteur is null
        defaultOffreShouldNotBeFound("prescripteur.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffresByLibelleIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where libelle equals to DEFAULT_LIBELLE
        defaultOffreShouldBeFound("libelle.equals=" + DEFAULT_LIBELLE);

        // Get all the offreList where libelle equals to UPDATED_LIBELLE
        defaultOffreShouldNotBeFound("libelle.equals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllOffresByLibelleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where libelle not equals to DEFAULT_LIBELLE
        defaultOffreShouldNotBeFound("libelle.notEquals=" + DEFAULT_LIBELLE);

        // Get all the offreList where libelle not equals to UPDATED_LIBELLE
        defaultOffreShouldBeFound("libelle.notEquals=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllOffresByLibelleIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where libelle in DEFAULT_LIBELLE or UPDATED_LIBELLE
        defaultOffreShouldBeFound("libelle.in=" + DEFAULT_LIBELLE + "," + UPDATED_LIBELLE);

        // Get all the offreList where libelle equals to UPDATED_LIBELLE
        defaultOffreShouldNotBeFound("libelle.in=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllOffresByLibelleIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where libelle is not null
        defaultOffreShouldBeFound("libelle.specified=true");

        // Get all the offreList where libelle is null
        defaultOffreShouldNotBeFound("libelle.specified=false");
    }
                @Test
    @Transactional
    public void getAllOffresByLibelleContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where libelle contains DEFAULT_LIBELLE
        defaultOffreShouldBeFound("libelle.contains=" + DEFAULT_LIBELLE);

        // Get all the offreList where libelle contains UPDATED_LIBELLE
        defaultOffreShouldNotBeFound("libelle.contains=" + UPDATED_LIBELLE);
    }

    @Test
    @Transactional
    public void getAllOffresByLibelleNotContainsSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where libelle does not contain DEFAULT_LIBELLE
        defaultOffreShouldNotBeFound("libelle.doesNotContain=" + DEFAULT_LIBELLE);

        // Get all the offreList where libelle does not contain UPDATED_LIBELLE
        defaultOffreShouldBeFound("libelle.doesNotContain=" + UPDATED_LIBELLE);
    }


    @Test
    @Transactional
    public void getAllOffresByPrixHTIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prixHT equals to DEFAULT_PRIX_HT
        defaultOffreShouldBeFound("prixHT.equals=" + DEFAULT_PRIX_HT);

        // Get all the offreList where prixHT equals to UPDATED_PRIX_HT
        defaultOffreShouldNotBeFound("prixHT.equals=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllOffresByPrixHTIsNotEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prixHT not equals to DEFAULT_PRIX_HT
        defaultOffreShouldNotBeFound("prixHT.notEquals=" + DEFAULT_PRIX_HT);

        // Get all the offreList where prixHT not equals to UPDATED_PRIX_HT
        defaultOffreShouldBeFound("prixHT.notEquals=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllOffresByPrixHTIsInShouldWork() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prixHT in DEFAULT_PRIX_HT or UPDATED_PRIX_HT
        defaultOffreShouldBeFound("prixHT.in=" + DEFAULT_PRIX_HT + "," + UPDATED_PRIX_HT);

        // Get all the offreList where prixHT equals to UPDATED_PRIX_HT
        defaultOffreShouldNotBeFound("prixHT.in=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllOffresByPrixHTIsNullOrNotNull() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prixHT is not null
        defaultOffreShouldBeFound("prixHT.specified=true");

        // Get all the offreList where prixHT is null
        defaultOffreShouldNotBeFound("prixHT.specified=false");
    }

    @Test
    @Transactional
    public void getAllOffresByPrixHTIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prixHT is greater than or equal to DEFAULT_PRIX_HT
        defaultOffreShouldBeFound("prixHT.greaterThanOrEqual=" + DEFAULT_PRIX_HT);

        // Get all the offreList where prixHT is greater than or equal to UPDATED_PRIX_HT
        defaultOffreShouldNotBeFound("prixHT.greaterThanOrEqual=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllOffresByPrixHTIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prixHT is less than or equal to DEFAULT_PRIX_HT
        defaultOffreShouldBeFound("prixHT.lessThanOrEqual=" + DEFAULT_PRIX_HT);

        // Get all the offreList where prixHT is less than or equal to SMALLER_PRIX_HT
        defaultOffreShouldNotBeFound("prixHT.lessThanOrEqual=" + SMALLER_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllOffresByPrixHTIsLessThanSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prixHT is less than DEFAULT_PRIX_HT
        defaultOffreShouldNotBeFound("prixHT.lessThan=" + DEFAULT_PRIX_HT);

        // Get all the offreList where prixHT is less than UPDATED_PRIX_HT
        defaultOffreShouldBeFound("prixHT.lessThan=" + UPDATED_PRIX_HT);
    }

    @Test
    @Transactional
    public void getAllOffresByPrixHTIsGreaterThanSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        // Get all the offreList where prixHT is greater than DEFAULT_PRIX_HT
        defaultOffreShouldNotBeFound("prixHT.greaterThan=" + DEFAULT_PRIX_HT);

        // Get all the offreList where prixHT is greater than SMALLER_PRIX_HT
        defaultOffreShouldBeFound("prixHT.greaterThan=" + SMALLER_PRIX_HT);
    }


    @Test
    @Transactional
    public void getAllOffresByTvaIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);
        Tva tva = TvaResourceIT.createEntity(em);
        em.persist(tva);
        em.flush();
        offre.addTva(tva);
        offreRepository.saveAndFlush(offre);
        Long tvaId = tva.getId();

        // Get all the offreList where tva equals to tvaId
        defaultOffreShouldBeFound("tvaId.equals=" + tvaId);

        // Get all the offreList where tva equals to tvaId + 1
        defaultOffreShouldNotBeFound("tvaId.equals=" + (tvaId + 1));
    }


    @Test
    @Transactional
    public void getAllOffresByLepIsEqualToSomething() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);
        Lep lep = LepResourceIT.createEntity(em);
        em.persist(lep);
        em.flush();
        offre.addLep(lep);
        offreRepository.saveAndFlush(offre);
        Long lepId = lep.getId();

        // Get all the offreList where lep equals to lepId
        defaultOffreShouldBeFound("lepId.equals=" + lepId);

        // Get all the offreList where lep equals to lepId + 1
        defaultOffreShouldNotBeFound("lepId.equals=" + (lepId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOffreShouldBeFound(String filter) throws Exception {
        restOffreMockMvc.perform(get("/api/offres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offre.getId().intValue())))
            .andExpect(jsonPath("$.[*].eanLibraire").value(hasItem(DEFAULT_EAN_LIBRAIRE)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].adoption").value(hasItem(DEFAULT_ADOPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].quantiteMinimaleAchat").value(hasItem(DEFAULT_QUANTITE_MINIMALE_ACHAT)))
            .andExpect(jsonPath("$.[*].licence").value(hasItem(DEFAULT_LICENCE)))
            .andExpect(jsonPath("$.[*].prescripteur").value(hasItem(DEFAULT_PRESCRIPTEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.intValue())));

        // Check, that the count call also returns 1
        restOffreMockMvc.perform(get("/api/offres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOffreShouldNotBeFound(String filter) throws Exception {
        restOffreMockMvc.perform(get("/api/offres?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOffreMockMvc.perform(get("/api/offres/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOffre() throws Exception {
        // Get the offre
        restOffreMockMvc.perform(get("/api/offres/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Update the offre
        Offre updatedOffre = offreRepository.findById(offre.getId()).get();
        // Disconnect from session so that the updates on updatedOffre are not directly saved in db
        em.detach(updatedOffre);
        updatedOffre
            .eanLibraire(UPDATED_EAN_LIBRAIRE)
            .reference(UPDATED_REFERENCE)
            .duree(UPDATED_DUREE)
            .adoption(UPDATED_ADOPTION)
            .quantiteMinimaleAchat(UPDATED_QUANTITE_MINIMALE_ACHAT)
            .licence(UPDATED_LICENCE)
            .prescripteur(UPDATED_PRESCRIPTEUR)
            .libelle(UPDATED_LIBELLE)
            .prixHT(UPDATED_PRIX_HT);
        OffreDTO offreDTO = offreMapper.toDto(updatedOffre);

        restOffreMockMvc.perform(put("/api/offres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isOk());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);
        Offre testOffre = offreList.get(offreList.size() - 1);
        assertThat(testOffre.getEanLibraire()).isEqualTo(UPDATED_EAN_LIBRAIRE);
        assertThat(testOffre.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testOffre.getDuree()).isEqualTo(UPDATED_DUREE);
        assertThat(testOffre.isAdoption()).isEqualTo(UPDATED_ADOPTION);
        assertThat(testOffre.getQuantiteMinimaleAchat()).isEqualTo(UPDATED_QUANTITE_MINIMALE_ACHAT);
        assertThat(testOffre.getLicence()).isEqualTo(UPDATED_LICENCE);
        assertThat(testOffre.isPrescripteur()).isEqualTo(UPDATED_PRESCRIPTEUR);
        assertThat(testOffre.getLibelle()).isEqualTo(UPDATED_LIBELLE);
        assertThat(testOffre.getPrixHT()).isEqualTo(UPDATED_PRIX_HT);

        // Validate the Offre in Elasticsearch
        verify(mockOffreSearchRepository, times(1)).save(testOffre);
    }

    @Test
    @Transactional
    public void updateNonExistingOffre() throws Exception {
        int databaseSizeBeforeUpdate = offreRepository.findAll().size();

        // Create the Offre
        OffreDTO offreDTO = offreMapper.toDto(offre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOffreMockMvc.perform(put("/api/offres")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(offreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Offre in the database
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Offre in Elasticsearch
        verify(mockOffreSearchRepository, times(0)).save(offre);
    }

    @Test
    @Transactional
    public void deleteOffre() throws Exception {
        // Initialize the database
        offreRepository.saveAndFlush(offre);

        int databaseSizeBeforeDelete = offreRepository.findAll().size();

        // Delete the offre
        restOffreMockMvc.perform(delete("/api/offres/{id}", offre.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offre> offreList = offreRepository.findAll();
        assertThat(offreList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Offre in Elasticsearch
        verify(mockOffreSearchRepository, times(1)).deleteById(offre.getId());
    }

    @Test
    @Transactional
    public void searchOffre() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        offreRepository.saveAndFlush(offre);
        when(mockOffreSearchRepository.search(queryStringQuery("id:" + offre.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(offre), PageRequest.of(0, 1), 1));

        // Search the offre
        restOffreMockMvc.perform(get("/api/_search/offres?query=id:" + offre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offre.getId().intValue())))
            .andExpect(jsonPath("$.[*].eanLibraire").value(hasItem(DEFAULT_EAN_LIBRAIRE)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE)))
            .andExpect(jsonPath("$.[*].duree").value(hasItem(DEFAULT_DUREE)))
            .andExpect(jsonPath("$.[*].adoption").value(hasItem(DEFAULT_ADOPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].quantiteMinimaleAchat").value(hasItem(DEFAULT_QUANTITE_MINIMALE_ACHAT)))
            .andExpect(jsonPath("$.[*].licence").value(hasItem(DEFAULT_LICENCE)))
            .andExpect(jsonPath("$.[*].prescripteur").value(hasItem(DEFAULT_PRESCRIPTEUR.booleanValue())))
            .andExpect(jsonPath("$.[*].libelle").value(hasItem(DEFAULT_LIBELLE)))
            .andExpect(jsonPath("$.[*].prixHT").value(hasItem(DEFAULT_PRIX_HT.intValue())));
    }
}
