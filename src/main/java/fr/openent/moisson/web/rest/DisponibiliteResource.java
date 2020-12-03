package fr.openent.moisson.web.rest;

import fr.openent.moisson.service.DisponibiliteService;
import fr.openent.moisson.web.rest.errors.BadRequestAlertException;
import fr.openent.moisson.service.dto.DisponibiliteDTO;
import fr.openent.moisson.service.dto.DisponibiliteCriteria;
import fr.openent.moisson.service.DisponibiliteQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link fr.openent.moisson.domain.Disponibilite}.
 */
@RestController
@RequestMapping("/api")
public class DisponibiliteResource {

    private final Logger log = LoggerFactory.getLogger(DisponibiliteResource.class);

    private static final String ENTITY_NAME = "disponibilite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisponibiliteService disponibiliteService;

    private final DisponibiliteQueryService disponibiliteQueryService;

    public DisponibiliteResource(DisponibiliteService disponibiliteService, DisponibiliteQueryService disponibiliteQueryService) {
        this.disponibiliteService = disponibiliteService;
        this.disponibiliteQueryService = disponibiliteQueryService;
    }

    /**
     * {@code POST  /disponibilites} : Create a new disponibilite.
     *
     * @param disponibiliteDTO the disponibiliteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disponibiliteDTO, or with status {@code 400 (Bad Request)} if the disponibilite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disponibilites")
    public ResponseEntity<DisponibiliteDTO> createDisponibilite(@RequestBody DisponibiliteDTO disponibiliteDTO) throws URISyntaxException {
        log.debug("REST request to save Disponibilite : {}", disponibiliteDTO);
        if (disponibiliteDTO.getId() != null) {
            throw new BadRequestAlertException("A new disponibilite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisponibiliteDTO result = disponibiliteService.save(disponibiliteDTO);
        return ResponseEntity.created(new URI("/api/disponibilites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disponibilites} : Updates an existing disponibilite.
     *
     * @param disponibiliteDTO the disponibiliteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disponibiliteDTO,
     * or with status {@code 400 (Bad Request)} if the disponibiliteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disponibiliteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disponibilites")
    public ResponseEntity<DisponibiliteDTO> updateDisponibilite(@RequestBody DisponibiliteDTO disponibiliteDTO) throws URISyntaxException {
        log.debug("REST request to update Disponibilite : {}", disponibiliteDTO);
        if (disponibiliteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisponibiliteDTO result = disponibiliteService.save(disponibiliteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disponibiliteDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /disponibilites} : get all the disponibilites.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disponibilites in body.
     */
    @GetMapping("/disponibilites")
    public ResponseEntity<List<DisponibiliteDTO>> getAllDisponibilites(DisponibiliteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Disponibilites by criteria: {}", criteria);
        Page<DisponibiliteDTO> page = disponibiliteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disponibilites/count} : count all the disponibilites.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/disponibilites/count")
    public ResponseEntity<Long> countDisponibilites(DisponibiliteCriteria criteria) {
        log.debug("REST request to count Disponibilites by criteria: {}", criteria);
        return ResponseEntity.ok().body(disponibiliteQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /disponibilites/:id} : get the "id" disponibilite.
     *
     * @param id the id of the disponibiliteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disponibiliteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disponibilites/{id}")
    public ResponseEntity<DisponibiliteDTO> getDisponibilite(@PathVariable Long id) {
        log.debug("REST request to get Disponibilite : {}", id);
        Optional<DisponibiliteDTO> disponibiliteDTO = disponibiliteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disponibiliteDTO);
    }

    /**
     * {@code DELETE  /disponibilites/:id} : delete the "id" disponibilite.
     *
     * @param id the id of the disponibiliteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disponibilites/{id}")
    public ResponseEntity<Void> deleteDisponibilite(@PathVariable Long id) {
        log.debug("REST request to delete Disponibilite : {}", id);
        disponibiliteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/disponibilites?query=:query} : search for the disponibilite corresponding
     * to the query.
     *
     * @param query the query of the disponibilite search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/disponibilites")
    public ResponseEntity<List<DisponibiliteDTO>> searchDisponibilites(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Disponibilites for query {}", query);
        Page<DisponibiliteDTO> page = disponibiliteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
