package ar.ed.um.programacion2.web.rest;

import ar.ed.um.programacion2.domain.Distribuidor;
import ar.ed.um.programacion2.repository.DistribuidorRepository;
import ar.ed.um.programacion2.service.DistribuidorService;
import ar.ed.um.programacion2.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ar.ed.um.programacion2.domain.Distribuidor}.
 */
@RestController
@RequestMapping("/api")
public class DistribuidorResource {

    private final Logger log = LoggerFactory.getLogger(DistribuidorResource.class);

    private static final String ENTITY_NAME = "distribuidor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DistribuidorService distribuidorService;

    private final DistribuidorRepository distribuidorRepository;

    public DistribuidorResource(DistribuidorService distribuidorService, DistribuidorRepository distribuidorRepository) {
        this.distribuidorService = distribuidorService;
        this.distribuidorRepository = distribuidorRepository;
    }

    /**
     * {@code POST  /distribuidors} : Create a new distribuidor.
     *
     * @param distribuidor the distribuidor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new distribuidor, or with status {@code 400 (Bad Request)} if the distribuidor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/distribuidors")
    public ResponseEntity<Distribuidor> createDistribuidor(@RequestBody Distribuidor distribuidor) throws URISyntaxException {
        log.debug("REST request to save Distribuidor : {}", distribuidor);
        if (distribuidor.getId() != null) {
            throw new BadRequestAlertException("A new distribuidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Distribuidor result = distribuidorService.save(distribuidor);
        return ResponseEntity
            .created(new URI("/api/distribuidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /distribuidors/:id} : Updates an existing distribuidor.
     *
     * @param id the id of the distribuidor to save.
     * @param distribuidor the distribuidor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated distribuidor,
     * or with status {@code 400 (Bad Request)} if the distribuidor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the distribuidor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/distribuidors/{id}")
    public ResponseEntity<Distribuidor> updateDistribuidor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Distribuidor distribuidor
    ) throws URISyntaxException {
        log.debug("REST request to update Distribuidor : {}, {}", id, distribuidor);
        if (distribuidor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, distribuidor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!distribuidorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Distribuidor result = distribuidorService.save(distribuidor);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, distribuidor.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /distribuidors/:id} : Partial updates given fields of an existing distribuidor, field will ignore if it is null
     *
     * @param id the id of the distribuidor to save.
     * @param distribuidor the distribuidor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated distribuidor,
     * or with status {@code 400 (Bad Request)} if the distribuidor is not valid,
     * or with status {@code 404 (Not Found)} if the distribuidor is not found,
     * or with status {@code 500 (Internal Server Error)} if the distribuidor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/distribuidors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Distribuidor> partialUpdateDistribuidor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody Distribuidor distribuidor
    ) throws URISyntaxException {
        log.debug("REST request to partial update Distribuidor partially : {}, {}", id, distribuidor);
        if (distribuidor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, distribuidor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!distribuidorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Distribuidor> result = distribuidorService.partialUpdate(distribuidor);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, distribuidor.getId().toString())
        );
    }

    /**
     * {@code GET  /distribuidors} : get all the distribuidors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of distribuidors in body.
     */
    @GetMapping("/distribuidors")
    public ResponseEntity<List<Distribuidor>> getAllDistribuidors(Pageable pageable) {
        log.debug("REST request to get a page of Distribuidors");
        Page<Distribuidor> page = distribuidorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /distribuidors/:id} : get the "id" distribuidor.
     *
     * @param id the id of the distribuidor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the distribuidor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/distribuidors/{id}")
    public ResponseEntity<Distribuidor> getDistribuidor(@PathVariable Long id) {
        log.debug("REST request to get Distribuidor : {}", id);
        Optional<Distribuidor> distribuidor = distribuidorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(distribuidor);
    }

    /**
     * {@code DELETE  /distribuidors/:id} : delete the "id" distribuidor.
     *
     * @param id the id of the distribuidor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/distribuidors/{id}")
    public ResponseEntity<Void> deleteDistribuidor(@PathVariable Long id) {
        log.debug("REST request to delete Distribuidor : {}", id);
        distribuidorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
