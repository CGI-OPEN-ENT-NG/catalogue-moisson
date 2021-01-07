package fr.openent.moisson.web.rest;

import fr.openent.moisson.service.TvaQueryService;
import fr.openent.moisson.service.TvaService;
import fr.openent.moisson.service.dto.TvaCriteria;
import fr.openent.moisson.service.dto.TvaDTO;
import fr.openent.moisson.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.openent.moisson.domain.Tva}.
 */
@RestController
@RequestMapping("/api")
public class TvaResource {

    private final Logger log = LoggerFactory.getLogger(TvaResource.class);

    private static final String ENTITY_NAME = "tva";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TvaService tvaService;

    private final TvaQueryService tvaQueryService;

    public TvaResource(TvaService tvaService, TvaQueryService tvaQueryService) {
        this.tvaService = tvaService;
        this.tvaQueryService = tvaQueryService;
    }

    /**
     * {@code POST  /tvas} : Create a new tva.
     *
     * @param tvaDTO the tvaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tvaDTO, or with status {@code 400 (Bad Request)} if the tva has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tvas")
    public ResponseEntity<TvaDTO> createTva(@RequestBody TvaDTO tvaDTO) throws URISyntaxException {
        log.debug("REST request to save Tva : {}", tvaDTO);
        if (tvaDTO.getId() != null) {
            throw new BadRequestAlertException("A new tva cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TvaDTO result = tvaService.save(tvaDTO);
        return ResponseEntity.created(new URI("/api/tvas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tvas} : Updates an existing tva.
     *
     * @param tvaDTO the tvaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tvaDTO,
     * or with status {@code 400 (Bad Request)} if the tvaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tvaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tvas")
    public ResponseEntity<TvaDTO> updateTva(@RequestBody TvaDTO tvaDTO) throws URISyntaxException {
        log.debug("REST request to update Tva : {}", tvaDTO);
        if (tvaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TvaDTO result = tvaService.save(tvaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tvaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tvas} : get all the tvas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tvas in body.
     */
    @GetMapping("/tvas")
    public ResponseEntity<List<TvaDTO>> getAllTvas(TvaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Tvas by criteria: {}", criteria);
        Page<TvaDTO> page = tvaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tvas/count} : count all the tvas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tvas/count")
    public ResponseEntity<Long> countTvas(TvaCriteria criteria) {
        log.debug("REST request to count Tvas by criteria: {}", criteria);
        return ResponseEntity.ok().body(tvaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tvas/:id} : get the "id" tva.
     *
     * @param id the id of the tvaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tvaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tvas/{id}")
    public ResponseEntity<TvaDTO> getTva(@PathVariable Long id) {
        log.debug("REST request to get Tva : {}", id);
        Optional<TvaDTO> tvaDTO = tvaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tvaDTO);
    }

    /**
     * {@code DELETE  /tvas/:id} : delete the "id" tva.
     *
     * @param id the id of the tvaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tvas/{id}")
    public ResponseEntity<Void> deleteTva(@PathVariable Long id) {
        log.debug("REST request to delete Tva : {}", id);
        tvaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/tvas?query=:query} : search for the tva corresponding
     * to the query.
     *
     * @param query the query of the tva search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/tvas")
    public ResponseEntity<List<TvaDTO>> searchTvas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Tvas for query {}", query);
        Page<TvaDTO> page = tvaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
