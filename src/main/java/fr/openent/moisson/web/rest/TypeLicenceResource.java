package fr.openent.moisson.web.rest;

import fr.openent.moisson.service.TypeLicenceService;
import fr.openent.moisson.web.rest.errors.BadRequestAlertException;
import fr.openent.moisson.service.dto.TypeLicenceDTO;
import fr.openent.moisson.service.dto.TypeLicenceCriteria;
import fr.openent.moisson.service.TypeLicenceQueryService;

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
 * REST controller for managing {@link fr.openent.moisson.domain.TypeLicence}.
 */
@RestController
@RequestMapping("/api")
public class TypeLicenceResource {

    private final Logger log = LoggerFactory.getLogger(TypeLicenceResource.class);

    private static final String ENTITY_NAME = "typeLicence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TypeLicenceService typeLicenceService;

    private final TypeLicenceQueryService typeLicenceQueryService;

    public TypeLicenceResource(TypeLicenceService typeLicenceService, TypeLicenceQueryService typeLicenceQueryService) {
        this.typeLicenceService = typeLicenceService;
        this.typeLicenceQueryService = typeLicenceQueryService;
    }

    /**
     * {@code POST  /type-licences} : Create a new typeLicence.
     *
     * @param typeLicenceDTO the typeLicenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new typeLicenceDTO, or with status {@code 400 (Bad Request)} if the typeLicence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/type-licences")
    public ResponseEntity<TypeLicenceDTO> createTypeLicence(@RequestBody TypeLicenceDTO typeLicenceDTO) throws URISyntaxException {
        log.debug("REST request to save TypeLicence : {}", typeLicenceDTO);
        if (typeLicenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new typeLicence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeLicenceDTO result = typeLicenceService.save(typeLicenceDTO);
        return ResponseEntity.created(new URI("/api/type-licences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /type-licences} : Updates an existing typeLicence.
     *
     * @param typeLicenceDTO the typeLicenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated typeLicenceDTO,
     * or with status {@code 400 (Bad Request)} if the typeLicenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the typeLicenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/type-licences")
    public ResponseEntity<TypeLicenceDTO> updateTypeLicence(@RequestBody TypeLicenceDTO typeLicenceDTO) throws URISyntaxException {
        log.debug("REST request to update TypeLicence : {}", typeLicenceDTO);
        if (typeLicenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TypeLicenceDTO result = typeLicenceService.save(typeLicenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, typeLicenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /type-licences} : get all the typeLicences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of typeLicences in body.
     */
    @GetMapping("/type-licences")
    public ResponseEntity<List<TypeLicenceDTO>> getAllTypeLicences(TypeLicenceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TypeLicences by criteria: {}", criteria);
        Page<TypeLicenceDTO> page = typeLicenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /type-licences/count} : count all the typeLicences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/type-licences/count")
    public ResponseEntity<Long> countTypeLicences(TypeLicenceCriteria criteria) {
        log.debug("REST request to count TypeLicences by criteria: {}", criteria);
        return ResponseEntity.ok().body(typeLicenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /type-licences/:id} : get the "id" typeLicence.
     *
     * @param id the id of the typeLicenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the typeLicenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/type-licences/{id}")
    public ResponseEntity<TypeLicenceDTO> getTypeLicence(@PathVariable Long id) {
        log.debug("REST request to get TypeLicence : {}", id);
        Optional<TypeLicenceDTO> typeLicenceDTO = typeLicenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(typeLicenceDTO);
    }

    /**
     * {@code DELETE  /type-licences/:id} : delete the "id" typeLicence.
     *
     * @param id the id of the typeLicenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/type-licences/{id}")
    public ResponseEntity<Void> deleteTypeLicence(@PathVariable Long id) {
        log.debug("REST request to delete TypeLicence : {}", id);
        typeLicenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/type-licences?query=:query} : search for the typeLicence corresponding
     * to the query.
     *
     * @param query the query of the typeLicence search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/type-licences")
    public ResponseEntity<List<TypeLicenceDTO>> searchTypeLicences(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TypeLicences for query {}", query);
        Page<TypeLicenceDTO> page = typeLicenceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
