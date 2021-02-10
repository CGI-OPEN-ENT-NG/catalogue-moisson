package fr.openent.moisson.service;

import fr.openent.moisson.service.dto.DisponibiliteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link fr.openent.moisson.domain.Disponibilite}.
 */
public interface DisponibiliteService {

    /**
     * Save a disponibilite.
     *
     * @param disponibiliteDTO the entity to save.
     * @return the persisted entity.
     */
    DisponibiliteDTO save(DisponibiliteDTO disponibiliteDTO);

    /**
     * Get all the disponibilites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisponibiliteDTO> findAll(Pageable pageable);
    /**
     * Get all the DisponibiliteDTO where ArticlePapier is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DisponibiliteDTO> findAllWhereArticlePapierIsNull();
    /**
     * Get all the DisponibiliteDTO where ArticleNumerique is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<DisponibiliteDTO> findAllWhereArticleNumeriqueIsNull();


    /**
     * Get the "id" disponibilite.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DisponibiliteDTO> findOne(Long id);

    /**
     * Delete the "id" disponibilite.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the disponibilite corresponding to the query.
     *
     * @param query the query of the search.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DisponibiliteDTO> search(String query, Pageable pageable);
}
