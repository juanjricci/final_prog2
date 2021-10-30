package ar.ed.um.programacion2.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ar.ed.um.programacion2.IntegrationTest;
import ar.ed.um.programacion2.domain.CarroCompra;
import ar.ed.um.programacion2.repository.CarroCompraRepository;
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
 * Integration tests for the {@link CarroCompraResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CarroCompraResourceIT {

    private static final String ENTITY_API_URL = "/api/carro-compras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CarroCompraRepository carroCompraRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCarroCompraMockMvc;

    private CarroCompra carroCompra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarroCompra createEntity(EntityManager em) {
        CarroCompra carroCompra = new CarroCompra();
        return carroCompra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CarroCompra createUpdatedEntity(EntityManager em) {
        CarroCompra carroCompra = new CarroCompra();
        return carroCompra;
    }

    @BeforeEach
    public void initTest() {
        carroCompra = createEntity(em);
    }

    @Test
    @Transactional
    void createCarroCompra() throws Exception {
        int databaseSizeBeforeCreate = carroCompraRepository.findAll().size();
        // Create the CarroCompra
        restCarroCompraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carroCompra)))
            .andExpect(status().isCreated());

        // Validate the CarroCompra in the database
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeCreate + 1);
        CarroCompra testCarroCompra = carroCompraList.get(carroCompraList.size() - 1);
    }

    @Test
    @Transactional
    void createCarroCompraWithExistingId() throws Exception {
        // Create the CarroCompra with an existing ID
        carroCompra.setId(1L);

        int databaseSizeBeforeCreate = carroCompraRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCarroCompraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carroCompra)))
            .andExpect(status().isBadRequest());

        // Validate the CarroCompra in the database
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCarroCompras() throws Exception {
        // Initialize the database
        carroCompraRepository.saveAndFlush(carroCompra);

        // Get all the carroCompraList
        restCarroCompraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(carroCompra.getId().intValue())));
    }

    @Test
    @Transactional
    void getCarroCompra() throws Exception {
        // Initialize the database
        carroCompraRepository.saveAndFlush(carroCompra);

        // Get the carroCompra
        restCarroCompraMockMvc
            .perform(get(ENTITY_API_URL_ID, carroCompra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(carroCompra.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCarroCompra() throws Exception {
        // Get the carroCompra
        restCarroCompraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCarroCompra() throws Exception {
        // Initialize the database
        carroCompraRepository.saveAndFlush(carroCompra);

        int databaseSizeBeforeUpdate = carroCompraRepository.findAll().size();

        // Update the carroCompra
        CarroCompra updatedCarroCompra = carroCompraRepository.findById(carroCompra.getId()).get();
        // Disconnect from session so that the updates on updatedCarroCompra are not directly saved in db
        em.detach(updatedCarroCompra);

        restCarroCompraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCarroCompra.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCarroCompra))
            )
            .andExpect(status().isOk());

        // Validate the CarroCompra in the database
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeUpdate);
        CarroCompra testCarroCompra = carroCompraList.get(carroCompraList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCarroCompra() throws Exception {
        int databaseSizeBeforeUpdate = carroCompraRepository.findAll().size();
        carroCompra.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarroCompraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, carroCompra.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carroCompra))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarroCompra in the database
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCarroCompra() throws Exception {
        int databaseSizeBeforeUpdate = carroCompraRepository.findAll().size();
        carroCompra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarroCompraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(carroCompra))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarroCompra in the database
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCarroCompra() throws Exception {
        int databaseSizeBeforeUpdate = carroCompraRepository.findAll().size();
        carroCompra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarroCompraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(carroCompra)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarroCompra in the database
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCarroCompraWithPatch() throws Exception {
        // Initialize the database
        carroCompraRepository.saveAndFlush(carroCompra);

        int databaseSizeBeforeUpdate = carroCompraRepository.findAll().size();

        // Update the carroCompra using partial update
        CarroCompra partialUpdatedCarroCompra = new CarroCompra();
        partialUpdatedCarroCompra.setId(carroCompra.getId());

        restCarroCompraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarroCompra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarroCompra))
            )
            .andExpect(status().isOk());

        // Validate the CarroCompra in the database
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeUpdate);
        CarroCompra testCarroCompra = carroCompraList.get(carroCompraList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateCarroCompraWithPatch() throws Exception {
        // Initialize the database
        carroCompraRepository.saveAndFlush(carroCompra);

        int databaseSizeBeforeUpdate = carroCompraRepository.findAll().size();

        // Update the carroCompra using partial update
        CarroCompra partialUpdatedCarroCompra = new CarroCompra();
        partialUpdatedCarroCompra.setId(carroCompra.getId());

        restCarroCompraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCarroCompra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCarroCompra))
            )
            .andExpect(status().isOk());

        // Validate the CarroCompra in the database
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeUpdate);
        CarroCompra testCarroCompra = carroCompraList.get(carroCompraList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCarroCompra() throws Exception {
        int databaseSizeBeforeUpdate = carroCompraRepository.findAll().size();
        carroCompra.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCarroCompraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, carroCompra.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carroCompra))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarroCompra in the database
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCarroCompra() throws Exception {
        int databaseSizeBeforeUpdate = carroCompraRepository.findAll().size();
        carroCompra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarroCompraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(carroCompra))
            )
            .andExpect(status().isBadRequest());

        // Validate the CarroCompra in the database
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCarroCompra() throws Exception {
        int databaseSizeBeforeUpdate = carroCompraRepository.findAll().size();
        carroCompra.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCarroCompraMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(carroCompra))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CarroCompra in the database
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCarroCompra() throws Exception {
        // Initialize the database
        carroCompraRepository.saveAndFlush(carroCompra);

        int databaseSizeBeforeDelete = carroCompraRepository.findAll().size();

        // Delete the carroCompra
        restCarroCompraMockMvc
            .perform(delete(ENTITY_API_URL_ID, carroCompra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CarroCompra> carroCompraList = carroCompraRepository.findAll();
        assertThat(carroCompraList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
