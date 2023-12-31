package fr.openent.moisson.web.rest;

import fr.openent.moisson.service.LicenceQueryService;
import fr.openent.moisson.service.LicenceService;
import fr.openent.moisson.service.dto.LicenceCriteria;
import fr.openent.moisson.service.dto.LicenceDTO;
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
 * REST controller for managing {@link fr.openent.moisson.domain.Licence}.
 */
@RestController
@RequestMapping("/api")
public class LicenceResource {

    private final Logger log = LoggerFactory.getLogger(LicenceResource.class);

    private static final String ENTITY_NAME = "licence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicenceService licenceService;

    private final LicenceQueryService licenceQueryService;

    public LicenceResource(LicenceService licenceService, LicenceQueryService licenceQueryService) {
        this.licenceService = licenceService;
        this.licenceQueryService = licenceQueryService;
    }

    /**
     * {@code POST  /licences} : Create a new licence.
     *
     * @param licenceDTO the licenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licenceDTO, or with status {@code 400 (Bad Request)} if the licence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/licences")
    public ResponseEntity<LicenceDTO> createLicence(@RequestBody LicenceDTO licenceDTO) throws URISyntaxException {
        log.debug("REST request to save Licence : {}", licenceDTO);
        if (licenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new licence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LicenceDTO result = licenceService.save(licenceDTO);
        return ResponseEntity.created(new URI("/api/licences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /licences} : Updates an existing licence.
     *
     * @param licenceDTO the licenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenceDTO,
     * or with status {@code 400 (Bad Request)} if the licenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the licenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/licences")
    public ResponseEntity<LicenceDTO> updateLicence(@RequestBody LicenceDTO licenceDTO) throws URISyntaxException {
        log.debug("REST request to update Licence : {}", licenceDTO);
        if (licenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LicenceDTO result = licenceService.save(licenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, licenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /licences} : get all the licences.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licences in body.
     */
    @GetMapping("/licences")
    public ResponseEntity<List<LicenceDTO>> getAllLicences(LicenceCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Licences by criteria: {}", criteria);
        Page<LicenceDTO> page = licenceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /licences/count} : count all the licences.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/licences/count")
    public ResponseEntity<Long> countLicences(LicenceCriteria criteria) {
        log.debug("REST request to count Licences by criteria: {}", criteria);
        return ResponseEntity.ok().body(licenceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /licences/:id} : get the "id" licence.
     *
     * @param id the id of the licenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/licences/{id}")
    public ResponseEntity<LicenceDTO> getLicence(@PathVariable Long id) {
        log.debug("REST request to get Licence : {}", id);
        Optional<LicenceDTO> licenceDTO = licenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(licenceDTO);
    }

    /**
     * {@code DELETE  /licences/:id} : delete the "id" licence.
     *
     * @param id the id of the licenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/licences/{id}")
    public ResponseEntity<Void> deleteLicence(@PathVariable Long id) {
        log.debug("REST request to delete Licence : {}", id);
        licenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/licences?query=:query} : search for the licence corresponding
     * to the query.
     *
     * @param query the query of the licence search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/licences")
    public ResponseEntity<List<LicenceDTO>> searchLicences(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Licences for query {}", query);
        Page<LicenceDTO> page = licenceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
