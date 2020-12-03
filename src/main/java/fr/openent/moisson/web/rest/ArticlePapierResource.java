package fr.openent.moisson.web.rest;

import fr.openent.moisson.service.ArticlePapierService;
import fr.openent.moisson.web.rest.errors.BadRequestAlertException;
import fr.openent.moisson.service.dto.ArticlePapierDTO;
import fr.openent.moisson.service.dto.ArticlePapierCriteria;
import fr.openent.moisson.service.ArticlePapierQueryService;

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
 * REST controller for managing {@link fr.openent.moisson.domain.ArticlePapier}.
 */
@RestController
@RequestMapping("/api")
public class ArticlePapierResource {

    private final Logger log = LoggerFactory.getLogger(ArticlePapierResource.class);

    private static final String ENTITY_NAME = "articlePapier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArticlePapierService articlePapierService;

    private final ArticlePapierQueryService articlePapierQueryService;

    public ArticlePapierResource(ArticlePapierService articlePapierService, ArticlePapierQueryService articlePapierQueryService) {
        this.articlePapierService = articlePapierService;
        this.articlePapierQueryService = articlePapierQueryService;
    }

    /**
     * {@code POST  /article-papiers} : Create a new articlePapier.
     *
     * @param articlePapierDTO the articlePapierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new articlePapierDTO, or with status {@code 400 (Bad Request)} if the articlePapier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/article-papiers")
    public ResponseEntity<ArticlePapierDTO> createArticlePapier(@Valid @RequestBody ArticlePapierDTO articlePapierDTO) throws URISyntaxException {
        log.debug("REST request to save ArticlePapier : {}", articlePapierDTO);
        if (articlePapierDTO.getId() != null) {
            throw new BadRequestAlertException("A new articlePapier cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(articlePapierDTO.getDisponibiliteId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        ArticlePapierDTO result = articlePapierService.save(articlePapierDTO);
        return ResponseEntity.created(new URI("/api/article-papiers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /article-papiers} : Updates an existing articlePapier.
     *
     * @param articlePapierDTO the articlePapierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated articlePapierDTO,
     * or with status {@code 400 (Bad Request)} if the articlePapierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the articlePapierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/article-papiers")
    public ResponseEntity<ArticlePapierDTO> updateArticlePapier(@Valid @RequestBody ArticlePapierDTO articlePapierDTO) throws URISyntaxException {
        log.debug("REST request to update ArticlePapier : {}", articlePapierDTO);
        if (articlePapierDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ArticlePapierDTO result = articlePapierService.save(articlePapierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, articlePapierDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /article-papiers} : get all the articlePapiers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of articlePapiers in body.
     */
    @GetMapping("/article-papiers")
    public ResponseEntity<List<ArticlePapierDTO>> getAllArticlePapiers(ArticlePapierCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ArticlePapiers by criteria: {}", criteria);
        Page<ArticlePapierDTO> page = articlePapierQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /article-papiers/count} : count all the articlePapiers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/article-papiers/count")
    public ResponseEntity<Long> countArticlePapiers(ArticlePapierCriteria criteria) {
        log.debug("REST request to count ArticlePapiers by criteria: {}", criteria);
        return ResponseEntity.ok().body(articlePapierQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /article-papiers/:id} : get the "id" articlePapier.
     *
     * @param id the id of the articlePapierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the articlePapierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/article-papiers/{id}")
    public ResponseEntity<ArticlePapierDTO> getArticlePapier(@PathVariable Long id) {
        log.debug("REST request to get ArticlePapier : {}", id);
        Optional<ArticlePapierDTO> articlePapierDTO = articlePapierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(articlePapierDTO);
    }

    /**
     * {@code DELETE  /article-papiers/:id} : delete the "id" articlePapier.
     *
     * @param id the id of the articlePapierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/article-papiers/{id}")
    public ResponseEntity<Void> deleteArticlePapier(@PathVariable Long id) {
        log.debug("REST request to delete ArticlePapier : {}", id);
        articlePapierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/article-papiers?query=:query} : search for the articlePapier corresponding
     * to the query.
     *
     * @param query the query of the articlePapier search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/article-papiers")
    public ResponseEntity<List<ArticlePapierDTO>> searchArticlePapiers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ArticlePapiers for query {}", query);
        Page<ArticlePapierDTO> page = articlePapierService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
