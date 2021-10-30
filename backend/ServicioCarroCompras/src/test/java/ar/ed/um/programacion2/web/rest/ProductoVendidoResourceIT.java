package ar.ed.um.programacion2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.ed.um.programacion2.IntegrationTest;
import ar.ed.um.programacion2.domain.ProductoVendido;
import ar.ed.um.programacion2.repository.ProductoVendidoRepository;
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
 * Integration tests for the {@link ProductoVendidoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductoVendidoResourceIT {

    private static final Integer DEFAULT_ID_PRODUCTO = 1;
    private static final Integer UPDATED_ID_PRODUCTO = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Long DEFAULT_CANTIDAD = 1L;
    private static final Long UPDATED_CANTIDAD = 2L;

    private static final Long DEFAULT_PRECIO_TOTAL = 1L;
    private static final Long UPDATED_PRECIO_TOTAL = 2L;

    private static final String ENTITY_API_URL = "/api/producto-vendidos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductoVendidoRepository productoVendidoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductoVendidoMockMvc;

    private ProductoVendido productoVendido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoVendido createEntity(EntityManager em) {
        ProductoVendido productoVendido = new ProductoVendido()
            .idProducto(DEFAULT_ID_PRODUCTO)
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .cantidad(DEFAULT_CANTIDAD)
            .precioTotal(DEFAULT_PRECIO_TOTAL);
        return productoVendido;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductoVendido createUpdatedEntity(EntityManager em) {
        ProductoVendido productoVendido = new ProductoVendido()
            .idProducto(UPDATED_ID_PRODUCTO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .cantidad(UPDATED_CANTIDAD)
            .precioTotal(UPDATED_PRECIO_TOTAL);
        return productoVendido;
    }

    @BeforeEach
    public void initTest() {
        productoVendido = createEntity(em);
    }

    @Test
    @Transactional
    void createProductoVendido() throws Exception {
        int databaseSizeBeforeCreate = productoVendidoRepository.findAll().size();
        // Create the ProductoVendido
        restProductoVendidoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoVendido))
            )
            .andExpect(status().isCreated());

        // Validate the ProductoVendido in the database
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeCreate + 1);
        ProductoVendido testProductoVendido = productoVendidoList.get(productoVendidoList.size() - 1);
        assertThat(testProductoVendido.getIdProducto()).isEqualTo(DEFAULT_ID_PRODUCTO);
        assertThat(testProductoVendido.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProductoVendido.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testProductoVendido.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
        assertThat(testProductoVendido.getPrecioTotal()).isEqualTo(DEFAULT_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    void createProductoVendidoWithExistingId() throws Exception {
        // Create the ProductoVendido with an existing ID
        productoVendido.setId(1L);

        int databaseSizeBeforeCreate = productoVendidoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoVendidoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoVendido))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoVendido in the database
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductoVendidos() throws Exception {
        // Initialize the database
        productoVendidoRepository.saveAndFlush(productoVendido);

        // Get all the productoVendidoList
        restProductoVendidoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productoVendido.getId().intValue())))
            .andExpect(jsonPath("$.[*].idProducto").value(hasItem(DEFAULT_ID_PRODUCTO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD.intValue())))
            .andExpect(jsonPath("$.[*].precioTotal").value(hasItem(DEFAULT_PRECIO_TOTAL.intValue())));
    }

    @Test
    @Transactional
    void getProductoVendido() throws Exception {
        // Initialize the database
        productoVendidoRepository.saveAndFlush(productoVendido);

        // Get the productoVendido
        restProductoVendidoMockMvc
            .perform(get(ENTITY_API_URL_ID, productoVendido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productoVendido.getId().intValue()))
            .andExpect(jsonPath("$.idProducto").value(DEFAULT_ID_PRODUCTO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD.intValue()))
            .andExpect(jsonPath("$.precioTotal").value(DEFAULT_PRECIO_TOTAL.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingProductoVendido() throws Exception {
        // Get the productoVendido
        restProductoVendidoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductoVendido() throws Exception {
        // Initialize the database
        productoVendidoRepository.saveAndFlush(productoVendido);

        int databaseSizeBeforeUpdate = productoVendidoRepository.findAll().size();

        // Update the productoVendido
        ProductoVendido updatedProductoVendido = productoVendidoRepository.findById(productoVendido.getId()).get();
        // Disconnect from session so that the updates on updatedProductoVendido are not directly saved in db
        em.detach(updatedProductoVendido);
        updatedProductoVendido
            .idProducto(UPDATED_ID_PRODUCTO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .cantidad(UPDATED_CANTIDAD)
            .precioTotal(UPDATED_PRECIO_TOTAL);

        restProductoVendidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProductoVendido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedProductoVendido))
            )
            .andExpect(status().isOk());

        // Validate the ProductoVendido in the database
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeUpdate);
        ProductoVendido testProductoVendido = productoVendidoList.get(productoVendidoList.size() - 1);
        assertThat(testProductoVendido.getIdProducto()).isEqualTo(UPDATED_ID_PRODUCTO);
        assertThat(testProductoVendido.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProductoVendido.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProductoVendido.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoVendido.getPrecioTotal()).isEqualTo(UPDATED_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    void putNonExistingProductoVendido() throws Exception {
        int databaseSizeBeforeUpdate = productoVendidoRepository.findAll().size();
        productoVendido.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoVendidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productoVendido.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoVendido))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoVendido in the database
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductoVendido() throws Exception {
        int databaseSizeBeforeUpdate = productoVendidoRepository.findAll().size();
        productoVendido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoVendidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productoVendido))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoVendido in the database
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductoVendido() throws Exception {
        int databaseSizeBeforeUpdate = productoVendidoRepository.findAll().size();
        productoVendido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoVendidoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productoVendido))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoVendido in the database
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductoVendidoWithPatch() throws Exception {
        // Initialize the database
        productoVendidoRepository.saveAndFlush(productoVendido);

        int databaseSizeBeforeUpdate = productoVendidoRepository.findAll().size();

        // Update the productoVendido using partial update
        ProductoVendido partialUpdatedProductoVendido = new ProductoVendido();
        partialUpdatedProductoVendido.setId(productoVendido.getId());

        partialUpdatedProductoVendido.descripcion(UPDATED_DESCRIPCION).cantidad(UPDATED_CANTIDAD);

        restProductoVendidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoVendido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoVendido))
            )
            .andExpect(status().isOk());

        // Validate the ProductoVendido in the database
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeUpdate);
        ProductoVendido testProductoVendido = productoVendidoList.get(productoVendidoList.size() - 1);
        assertThat(testProductoVendido.getIdProducto()).isEqualTo(DEFAULT_ID_PRODUCTO);
        assertThat(testProductoVendido.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testProductoVendido.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProductoVendido.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoVendido.getPrecioTotal()).isEqualTo(DEFAULT_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateProductoVendidoWithPatch() throws Exception {
        // Initialize the database
        productoVendidoRepository.saveAndFlush(productoVendido);

        int databaseSizeBeforeUpdate = productoVendidoRepository.findAll().size();

        // Update the productoVendido using partial update
        ProductoVendido partialUpdatedProductoVendido = new ProductoVendido();
        partialUpdatedProductoVendido.setId(productoVendido.getId());

        partialUpdatedProductoVendido
            .idProducto(UPDATED_ID_PRODUCTO)
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .cantidad(UPDATED_CANTIDAD)
            .precioTotal(UPDATED_PRECIO_TOTAL);

        restProductoVendidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductoVendido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductoVendido))
            )
            .andExpect(status().isOk());

        // Validate the ProductoVendido in the database
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeUpdate);
        ProductoVendido testProductoVendido = productoVendidoList.get(productoVendidoList.size() - 1);
        assertThat(testProductoVendido.getIdProducto()).isEqualTo(UPDATED_ID_PRODUCTO);
        assertThat(testProductoVendido.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testProductoVendido.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testProductoVendido.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
        assertThat(testProductoVendido.getPrecioTotal()).isEqualTo(UPDATED_PRECIO_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingProductoVendido() throws Exception {
        int databaseSizeBeforeUpdate = productoVendidoRepository.findAll().size();
        productoVendido.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoVendidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productoVendido.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoVendido))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoVendido in the database
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductoVendido() throws Exception {
        int databaseSizeBeforeUpdate = productoVendidoRepository.findAll().size();
        productoVendido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoVendidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoVendido))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductoVendido in the database
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductoVendido() throws Exception {
        int databaseSizeBeforeUpdate = productoVendidoRepository.findAll().size();
        productoVendido.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductoVendidoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productoVendido))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductoVendido in the database
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductoVendido() throws Exception {
        // Initialize the database
        productoVendidoRepository.saveAndFlush(productoVendido);

        int databaseSizeBeforeDelete = productoVendidoRepository.findAll().size();

        // Delete the productoVendido
        restProductoVendidoMockMvc
            .perform(delete(ENTITY_API_URL_ID, productoVendido.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductoVendido> productoVendidoList = productoVendidoRepository.findAll();
        assertThat(productoVendidoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
