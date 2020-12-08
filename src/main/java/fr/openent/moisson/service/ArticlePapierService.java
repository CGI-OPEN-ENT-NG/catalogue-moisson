package fr.openent.moisson.service;

import fr.openent.moisson.domain.ArticlePapier;
import fr.openent.moisson.service.dto.ArticlePapierDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.openent.moisson.domain.ArticlePapier}.
 */
public interface ArticlePapierService {

    /**
     * Save a articlePapier.
     *
     * @param articlePapierDTO the entity to save.
     * @return the persisted entity.
     */
    ArticlePapierDTO save(ArticlePapierDTO articlePapierDTO);

    /**
     * Save a articlePapier.
     *
     * @param articlePapier the entity to save.
     * @return the persisted entity.
     */
    ArticlePapier save(ArticlePapier articlePapier);

    /**
     * Get all the articlePapiers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticlePapierDTO> findAll(Pageable pageable);


    /**
     * Get the "id" articlePapier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticlePapierDTO> findOneDTO(Long id);

    /**
     * Get the "id" articlePapier.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticlePapier> findOne(Long id);

    /**
     * Delete the "id" articlePapier.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the articlePapier corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticlePapierDTO> search(String query, Pageable pageable);
}
