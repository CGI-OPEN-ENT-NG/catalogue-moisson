package fr.openent.moisson.service;

import fr.openent.moisson.service.dto.LepDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.openent.moisson.domain.Lep}.
 */
public interface LepService {

    /**
     * Save a lep.
     *
     * @param lepDTO the entity to save.
     * @return the persisted entity.
     */
    LepDTO save(LepDTO lepDTO);

    /**
     * Get all the leps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LepDTO> findAll(Pageable pageable);


    /**
     * Get the "id" lep.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LepDTO> findOne(Long id);

    /**
     * Delete the "id" lep.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the lep corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LepDTO> search(String query, Pageable pageable);
}
