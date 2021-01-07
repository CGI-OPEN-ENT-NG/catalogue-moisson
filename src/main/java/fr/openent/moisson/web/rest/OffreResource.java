package fr.openent.moisson.web.rest;

import fr.openent.moisson.service.OffreQueryService;
import fr.openent.moisson.service.OffreService;
import fr.openent.moisson.service.dto.OffreCriteria;
import fr.openent.moisson.service.dto.OffreDTO;
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
 * REST controller for managing {@link fr.openent.moisson.domain.Offre}.
 */
@RestController
@RequestMapping("/api")
public class OffreResource {

    private final Logger log = LoggerFactory.getLogger(OffreResource.class);

    private static final String ENTITY_NAME = "offre";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OffreService offreService;

    private final OffreQueryService offreQueryService;

    public OffreResource(OffreService offreService, OffreQueryService offreQueryService) {
        this.offreService = offreService;
        this.offreQueryService = offreQueryService;
    }

    /**
     * {@code POST  /offres} : Create a new offre.
     *
     * @param offreDTO the offreDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offreDTO, or with status {@code 400 (Bad Request)} if the offre has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/offres")
    public ResponseEntity<OffreDTO> createOffre(@Valid @RequestBody OffreDTO offreDTO) throws URISyntaxException {
        log.debug("REST request to save Offre : {}", offreDTO);
        if (offreDTO.getId() != null) {
            throw new BadRequestAlertException("A new offre cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(offreDTO.getLicenceId())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        OffreDTO result = offreService.save(offreDTO);
        return ResponseEntity.created(new URI("/api/offres/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /offres} : Updates an existing offre.
     *
     * @param offreDTO the offreDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offreDTO,
     * or with status {@code 400 (Bad Request)} if the offreDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offreDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/offres")
    public ResponseEntity<OffreDTO> updateOffre(@Valid @RequestBody OffreDTO offreDTO) throws URISyntaxException {
        log.debug("REST request to update Offre : {}", offreDTO);
        if (offreDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OffreDTO result = offreService.save(offreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, offreDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /offres} : get all the offres.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offres in body.
     */
    @GetMapping("/offres")
    public ResponseEntity<List<OffreDTO>> getAllOffres(OffreCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Offres by criteria: {}", criteria);
        Page<OffreDTO> page = offreQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /offres/count} : count all the offres.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/offres/count")
    public ResponseEntity<Long> countOffres(OffreCriteria criteria) {
        log.debug("REST request to count Offres by criteria: {}", criteria);
        return ResponseEntity.ok().body(offreQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /offres/:id} : get the "id" offre.
     *
     * @param id the id of the offreDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offreDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offres/{id}")
    public ResponseEntity<OffreDTO> getOffre(@PathVariable Long id) {
        log.debug("REST request to get Offre : {}", id);
        Optional<OffreDTO> offreDTO = offreService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offreDTO);
    }

    /**
     * {@code DELETE  /offres/:id} : delete the "id" offre.
     *
     * @param id the id of the offreDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offres/{id}")
    public ResponseEntity<Void> deleteOffre(@PathVariable Long id) {
        log.debug("REST request to delete Offre : {}", id);
        offreService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/offres?query=:query} : search for the offre corresponding
     * to the query.
     *
     * @param query the query of the offre search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/offres")
    public ResponseEntity<List<OffreDTO>> searchOffres(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Offres for query {}", query);
        Page<OffreDTO> page = offreService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
