package fr.openent.moisson.service;

import fr.openent.moisson.service.dto.TvaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.openent.moisson.domain.Tva}.
 */
public interface TvaService {

    /**
     * Save a tva.
     *
     * @param tvaDTO the entity to save.
     * @return the persisted entity.
     */
    TvaDTO save(TvaDTO tvaDTO);

    /**
     * Get all the tvas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TvaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" tva.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TvaDTO> findOne(Long id);

    /**
     * Delete the "id" tva.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the tva corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TvaDTO> search(String query, Pageable pageable);
}
