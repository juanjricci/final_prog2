package ar.ed.um.programacion2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.ed.um.programacion2.IntegrationTest;
import ar.ed.um.programacion2.domain.ProductoCarro;
import ar.ed.um.programacion2.repository.ProductoCarroRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProductoCarroResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductoCarroResourceIT {

    private static final Integer DEFAULT_ID_PRODUCTO = 1;
    private static final Integer UPDATED_ID_PRODUCTO = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final Long DEFAULT_PRECIO = 1L;
    private static final Long UPDATED_PRECIO = 2L;

    private static final String ENTITY_API_URL = "/api/producto-carros";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoCarroRepository productoCarroRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoCarroMockMvc;

    private ProductoCarro productoCarro;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoCarro createEntity(EntityManager em) {
        ProductoCarro productoCarro = new ProductoCarro()
            .idProducto(DEFAULT_ID_PRODUCTO)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .cantidad(DEFAULT_CANTIDAD)
            .precio(DEFAULT_PRECIO);
        return productoCarro;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoCarro createUpdatedEntity(EntityManager em) {
        ProductoCarro productoCarro = new ProductoCarro()
            .idProducto(UPDATED_ID_PRODUCTO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .cantidad(UPDATED_CANTIDAD)
            .precio(UPDATED_PRECIO);
        return productoCarro;
    }

    @BeforeEach
    public void initTest() {
        productoCarro = createEntity(em);
    }

    @Test
    @Transactional
    void createProductoCarro() throws Exception {
        int databaseSizeBeforeCreate = productoCarroRepository.findAll().size();
        // Create the ProductoCarro
        restProductoCarroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoCarro)))
            .andExpect(status().isCreated());

        // Validate the ProductoCarro in the database
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoCarro testProductoCarro = productoCarroList.get(productoCarroList.size() - 1);
        assertThat(testProductoCarro.getIdProducto()).isEqualTo(DEFAULT_ID_PRODUCTO);
        assertThat(testProductoCarro.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProductoCarro.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProductoCarro.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProductoCarro.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    void createProductoCarroWithExistingId() throws Exception {
        // Create the ProductoCarro with an existing ID
        productoCarro.setId(1L);

        int databaseSizeBeforeCreate = productoCarroRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoCarroMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoCarro)))
            .andExpect(status().isBadRequest());

        // Validate the ProductoCarro in the database
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductoCarros() throws Exception {
        // Initialize the database
        productoCarroRepository.saveAndFlush(productoCarro);

        // Get all the productoCarroList
        restProductoCarroMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoCarro.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProducto").value(hasItem(DEFAULT_ID_PRODUCTO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())));
    }

    @Test
    @Transactional
    void getProductoCarro() throws Exception {
        // Initialize the database
        productoCarroRepository.saveAndFlush(productoCarro);

        // Get the productoCarro
        restProductoCarroMockMvc
            .perform(get(ENTITY_API_URL_ID, productoCarro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productoCarro.getId().intValue()))
            .andExpect(jsonPath("$.idProducto").value(DEFAULT_ID_PRODUCTO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingProductoCarro() throws Exception {
        // Get the productoCarro
        restProductoCarroMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductoCarro() throws Exception {
        // Initialize the database
        productoCarroRepository.saveAndFlush(productoCarro);

        int databaseSizeBeforeUpdate = productoCarroRepository.findAll().size();

        // Update the productoCarro
        ProductoCarro updatedProductoCarro = productoCarroRepository.findById(productoCarro.getId()).get();
        // Disconnect from session so that the updates on updatedProductoCarro are not directly saved in db
        em.detach(updatedProductoCarro);
        updatedProductoCarro
            .idProducto(UPDATED_ID_PRODUCTO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .cantidad(UPDATED_CANTIDAD)
            .precio(UPDATED_PRECIO);

        restProductoCarroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductoCarro.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductoCarro))
            )
            .andExpect(status().isOk());

        // Validate the ProductoCarro in the database
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeUpdate);
        ProductoCarro testProductoCarro = productoCarroList.get(productoCarroList.size() - 1);
        assertThat(testProductoCarro.getIdProducto()).isEqualTo(UPDATED_ID_PRODUCTO);
        assertThat(testProductoCarro.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProductoCarro.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProductoCarro.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoCarro.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    void putNonExistingProductoCarro() throws Exception {
        int databaseSizeBeforeUpdate = productoCarroRepository.findAll().size();
        productoCarro.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoCarroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoCarro.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoCarro))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoCarro in the database
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductoCarro() throws Exception {
        int databaseSizeBeforeUpdate = productoCarroRepository.findAll().size();
        productoCarro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoCarroMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoCarro))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoCarro in the database
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductoCarro() throws Exception {
        int databaseSizeBeforeUpdate = productoCarroRepository.findAll().size();
        productoCarro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoCarroMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoCarro)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoCarro in the database
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoCarroWithPatch() throws Exception {
        // Initialize the database
        productoCarroRepository.saveAndFlush(productoCarro);

        int databaseSizeBeforeUpdate = productoCarroRepository.findAll().size();

        // Update the productoCarro using partial update
        ProductoCarro partialUpdatedProductoCarro = new ProductoCarro();
        partialUpdatedProductoCarro.setId(productoCarro.getId());

        partialUpdatedProductoCarro.nombre(UPDATED_NOMBRE);

        restProductoCarroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoCarro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoCarro))
            )
            .andExpect(status().isOk());

        // Validate the ProductoCarro in the database
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeUpdate);
        ProductoCarro testProductoCarro = productoCarroList.get(productoCarroList.size() - 1);
        assertThat(testProductoCarro.getIdProducto()).isEqualTo(DEFAULT_ID_PRODUCTO);
        assertThat(testProductoCarro.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProductoCarro.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProductoCarro.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProductoCarro.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    void fullUpdateProductoCarroWithPatch() throws Exception {
        // Initialize the database
        productoCarroRepository.saveAndFlush(productoCarro);

        int databaseSizeBeforeUpdate = productoCarroRepository.findAll().size();

        // Update the productoCarro using partial update
        ProductoCarro partialUpdatedProductoCarro = new ProductoCarro();
        partialUpdatedProductoCarro.setId(productoCarro.getId());

        partialUpdatedProductoCarro
            .idProducto(UPDATED_ID_PRODUCTO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .cantidad(UPDATED_CANTIDAD)
            .precio(UPDATED_PRECIO);

        restProductoCarroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoCarro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoCarro))
            )
            .andExpect(status().isOk());

        // Validate the ProductoCarro in the database
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeUpdate);
        ProductoCarro testProductoCarro = productoCarroList.get(productoCarroList.size() - 1);
        assertThat(testProductoCarro.getIdProducto()).isEqualTo(UPDATED_ID_PRODUCTO);
        assertThat(testProductoCarro.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProductoCarro.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProductoCarro.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoCarro.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    void patchNonExistingProductoCarro() throws Exception {
        int databaseSizeBeforeUpdate = productoCarroRepository.findAll().size();
        productoCarro.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoCarroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoCarro.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoCarro))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoCarro in the database
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductoCarro() throws Exception {
        int databaseSizeBeforeUpdate = productoCarroRepository.findAll().size();
        productoCarro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoCarroMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoCarro))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoCarro in the database
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductoCarro() throws Exception {
        int databaseSizeBeforeUpdate = productoCarroRepository.findAll().size();
        productoCarro.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoCarroMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productoCarro))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoCarro in the database
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductoCarro() throws Exception {
        // Initialize the database
        productoCarroRepository.saveAndFlush(productoCarro);

        int databaseSizeBeforeDelete = productoCarroRepository.findAll().size();

        // Delete the productoCarro
        restProductoCarroMockMvc
            .perform(delete(ENTITY_API_URL_ID, productoCarro.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoCarro> productoCarroList = productoCarroRepository.findAll();
        assertThat(productoCarroList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
