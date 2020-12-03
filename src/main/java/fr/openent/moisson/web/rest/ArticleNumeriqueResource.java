package fr.openent.moisson.web.rest;

import fr.openent.moisson.service.ArticleNumeriqueService;
import fr.openent.moisson.web.rest.errors.BadRequestAlertException;
import fr.openent.moisson.service.dto.ArticleNumeriqueDTO;
import fr.openent.moisson.service.dto.ArticleNumeriqueCriteria;
import fr.openent.moisson.service.ArticleNumeriqueQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link fr.openent.moisson.domain.ArticleNumerique}.
 */
@RestController
@RequestMapping("/api")
public class ArticleNumeriqueResource {

    private final Logger log = LoggerFactory.getLogger(ArticleNumeriqueResource.class);

    private static final String ENTITY_NAME = "articleNumerique";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticleNumeriqueService articleNumeriqueService;

    private final ArticleNumeriqueQueryService articleNumeriqueQueryService;

    public ArticleNumeriqueResource(ArticleNumeriqueService articleNumeriqueService, ArticleNumeriqueQueryService articleNumeriqueQueryService) {
        this.articleNumeriqueService = articleNumeriqueService;
        this.articleNumeriqueQueryService = articleNumeriqueQueryService;
    }

    /**
     * {@code POST  /article-numeriques} : Create a new articleNumerique.
     *
     * @param articleNumeriqueDTO the articleNumeriqueDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articleNumeriqueDTO, or with status {@code 400 (Bad Request)} if the articleNumerique has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/article-numeriques")
    public ResponseEntity<ArticleNumeriqueDTO> createArticleNumerique(@Valid @RequestBody ArticleNumeriqueDTO articleNumeriqueDTO) throws URISyntaxException {
        log.debug("REST request to save ArticleNumerique : {}", articleNumeriqueDTO);
        if (articleNumeriqueDTO.getId() != null) {
            throw new BadRequestAlertException("A new articleNumerique cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(articleNumeriqueDTO.getDisponibiliteId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        ArticleNumeriqueDTO result = articleNumeriqueService.save(articleNumeriqueDTO);
        return ResponseEntity.created(new URI("/api/article-numeriques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /article-numeriques} : Updates an existing articleNumerique.
     *
     * @param articleNumeriqueDTO the articleNumeriqueDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articleNumeriqueDTO,
     * or with status {@code 400 (Bad Request)} if the articleNumeriqueDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articleNumeriqueDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/article-numeriques")
    public ResponseEntity<ArticleNumeriqueDTO> updateArticleNumerique(@Valid @RequestBody ArticleNumeriqueDTO articleNumeriqueDTO) throws URISyntaxException {
        log.debug("REST request to update ArticleNumerique : {}", articleNumeriqueDTO);
        if (articleNumeriqueDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArticleNumeriqueDTO result = articleNumeriqueService.save(articleNumeriqueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articleNumeriqueDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /article-numeriques} : get all the articleNumeriques.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articleNumeriques in body.
     */
    @GetMapping("/article-numeriques")
    public ResponseEntity<List<ArticleNumeriqueDTO>> getAllArticleNumeriques(ArticleNumeriqueCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ArticleNumeriques by criteria: {}", criteria);
        Page<ArticleNumeriqueDTO> page = articleNumeriqueQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-numeriques/count} : count all the articleNumeriques.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/article-numeriques/count")
    public ResponseEntity<Long> countArticleNumeriques(ArticleNumeriqueCriteria criteria) {
        log.debug("REST request to count ArticleNumeriques by criteria: {}", criteria);
        return ResponseEntity.ok().body(articleNumeriqueQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /article-numeriques/:id} : get the "id" articleNumerique.
     *
     * @param id the id of the articleNumeriqueDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articleNumeriqueDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/article-numeriques/{id}")
    public ResponseEntity<ArticleNumeriqueDTO> getArticleNumerique(@PathVariable Long id) {
        log.debug("REST request to get ArticleNumerique : {}", id);
        Optional<ArticleNumeriqueDTO> articleNumeriqueDTO = articleNumeriqueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articleNumeriqueDTO);
    }

    /**
     * {@code DELETE  /article-numeriques/:id} : delete the "id" articleNumerique.
     *
     * @param id the id of the articleNumeriqueDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/article-numeriques/{id}")
    public ResponseEntity<Void> deleteArticleNumerique(@PathVariable Long id) {
        log.debug("REST request to delete ArticleNumerique : {}", id);
        articleNumeriqueService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/article-numeriques?query=:query} : search for the articleNumerique corresponding
     * to the query.
     *
     * @param query the query of the articleNumerique search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/article-numeriques")
    public ResponseEntity<List<ArticleNumeriqueDTO>> searchArticleNumeriques(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ArticleNumeriques for query {}", query);
        Page<ArticleNumeriqueDTO> page = articleNumeriqueService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
