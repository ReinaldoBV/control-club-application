package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.BienesAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Bienes;
import cl.controlclub.myapp.repository.BienesRepository;
import cl.controlclub.myapp.service.dto.BienesDTO;
import cl.controlclub.myapp.service.mapper.BienesMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BienesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BienesResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/bienes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private BienesRepository bienesRepository;

    @Autowired
    private BienesMapper bienesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBienesMockMvc;

    private Bienes bienes;

    private Bienes insertedBienes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bienes createEntity(EntityManager em) {
        Bienes bienes = new Bienes().descripcion(DEFAULT_DESCRIPCION).cantidad(DEFAULT_CANTIDAD).estado(DEFAULT_ESTADO);
        return bienes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bienes createUpdatedEntity(EntityManager em) {
        Bienes bienes = new Bienes().descripcion(UPDATED_DESCRIPCION).cantidad(UPDATED_CANTIDAD).estado(UPDATED_ESTADO);
        return bienes;
    }

    @BeforeEach
    public void initTest() {
        bienes = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedBienes != null) {
            bienesRepository.delete(insertedBienes);
            insertedBienes = null;
        }
    }

    @Test
    @Transactional
    void createBienes() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Bienes
        BienesDTO bienesDTO = bienesMapper.toDto(bienes);
        var returnedBienesDTO = om.readValue(
            restBienesMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bienesDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            BienesDTO.class
        );

        // Validate the Bienes in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedBienes = bienesMapper.toEntity(returnedBienesDTO);
        assertBienesUpdatableFieldsEquals(returnedBienes, getPersistedBienes(returnedBienes));

        insertedBienes = returnedBienes;
    }

    @Test
    @Transactional
    void createBienesWithExistingId() throws Exception {
        // Create the Bienes with an existing ID
        bienes.setId(1L);
        BienesDTO bienesDTO = bienesMapper.toDto(bienes);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBienesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bienesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Bienes in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bienes.setDescripcion(null);

        // Create the Bienes, which fails.
        BienesDTO bienesDTO = bienesMapper.toDto(bienes);

        restBienesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bienesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCantidadIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bienes.setCantidad(null);

        // Create the Bienes, which fails.
        BienesDTO bienesDTO = bienesMapper.toDto(bienes);

        restBienesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bienesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        bienes.setEstado(null);

        // Create the Bienes, which fails.
        BienesDTO bienesDTO = bienesMapper.toDto(bienes);

        restBienesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bienesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBienes() throws Exception {
        // Initialize the database
        insertedBienes = bienesRepository.saveAndFlush(bienes);

        // Get all the bienesList
        restBienesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bienes.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }

    @Test
    @Transactional
    void getBienes() throws Exception {
        // Initialize the database
        insertedBienes = bienesRepository.saveAndFlush(bienes);

        // Get the bienes
        restBienesMockMvc
            .perform(get(ENTITY_API_URL_ID, bienes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bienes.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }

    @Test
    @Transactional
    void getNonExistingBienes() throws Exception {
        // Get the bienes
        restBienesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBienes() throws Exception {
        // Initialize the database
        insertedBienes = bienesRepository.saveAndFlush(bienes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bienes
        Bienes updatedBienes = bienesRepository.findById(bienes.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBienes are not directly saved in db
        em.detach(updatedBienes);
        updatedBienes.descripcion(UPDATED_DESCRIPCION).cantidad(UPDATED_CANTIDAD).estado(UPDATED_ESTADO);
        BienesDTO bienesDTO = bienesMapper.toDto(updatedBienes);

        restBienesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bienesDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bienesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Bienes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedBienesToMatchAllProperties(updatedBienes);
    }

    @Test
    @Transactional
    void putNonExistingBienes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bienes.setId(longCount.incrementAndGet());

        // Create the Bienes
        BienesDTO bienesDTO = bienesMapper.toDto(bienes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBienesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bienesDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bienesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bienes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBienes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bienes.setId(longCount.incrementAndGet());

        // Create the Bienes
        BienesDTO bienesDTO = bienesMapper.toDto(bienes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBienesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(bienesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bienes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBienes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bienes.setId(longCount.incrementAndGet());

        // Create the Bienes
        BienesDTO bienesDTO = bienesMapper.toDto(bienes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBienesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(bienesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bienes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBienesWithPatch() throws Exception {
        // Initialize the database
        insertedBienes = bienesRepository.saveAndFlush(bienes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bienes using partial update
        Bienes partialUpdatedBienes = new Bienes();
        partialUpdatedBienes.setId(bienes.getId());

        partialUpdatedBienes.cantidad(UPDATED_CANTIDAD).estado(UPDATED_ESTADO);

        restBienesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBienes.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBienes))
            )
            .andExpect(status().isOk());

        // Validate the Bienes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBienesUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedBienes, bienes), getPersistedBienes(bienes));
    }

    @Test
    @Transactional
    void fullUpdateBienesWithPatch() throws Exception {
        // Initialize the database
        insertedBienes = bienesRepository.saveAndFlush(bienes);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the bienes using partial update
        Bienes partialUpdatedBienes = new Bienes();
        partialUpdatedBienes.setId(bienes.getId());

        partialUpdatedBienes.descripcion(UPDATED_DESCRIPCION).cantidad(UPDATED_CANTIDAD).estado(UPDATED_ESTADO);

        restBienesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBienes.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedBienes))
            )
            .andExpect(status().isOk());

        // Validate the Bienes in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertBienesUpdatableFieldsEquals(partialUpdatedBienes, getPersistedBienes(partialUpdatedBienes));
    }

    @Test
    @Transactional
    void patchNonExistingBienes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bienes.setId(longCount.incrementAndGet());

        // Create the Bienes
        BienesDTO bienesDTO = bienesMapper.toDto(bienes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBienesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bienesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bienesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bienes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBienes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bienes.setId(longCount.incrementAndGet());

        // Create the Bienes
        BienesDTO bienesDTO = bienesMapper.toDto(bienes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBienesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(bienesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bienes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBienes() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        bienes.setId(longCount.incrementAndGet());

        // Create the Bienes
        BienesDTO bienesDTO = bienesMapper.toDto(bienes);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBienesMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(bienesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bienes in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBienes() throws Exception {
        // Initialize the database
        insertedBienes = bienesRepository.saveAndFlush(bienes);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the bienes
        restBienesMockMvc
            .perform(delete(ENTITY_API_URL_ID, bienes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return bienesRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Bienes getPersistedBienes(Bienes bienes) {
        return bienesRepository.findById(bienes.getId()).orElseThrow();
    }

    protected void assertPersistedBienesToMatchAllProperties(Bienes expectedBienes) {
        assertBienesAllPropertiesEquals(expectedBienes, getPersistedBienes(expectedBienes));
    }

    protected void assertPersistedBienesToMatchUpdatableProperties(Bienes expectedBienes) {
        assertBienesAllUpdatablePropertiesEquals(expectedBienes, getPersistedBienes(expectedBienes));
    }
}
