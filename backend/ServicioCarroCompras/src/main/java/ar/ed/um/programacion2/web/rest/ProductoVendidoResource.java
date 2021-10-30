package ar.ed.um.programacion2.web.rest;

import ar.ed.um.programacion2.domain.ProductoVendido;
import ar.ed.um.programacion2.repository.ProductoVendidoRepository;
import ar.ed.um.programacion2.service.ProductoVendidoService;
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
 * REST controller for managing {@link ar.ed.um.programacion2.domain.ProductoVendido}.
 */
@RestController
@RequestMapping("/api")
public class ProductoVendidoResource {

    private final Logger log = LoggerFactory.getLogger(ProductoVendidoResource.class);

    private static final String ENTITY_NAME = "productoVendido";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductoVendidoService productoVendidoService;

    private final ProductoVendidoRepository productoVendidoRepository;

    public ProductoVendidoResource(ProductoVendidoService productoVendidoService, ProductoVendidoRepository productoVendidoRepository) {
        this.productoVendidoService = productoVendidoService;
        this.productoVendidoRepository = productoVendidoRepository;
    }

    /**
     * {@code POST  /producto-vendidos} : Create a new productoVendido.
     *
     * @param productoVendido the productoVendido to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productoVendido, or with status {@code 400 (Bad Request)} if the productoVendido has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/producto-vendidos")
    public ResponseEntity<ProductoVendido> createProductoVendido(@RequestBody ProductoVendido productoVendido) throws URISyntaxException {
        log.debug("REST request to save ProductoVendido : {}", productoVendido);
        if (productoVendido.getId() != null) {
            throw new BadRequestAlertException("A new productoVendido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductoVendido result = productoVendidoService.save(productoVendido);
        return ResponseEntity
            .created(new URI("/api/producto-vendidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /producto-vendidos/:id} : Updates an existing productoVendido.
     *
     * @param id the id of the productoVendido to save.
     * @param productoVendido the productoVendido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoVendido,
     * or with status {@code 400 (Bad Request)} if the productoVendido is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productoVendido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/producto-vendidos/{id}")
    public ResponseEntity<ProductoVendido> updateProductoVendido(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductoVendido productoVendido
    ) throws URISyntaxException {
        log.debug("REST request to update ProductoVendido : {}, {}", id, productoVendido);
        if (productoVendido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoVendido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoVendidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductoVendido result = productoVendidoService.save(productoVendido);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoVendido.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /producto-vendidos/:id} : Partial updates given fields of an existing productoVendido, field will ignore if it is null
     *
     * @param id the id of the productoVendido to save.
     * @param productoVendido the productoVendido to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productoVendido,
     * or with status {@code 400 (Bad Request)} if the productoVendido is not valid,
     * or with status {@code 404 (Not Found)} if the productoVendido is not found,
     * or with status {@code 500 (Internal Server Error)} if the productoVendido couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/producto-vendidos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductoVendido> partialUpdateProductoVendido(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductoVendido productoVendido
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductoVendido partially : {}, {}", id, productoVendido);
        if (productoVendido.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productoVendido.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productoVendidoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductoVendido> result = productoVendidoService.partialUpdate(productoVendido);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productoVendido.getId().toString())
        );
    }

    /**
     * {@code GET  /producto-vendidos} : get all the productoVendidos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productoVendidos in body.
     */
    @GetMapping("/producto-vendidos")
    public ResponseEntity<List<ProductoVendido>> getAllProductoVendidos(Pageable pageable) {
        log.debug("REST request to get a page of ProductoVendidos");
        Page<ProductoVendido> page = productoVendidoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /producto-vendidos/:id} : get the "id" productoVendido.
     *
     * @param id the id of the productoVendido to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productoVendido, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/producto-vendidos/{id}")
    public ResponseEntity<ProductoVendido> getProductoVendido(@PathVariable Long id) {
        log.debug("REST request to get ProductoVendido : {}", id);
        Optional<ProductoVendido> productoVendido = productoVendidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productoVendido);
    }

    /**
     * {@code DELETE  /producto-vendidos/:id} : delete the "id" productoVendido.
     *
     * @param id the id of the productoVendido to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/producto-vendidos/{id}")
    public ResponseEntity<Void> deleteProductoVendido(@PathVariable Long id) {
        log.debug("REST request to delete ProductoVendido : {}", id);
        productoVendidoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
