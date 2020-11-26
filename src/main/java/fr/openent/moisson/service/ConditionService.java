package fr.openent.moisson.service;

import fr.openent.moisson.service.dto.ConditionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.openent.moisson.domain.Condition}.
 */
public interface ConditionService {

    /**
     * Save a condition.
     *
     * @param conditionDTO the entity to save.
     * @return the persisted entity.
     */
    ConditionDTO save(ConditionDTO conditionDTO);

    /**
     * Get all the conditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConditionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" condition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConditionDTO> findOne(Long id);

    /**
     * Delete the "id" condition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the condition corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConditionDTO> search(String query, Pageable pageable);
}
