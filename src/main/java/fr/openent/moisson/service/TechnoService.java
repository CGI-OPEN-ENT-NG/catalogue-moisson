package fr.openent.moisson.service;

import fr.openent.moisson.service.dto.TechnoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.openent.moisson.domain.Techno}.
 */
public interface TechnoService {

    /**
     * Save a techno.
     *
     * @param technoDTO the entity to save.
     * @return the persisted entity.
     */
    TechnoDTO save(TechnoDTO technoDTO);

    /**
     * Get all the technos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TechnoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" techno.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TechnoDTO> findOne(Long id);

    /**
     * Delete the "id" techno.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the techno corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TechnoDTO> search(String query, Pageable pageable);
}
