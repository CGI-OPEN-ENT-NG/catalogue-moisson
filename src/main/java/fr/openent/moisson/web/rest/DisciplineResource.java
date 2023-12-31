package fr.openent.moisson.web.rest;

import fr.openent.moisson.service.DisciplineQueryService;
import fr.openent.moisson.service.DisciplineService;
import fr.openent.moisson.service.dto.DisciplineCriteria;
import fr.openent.moisson.service.dto.DisciplineDTO;
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
 * REST controller for managing {@link fr.openent.moisson.domain.Discipline}.
 */
@RestController
@RequestMapping("/api")
public class DisciplineResource {

    private final Logger log = LoggerFactory.getLogger(DisciplineResource.class);

    private static final String ENTITY_NAME = "discipline";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplineService disciplineService;

    private final DisciplineQueryService disciplineQueryService;

    public DisciplineResource(DisciplineService disciplineService, DisciplineQueryService disciplineQueryService) {
        this.disciplineService = disciplineService;
        this.disciplineQueryService = disciplineQueryService;
    }

    /**
     * {@code POST  /disciplines} : Create a new discipline.
     *
     * @param disciplineDTO the disciplineDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplineDTO, or with status {@code 400 (Bad Request)} if the discipline has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/disciplines")
    public ResponseEntity<DisciplineDTO> createDiscipline(@RequestBody DisciplineDTO disciplineDTO) throws URISyntaxException {
        log.debug("REST request to save Discipline : {}", disciplineDTO);
        if (disciplineDTO.getId() != null) {
            throw new BadRequestAlertException("A new discipline cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DisciplineDTO result = disciplineService.save(disciplineDTO);
        return ResponseEntity.created(new URI("/api/disciplines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /disciplines} : Updates an existing discipline.
     *
     * @param disciplineDTO the disciplineDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplineDTO,
     * or with status {@code 400 (Bad Request)} if the disciplineDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplineDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/disciplines")
    public ResponseEntity<DisciplineDTO> updateDiscipline(@RequestBody DisciplineDTO disciplineDTO) throws URISyntaxException {
        log.debug("REST request to update Discipline : {}", disciplineDTO);
        if (disciplineDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DisciplineDTO result = disciplineService.save(disciplineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplineDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /disciplines} : get all the disciplines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplines in body.
     */
    @GetMapping("/disciplines")
    public ResponseEntity<List<DisciplineDTO>> getAllDisciplines(DisciplineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Disciplines by criteria: {}", criteria);
        Page<DisciplineDTO> page = disciplineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /disciplines/count} : count all the disciplines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/disciplines/count")
    public ResponseEntity<Long> countDisciplines(DisciplineCriteria criteria) {
        log.debug("REST request to count Disciplines by criteria: {}", criteria);
        return ResponseEntity.ok().body(disciplineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /disciplines/:id} : get the "id" discipline.
     *
     * @param id the id of the disciplineDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplineDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/disciplines/{id}")
    public ResponseEntity<DisciplineDTO> getDiscipline(@PathVariable Long id) {
        log.debug("REST request to get Discipline : {}", id);
        Optional<DisciplineDTO> disciplineDTO = disciplineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(disciplineDTO);
    }

    /**
     * {@code DELETE  /disciplines/:id} : delete the "id" discipline.
     *
     * @param id the id of the disciplineDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/disciplines/{id}")
    public ResponseEntity<Void> deleteDiscipline(@PathVariable Long id) {
        log.debug("REST request to delete Discipline : {}", id);
        disciplineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/disciplines?query=:query} : search for the discipline corresponding
     * to the query.
     *
     * @param query the query of the discipline search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/disciplines")
    public ResponseEntity<List<DisciplineDTO>> searchDisciplines(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Disciplines for query {}", query);
        Page<DisciplineDTO> page = disciplineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
        }
}
