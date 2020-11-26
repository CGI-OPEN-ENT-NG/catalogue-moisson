package fr.openent.moisson.web.rest;

import fr.openent.moisson.MoissoncatalogueApp;
import fr.openent.moisson.domain.Condition;
import fr.openent.moisson.repository.ConditionRepository;
import fr.openent.moisson.repository.search.ConditionSearchRepository;
import fr.openent.moisson.service.ConditionService;
import fr.openent.moisson.service.dto.ConditionDTO;
import fr.openent.moisson.service.mapper.ConditionMapper;
import fr.openent.moisson.service.dto.ConditionCriteria;
import fr.openent.moisson.service.ConditionQueryService;

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
 * Integration tests for the {@link ConditionResource} REST controller.
 */
@SpringBootTest(classes = MoissoncatalogueApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ConditionResourceIT {

    private static final Integer DEFAULT_GRATUITE = 1;
    private static final Integer UPDATED_GRATUITE = 2;
    private static final Integer SMALLER_GRATUITE = 1 - 1;

    private static final Integer DEFAULT_CONDITION_GRATUITE = 1;
    private static final Integer UPDATED_CONDITION_GRATUITE = 2;
    private static final Integer SMALLER_CONDITION_GRATUITE = 1 - 1;

    @Autowired
    private ConditionRepository conditionRepository;

    @Autowired
    private ConditionMapper conditionMapper;

    @Autowired
    private ConditionService conditionService;

    /**
     * This repository is mocked in the fr.openent.moisson.repository.search test package.
     *
     * @see fr.openent.moisson.repository.search.ConditionSearchRepositoryMockConfiguration
     */
    @Autowired
    private ConditionSearchRepository mockConditionSearchRepository;

    @Autowired
    private ConditionQueryService conditionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConditionMockMvc;

    private Condition condition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Condition createEntity(EntityManager em) {
        Condition condition = new Condition()
            .gratuite(DEFAULT_GRATUITE)
            .conditionGratuite(DEFAULT_CONDITION_GRATUITE);
        return condition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Condition createUpdatedEntity(EntityManager em) {
        Condition condition = new Condition()
            .gratuite(UPDATED_GRATUITE)
            .conditionGratuite(UPDATED_CONDITION_GRATUITE);
        return condition;
    }

    @BeforeEach
    public void initTest() {
        condition = createEntity(em);
    }

    @Test
    @Transactional
    public void createCondition() throws Exception {
        int databaseSizeBeforeCreate = conditionRepository.findAll().size();
        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);
        restConditionMockMvc.perform(post("/api/conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conditionDTO)))
            .andExpect(status().isCreated());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeCreate + 1);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getGratuite()).isEqualTo(DEFAULT_GRATUITE);
        assertThat(testCondition.getConditionGratuite()).isEqualTo(DEFAULT_CONDITION_GRATUITE);

        // Validate the Condition in Elasticsearch
        verify(mockConditionSearchRepository, times(1)).save(testCondition);
    }

    @Test
    @Transactional
    public void createConditionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conditionRepository.findAll().size();

        // Create the Condition with an existing ID
        condition.setId(1L);
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConditionMockMvc.perform(post("/api/conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Condition in Elasticsearch
        verify(mockConditionSearchRepository, times(0)).save(condition);
    }


    @Test
    @Transactional
    public void getAllConditions() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList
        restConditionMockMvc.perform(get("/api/conditions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(condition.getId().intValue())))
            .andExpect(jsonPath("$.[*].gratuite").value(hasItem(DEFAULT_GRATUITE)))
            .andExpect(jsonPath("$.[*].conditionGratuite").value(hasItem(DEFAULT_CONDITION_GRATUITE)));
    }
    
    @Test
    @Transactional
    public void getCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get the condition
        restConditionMockMvc.perform(get("/api/conditions/{id}", condition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(condition.getId().intValue()))
            .andExpect(jsonPath("$.gratuite").value(DEFAULT_GRATUITE))
            .andExpect(jsonPath("$.conditionGratuite").value(DEFAULT_CONDITION_GRATUITE));
    }


    @Test
    @Transactional
    public void getConditionsByIdFiltering() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        Long id = condition.getId();

        defaultConditionShouldBeFound("id.equals=" + id);
        defaultConditionShouldNotBeFound("id.notEquals=" + id);

        defaultConditionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultConditionShouldNotBeFound("id.greaterThan=" + id);

        defaultConditionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultConditionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllConditionsByGratuiteIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where gratuite equals to DEFAULT_GRATUITE
        defaultConditionShouldBeFound("gratuite.equals=" + DEFAULT_GRATUITE);

        // Get all the conditionList where gratuite equals to UPDATED_GRATUITE
        defaultConditionShouldNotBeFound("gratuite.equals=" + UPDATED_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByGratuiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where gratuite not equals to DEFAULT_GRATUITE
        defaultConditionShouldNotBeFound("gratuite.notEquals=" + DEFAULT_GRATUITE);

        // Get all the conditionList where gratuite not equals to UPDATED_GRATUITE
        defaultConditionShouldBeFound("gratuite.notEquals=" + UPDATED_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByGratuiteIsInShouldWork() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where gratuite in DEFAULT_GRATUITE or UPDATED_GRATUITE
        defaultConditionShouldBeFound("gratuite.in=" + DEFAULT_GRATUITE + "," + UPDATED_GRATUITE);

        // Get all the conditionList where gratuite equals to UPDATED_GRATUITE
        defaultConditionShouldNotBeFound("gratuite.in=" + UPDATED_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByGratuiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where gratuite is not null
        defaultConditionShouldBeFound("gratuite.specified=true");

        // Get all the conditionList where gratuite is null
        defaultConditionShouldNotBeFound("gratuite.specified=false");
    }

    @Test
    @Transactional
    public void getAllConditionsByGratuiteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where gratuite is greater than or equal to DEFAULT_GRATUITE
        defaultConditionShouldBeFound("gratuite.greaterThanOrEqual=" + DEFAULT_GRATUITE);

        // Get all the conditionList where gratuite is greater than or equal to UPDATED_GRATUITE
        defaultConditionShouldNotBeFound("gratuite.greaterThanOrEqual=" + UPDATED_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByGratuiteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where gratuite is less than or equal to DEFAULT_GRATUITE
        defaultConditionShouldBeFound("gratuite.lessThanOrEqual=" + DEFAULT_GRATUITE);

        // Get all the conditionList where gratuite is less than or equal to SMALLER_GRATUITE
        defaultConditionShouldNotBeFound("gratuite.lessThanOrEqual=" + SMALLER_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByGratuiteIsLessThanSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where gratuite is less than DEFAULT_GRATUITE
        defaultConditionShouldNotBeFound("gratuite.lessThan=" + DEFAULT_GRATUITE);

        // Get all the conditionList where gratuite is less than UPDATED_GRATUITE
        defaultConditionShouldBeFound("gratuite.lessThan=" + UPDATED_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByGratuiteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where gratuite is greater than DEFAULT_GRATUITE
        defaultConditionShouldNotBeFound("gratuite.greaterThan=" + DEFAULT_GRATUITE);

        // Get all the conditionList where gratuite is greater than SMALLER_GRATUITE
        defaultConditionShouldBeFound("gratuite.greaterThan=" + SMALLER_GRATUITE);
    }


    @Test
    @Transactional
    public void getAllConditionsByConditionGratuiteIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionGratuite equals to DEFAULT_CONDITION_GRATUITE
        defaultConditionShouldBeFound("conditionGratuite.equals=" + DEFAULT_CONDITION_GRATUITE);

        // Get all the conditionList where conditionGratuite equals to UPDATED_CONDITION_GRATUITE
        defaultConditionShouldNotBeFound("conditionGratuite.equals=" + UPDATED_CONDITION_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByConditionGratuiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionGratuite not equals to DEFAULT_CONDITION_GRATUITE
        defaultConditionShouldNotBeFound("conditionGratuite.notEquals=" + DEFAULT_CONDITION_GRATUITE);

        // Get all the conditionList where conditionGratuite not equals to UPDATED_CONDITION_GRATUITE
        defaultConditionShouldBeFound("conditionGratuite.notEquals=" + UPDATED_CONDITION_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByConditionGratuiteIsInShouldWork() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionGratuite in DEFAULT_CONDITION_GRATUITE or UPDATED_CONDITION_GRATUITE
        defaultConditionShouldBeFound("conditionGratuite.in=" + DEFAULT_CONDITION_GRATUITE + "," + UPDATED_CONDITION_GRATUITE);

        // Get all the conditionList where conditionGratuite equals to UPDATED_CONDITION_GRATUITE
        defaultConditionShouldNotBeFound("conditionGratuite.in=" + UPDATED_CONDITION_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByConditionGratuiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionGratuite is not null
        defaultConditionShouldBeFound("conditionGratuite.specified=true");

        // Get all the conditionList where conditionGratuite is null
        defaultConditionShouldNotBeFound("conditionGratuite.specified=false");
    }

    @Test
    @Transactional
    public void getAllConditionsByConditionGratuiteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionGratuite is greater than or equal to DEFAULT_CONDITION_GRATUITE
        defaultConditionShouldBeFound("conditionGratuite.greaterThanOrEqual=" + DEFAULT_CONDITION_GRATUITE);

        // Get all the conditionList where conditionGratuite is greater than or equal to UPDATED_CONDITION_GRATUITE
        defaultConditionShouldNotBeFound("conditionGratuite.greaterThanOrEqual=" + UPDATED_CONDITION_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByConditionGratuiteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionGratuite is less than or equal to DEFAULT_CONDITION_GRATUITE
        defaultConditionShouldBeFound("conditionGratuite.lessThanOrEqual=" + DEFAULT_CONDITION_GRATUITE);

        // Get all the conditionList where conditionGratuite is less than or equal to SMALLER_CONDITION_GRATUITE
        defaultConditionShouldNotBeFound("conditionGratuite.lessThanOrEqual=" + SMALLER_CONDITION_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByConditionGratuiteIsLessThanSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionGratuite is less than DEFAULT_CONDITION_GRATUITE
        defaultConditionShouldNotBeFound("conditionGratuite.lessThan=" + DEFAULT_CONDITION_GRATUITE);

        // Get all the conditionList where conditionGratuite is less than UPDATED_CONDITION_GRATUITE
        defaultConditionShouldBeFound("conditionGratuite.lessThan=" + UPDATED_CONDITION_GRATUITE);
    }

    @Test
    @Transactional
    public void getAllConditionsByConditionGratuiteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        // Get all the conditionList where conditionGratuite is greater than DEFAULT_CONDITION_GRATUITE
        defaultConditionShouldNotBeFound("conditionGratuite.greaterThan=" + DEFAULT_CONDITION_GRATUITE);

        // Get all the conditionList where conditionGratuite is greater than SMALLER_CONDITION_GRATUITE
        defaultConditionShouldBeFound("conditionGratuite.greaterThan=" + SMALLER_CONDITION_GRATUITE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultConditionShouldBeFound(String filter) throws Exception {
        restConditionMockMvc.perform(get("/api/conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(condition.getId().intValue())))
            .andExpect(jsonPath("$.[*].gratuite").value(hasItem(DEFAULT_GRATUITE)))
            .andExpect(jsonPath("$.[*].conditionGratuite").value(hasItem(DEFAULT_CONDITION_GRATUITE)));

        // Check, that the count call also returns 1
        restConditionMockMvc.perform(get("/api/conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultConditionShouldNotBeFound(String filter) throws Exception {
        restConditionMockMvc.perform(get("/api/conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConditionMockMvc.perform(get("/api/conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCondition() throws Exception {
        // Get the condition
        restConditionMockMvc.perform(get("/api/conditions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Update the condition
        Condition updatedCondition = conditionRepository.findById(condition.getId()).get();
        // Disconnect from session so that the updates on updatedCondition are not directly saved in db
        em.detach(updatedCondition);
        updatedCondition
            .gratuite(UPDATED_GRATUITE)
            .conditionGratuite(UPDATED_CONDITION_GRATUITE);
        ConditionDTO conditionDTO = conditionMapper.toDto(updatedCondition);

        restConditionMockMvc.perform(put("/api/conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conditionDTO)))
            .andExpect(status().isOk());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);
        Condition testCondition = conditionList.get(conditionList.size() - 1);
        assertThat(testCondition.getGratuite()).isEqualTo(UPDATED_GRATUITE);
        assertThat(testCondition.getConditionGratuite()).isEqualTo(UPDATED_CONDITION_GRATUITE);

        // Validate the Condition in Elasticsearch
        verify(mockConditionSearchRepository, times(1)).save(testCondition);
    }

    @Test
    @Transactional
    public void updateNonExistingCondition() throws Exception {
        int databaseSizeBeforeUpdate = conditionRepository.findAll().size();

        // Create the Condition
        ConditionDTO conditionDTO = conditionMapper.toDto(condition);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionMockMvc.perform(put("/api/conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(conditionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Condition in the database
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Condition in Elasticsearch
        verify(mockConditionSearchRepository, times(0)).save(condition);
    }

    @Test
    @Transactional
    public void deleteCondition() throws Exception {
        // Initialize the database
        conditionRepository.saveAndFlush(condition);

        int databaseSizeBeforeDelete = conditionRepository.findAll().size();

        // Delete the condition
        restConditionMockMvc.perform(delete("/api/conditions/{id}", condition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Condition> conditionList = conditionRepository.findAll();
        assertThat(conditionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Condition in Elasticsearch
        verify(mockConditionSearchRepository, times(1)).deleteById(condition.getId());
    }

    @Test
    @Transactional
    public void searchCondition() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        conditionRepository.saveAndFlush(condition);
        when(mockConditionSearchRepository.search(queryStringQuery("id:" + condition.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(condition), PageRequest.of(0, 1), 1));

        // Search the condition
        restConditionMockMvc.perform(get("/api/_search/conditions?query=id:" + condition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(condition.getId().intValue())))
            .andExpect(jsonPath("$.[*].gratuite").value(hasItem(DEFAULT_GRATUITE)))
            .andExpect(jsonPath("$.[*].conditionGratuite").value(hasItem(DEFAULT_CONDITION_GRATUITE)));
    }
}
