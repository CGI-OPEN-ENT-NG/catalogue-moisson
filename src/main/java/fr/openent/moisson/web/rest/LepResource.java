package fr.openent.moisson.web.rest;

import fr.openent.moisson.service.LepQueryService;
import fr.openent.moisson.service.LepService;
import fr.openent.moisson.service.dto.LepCriteria;
import fr.openent.moisson.service.dto.LepDTO;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link fr.openent.moisson.domain.Lep}.
 */
@RestController
@RequestMapping("/api")
public class LepResource {

    private final Logger log = LoggerFactory.getLogger(LepResource.class);

    private static final String ENTITY_NAME = "lep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LepService lepService;

    private final LepQueryService lepQueryService;

    public LepResource(LepService lepService, LepQueryService lepQueryService) {
        this.lepService = lepService;
        this.lepQueryService = lepQueryService;
    }

    /**
     * {@code POST  /leps} : Create a new lep.
     *
     * @param lepDTO the lepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new lepDTO, or with status {@code 400 (Bad Request)} if the lep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/leps")
    public ResponseEntity<LepDTO> createLep(@Valid @RequestBody LepDTO lepDTO) throws URISyntaxException {
        log.debug("REST request to save Lep : {}", lepDTO);
        if (lepDTO.getId() != null) {
            throw new BadRequestAlertException("A new lep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(lepDTO.getLicenceId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        LepDTO result = lepService.save(lepDTO);
        return ResponseEntity.created(new URI("/api/leps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /leps} : Updates an existing lep.
     *
     * @param lepDTO the lepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated lepDTO,
     * or with status {@code 400 (Bad Request)} if the lepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the lepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/leps")
    public ResponseEntity<LepDTO> updateLep(@Valid @RequestBody LepDTO lepDTO) throws URISyntaxException {
        log.debug("REST request to update Lep : {}", lepDTO);
        if (lepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LepDTO result = lepService.save(lepDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, lepDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /leps} : get all the leps.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leps in body.
     */
    @GetMapping("/leps")
    public ResponseEntity<List<LepDTO>> getAllLeps(LepCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Leps by criteria: {}", criteria);
        Page<LepDTO> page = lepQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /leps/count} : count all the leps.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/leps/count")
    public ResponseEntity<Long> countLeps(LepCriteria criteria) {
        log.debug("REST request to count Leps by criteria: {}", criteria);
        return ResponseEntity.ok().body(lepQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /leps/:id} : get the "id" lep.
     *
     * @param id the id of the lepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the lepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/leps/{id}")
    public ResponseEntity<LepDTO> getLep(@PathVariable Long id) {
        log.debug("REST request to get Lep : {}", id);
        Optional<LepDTO> lepDTO = lepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(lepDTO);
    }

    /**
     * {@code DELETE  /leps/:id} : delete the "id" lep.
     *
     * @param id the id of the lepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/leps/{id}")
    public ResponseEntity<Void> deleteLep(@PathVariable Long id) {
        log.debug("REST request to delete Lep : {}", id);
        lepService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/leps?query=:query} : search for the lep corresponding
     * to the query.
     *
     * @param query the query of the lep search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/leps")
    public ResponseEntity<List<LepDTO>> searchLeps(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Leps for query {}", query);
        Page<LepDTO> page = lepService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
