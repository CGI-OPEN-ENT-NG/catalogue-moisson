package fr.openent.moisson.web.rest;

import fr.openent.moisson.service.TechnoService;
import fr.openent.moisson.web.rest.errors.BadRequestAlertException;
import fr.openent.moisson.service.dto.TechnoDTO;
import fr.openent.moisson.service.dto.TechnoCriteria;
import fr.openent.moisson.service.TechnoQueryService;

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
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link fr.openent.moisson.domain.Techno}.
 */
@RestController
@RequestMapping("/api")
public class TechnoResource {

    private final Logger log = LoggerFactory.getLogger(TechnoResource.class);

    private static final String ENTITY_NAME = "techno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TechnoService technoService;

    private final TechnoQueryService technoQueryService;

    public TechnoResource(TechnoService technoService, TechnoQueryService technoQueryService) {
        this.technoService = technoService;
        this.technoQueryService = technoQueryService;
    }

    /**
     * {@code POST  /technos} : Create a new techno.
     *
     * @param technoDTO the technoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new technoDTO, or with status {@code 400 (Bad Request)} if the techno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/technos")
    public ResponseEntity<TechnoDTO> createTechno(@Valid @RequestBody TechnoDTO technoDTO) throws URISyntaxException {
        log.debug("REST request to save Techno : {}", technoDTO);
        if (technoDTO.getId() != null) {
            throw new BadRequestAlertException("A new techno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TechnoDTO result = technoService.save(technoDTO);
        return ResponseEntity.created(new URI("/api/technos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /technos} : Updates an existing techno.
     *
     * @param technoDTO the technoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated technoDTO,
     * or with status {@code 400 (Bad Request)} if the technoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the technoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/technos")
    public ResponseEntity<TechnoDTO> updateTechno(@Valid @RequestBody TechnoDTO technoDTO) throws URISyntaxException {
        log.debug("REST request to update Techno : {}", technoDTO);
        if (technoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TechnoDTO result = technoService.save(technoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, technoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /technos} : get all the technos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of technos in body.
     */
    @GetMapping("/technos")
    public ResponseEntity<List<TechnoDTO>> getAllTechnos(TechnoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Technos by criteria: {}", criteria);
        Page<TechnoDTO> page = technoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /technos/count} : count all the technos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/technos/count")
    public ResponseEntity<Long> countTechnos(TechnoCriteria criteria) {
        log.debug("REST request to count Technos by criteria: {}", criteria);
        return ResponseEntity.ok().body(technoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /technos/:id} : get the "id" techno.
     *
     * @param id the id of the technoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the technoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/technos/{id}")
    public ResponseEntity<TechnoDTO> getTechno(@PathVariable Long id) {
        log.debug("REST request to get Techno : {}", id);
        Optional<TechnoDTO> technoDTO = technoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(technoDTO);
    }

    /**
     * {@code DELETE  /technos/:id} : delete the "id" techno.
     *
     * @param id the id of the technoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/technos/{id}")
    public ResponseEntity<Void> deleteTechno(@PathVariable Long id) {
        log.debug("REST request to delete Techno : {}", id);
        technoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/technos?query=:query} : search for the techno corresponding
     * to the query.
     *
     * @param query the query of the techno search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/technos")
    public ResponseEntity<List<TechnoDTO>> searchTechnos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Technos for query {}", query);
        Page<TechnoDTO> page = technoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
