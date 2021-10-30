package ar.ed.um.programacion2.web.rest;

import ar.ed.um.programacion2.domain.ProductoCarro;
import ar.ed.um.programacion2.repository.ProductoCarroRepository;
import ar.ed.um.programacion2.service.ProductoCarroService;
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
 * REST controller for managing {@link ar.ed.um.programacion2.domain.ProductoCarro}.
 */
@RestController
@RequestMapping("/api")
public class ProductoCarroResource {

    private final Logger log = LoggerFactory.getLogger(ProductoCarroResource.class);

    private static final String ENTITY_NAME = "productoCarro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoCarroService productoCarroService;

    private final ProductoCarroRepository productoCarroRepository;

    public ProductoCarroResource(ProductoCarroService productoCarroService, ProductoCarroRepository productoCarroRepository) {
        this.productoCarroService = productoCarroService;
        this.productoCarroRepository = productoCarroRepository;
    }

    /**
     * {@code POST  /producto-carros} : Create a new productoCarro.
     *
     * @param productoCarro the productoCarro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoCarro, or with status {@code 400 (Bad Request)} if the productoCarro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-carros")
    public ResponseEntity<ProductoCarro> createProductoCarro(@RequestBody ProductoCarro productoCarro) throws URISyntaxException {
        log.debug("REST request to save ProductoCarro : {}", productoCarro);
        if (productoCarro.getId() != null) {
            throw new BadRequestAlertException("A new productoCarro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoCarro result = productoCarroService.save(productoCarro);
        return ResponseEntity
            .created(new URI("/api/producto-carros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-carros/:id} : Updates an existing productoCarro.
     *
     * @param id the id of the productoCarro to save.
     * @param productoCarro the productoCarro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoCarro,
     * or with status {@code 400 (Bad Request)} if the productoCarro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoCarro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-carros/{id}")
    public ResponseEntity<ProductoCarro> updateProductoCarro(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductoCarro productoCarro
    ) throws URISyntaxException {
        log.debug("REST request to update ProductoCarro : {}, {}", id, productoCarro);
        if (productoCarro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoCarro.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoCarroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductoCarro result = productoCarroService.save(productoCarro);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoCarro.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /producto-carros/:id} : Partial updates given fields of an existing productoCarro, field will ignore if it is null
     *
     * @param id the id of the productoCarro to save.
     * @param productoCarro the productoCarro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoCarro,
     * or with status {@code 400 (Bad Request)} if the productoCarro is not valid,
     * or with status {@code 404 (Not Found)} if the productoCarro is not found,
     * or with status {@code 500 (Internal Server Error)} if the productoCarro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/producto-carros/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductoCarro> partialUpdateProductoCarro(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductoCarro productoCarro
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductoCarro partially : {}, {}", id, productoCarro);
        if (productoCarro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoCarro.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoCarroRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductoCarro> result = productoCarroService.partialUpdate(productoCarro);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoCarro.getId().toString())
        );
    }

    /**
     * {@code GET  /producto-carros} : get all the productoCarros.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoCarros in body.
     */
    @GetMapping("/producto-carros")
    public ResponseEntity<List<ProductoCarro>> getAllProductoCarros(Pageable pageable) {
        log.debug("REST request to get a page of ProductoCarros");
        Page<ProductoCarro> page = productoCarroService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /producto-carros/:id} : get the "id" productoCarro.
     *
     * @param id the id of the productoCarro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoCarro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-carros/{id}")
    public ResponseEntity<ProductoCarro> getProductoCarro(@PathVariable Long id) {
        log.debug("REST request to get ProductoCarro : {}", id);
        Optional<ProductoCarro> productoCarro = productoCarroService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoCarro);
    }

    /**
     * {@code DELETE  /producto-carros/:id} : delete the "id" productoCarro.
     *
     * @param id the id of the productoCarro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-carros/{id}")
    public ResponseEntity<Void> deleteProductoCarro(@PathVariable Long id) {
        log.debug("REST request to delete ProductoCarro : {}", id);
        productoCarroService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
