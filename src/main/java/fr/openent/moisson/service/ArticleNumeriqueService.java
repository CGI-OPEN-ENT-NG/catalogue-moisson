package fr.openent.moisson.service;

import fr.openent.moisson.domain.ArticleNumerique;
import fr.openent.moisson.domain.ArticlePapier;
import fr.openent.moisson.service.dto.ArticleNumeriqueDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link fr.openent.moisson.domain.ArticleNumerique}.
 */
public interface ArticleNumeriqueService {

    /**
     * Save a articleNumerique.
     *
     * @param articleNumeriqueDTO the entity to save.
     * @return the persisted entity.
     */
    ArticleNumeriqueDTO save(ArticleNumeriqueDTO articleNumeriqueDTO);

    /**
     * Save a articleNumerique.
     *
     * @param articleNumerique the entity to save.
     * @return the persisted entity.
     */
    ArticleNumerique save(ArticleNumerique articleNumerique);

    /**
     * Get all the articleNumeriques.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleNumeriqueDTO> findAll(Pageable pageable);


    /**
     * Get the "id" articleNumerique.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ArticleNumeriqueDTO> findOne(Long id);

    /**
     * Delete the "id" articleNumerique.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the articleNumerique corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ArticleNumeriqueDTO> search(String query, Pageable pageable);
}
