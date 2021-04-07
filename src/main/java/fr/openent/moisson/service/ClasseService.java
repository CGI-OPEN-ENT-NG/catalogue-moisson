package fr.openent.moisson.service;

import fr.openent.moisson.service.dto.ClasseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.openent.moisson.domain.Classe}.
 */
public interface ClasseService {

    /**
     * Save a classe.
     *
     * @param classeDTO the entity to save.
     * @return the persisted entity.
     */
    ClasseDTO save(ClasseDTO classeDTO);

    /**
     * Get all the classes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClasseDTO> findAll(Pageable pageable);


    /**
     * Get the "id" classe.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClasseDTO> findOne(Long id);

    /**
     * Delete the "id" classe.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the classe corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClasseDTO> search(String query, Pageable pageable);
}
