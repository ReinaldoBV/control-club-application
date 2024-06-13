package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.ComunaAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Comuna;
import cl.controlclub.myapp.repository.ComunaRepository;
import cl.controlclub.myapp.service.dto.ComunaDTO;
import cl.controlclub.myapp.service.mapper.ComunaMapper;
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
 * Integration tests for the {@link ComunaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ComunaResourceIT {

    private static final String DEFAULT_COMUNA = "AAAAAAAAAA";
    private static final String UPDATED_COMUNA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/comunas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private ComunaMapper comunaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComunaMockMvc;

    private Comuna comuna;

    private Comuna insertedComuna;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comuna createEntity(EntityManager em) {
        Comuna comuna = new Comuna().comuna(DEFAULT_COMUNA);
        return comuna;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comuna createUpdatedEntity(EntityManager em) {
        Comuna comuna = new Comuna().comuna(UPDATED_COMUNA);
        return comuna;
    }

    @BeforeEach
    public void initTest() {
        comuna = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedComuna != null) {
            comunaRepository.delete(insertedComuna);
            insertedComuna = null;
        }
    }

    @Test
    @Transactional
    void createComuna() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Comuna
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);
        var returnedComunaDTO = om.readValue(
            restComunaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(comunaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ComunaDTO.class
        );

        // Validate the Comuna in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedComuna = comunaMapper.toEntity(returnedComunaDTO);
        assertComunaUpdatableFieldsEquals(returnedComuna, getPersistedComuna(returnedComuna));

        insertedComuna = returnedComuna;
    }

    @Test
    @Transactional
    void createComunaWithExistingId() throws Exception {
        // Create the Comuna with an existing ID
        comuna.setId(1L);
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComunaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(comunaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comuna in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkComunaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        comuna.setComuna(null);

        // Create the Comuna, which fails.
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        restComunaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(comunaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllComunas() throws Exception {
        // Initialize the database
        insertedComuna = comunaRepository.saveAndFlush(comuna);

        // Get all the comunaList
        restComunaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comuna.getId().intValue())))
            .andExpect(jsonPath("$.[*].comuna").value(hasItem(DEFAULT_COMUNA)));
    }

    @Test
    @Transactional
    void getComuna() throws Exception {
        // Initialize the database
        insertedComuna = comunaRepository.saveAndFlush(comuna);

        // Get the comuna
        restComunaMockMvc
            .perform(get(ENTITY_API_URL_ID, comuna.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comuna.getId().intValue()))
            .andExpect(jsonPath("$.comuna").value(DEFAULT_COMUNA));
    }

    @Test
    @Transactional
    void getNonExistingComuna() throws Exception {
        // Get the comuna
        restComunaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingComuna() throws Exception {
        // Initialize the database
        insertedComuna = comunaRepository.saveAndFlush(comuna);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the comuna
        Comuna updatedComuna = comunaRepository.findById(comuna.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedComuna are not directly saved in db
        em.detach(updatedComuna);
        updatedComuna.comuna(UPDATED_COMUNA);
        ComunaDTO comunaDTO = comunaMapper.toDto(updatedComuna);

        restComunaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comunaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(comunaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Comuna in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedComunaToMatchAllProperties(updatedComuna);
    }

    @Test
    @Transactional
    void putNonExistingComuna() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comuna.setId(longCount.incrementAndGet());

        // Create the Comuna
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComunaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comunaDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(comunaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comuna in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchComuna() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comuna.setId(longCount.incrementAndGet());

        // Create the Comuna
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComunaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(comunaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comuna in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComuna() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comuna.setId(longCount.incrementAndGet());

        // Create the Comuna
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComunaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(comunaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comuna in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateComunaWithPatch() throws Exception {
        // Initialize the database
        insertedComuna = comunaRepository.saveAndFlush(comuna);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the comuna using partial update
        Comuna partialUpdatedComuna = new Comuna();
        partialUpdatedComuna.setId(comuna.getId());

        partialUpdatedComuna.comuna(UPDATED_COMUNA);

        restComunaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComuna.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedComuna))
            )
            .andExpect(status().isOk());

        // Validate the Comuna in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertComunaUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedComuna, comuna), getPersistedComuna(comuna));
    }

    @Test
    @Transactional
    void fullUpdateComunaWithPatch() throws Exception {
        // Initialize the database
        insertedComuna = comunaRepository.saveAndFlush(comuna);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the comuna using partial update
        Comuna partialUpdatedComuna = new Comuna();
        partialUpdatedComuna.setId(comuna.getId());

        partialUpdatedComuna.comuna(UPDATED_COMUNA);

        restComunaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComuna.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedComuna))
            )
            .andExpect(status().isOk());

        // Validate the Comuna in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertComunaUpdatableFieldsEquals(partialUpdatedComuna, getPersistedComuna(partialUpdatedComuna));
    }

    @Test
    @Transactional
    void patchNonExistingComuna() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comuna.setId(longCount.incrementAndGet());

        // Create the Comuna
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComunaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, comunaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(comunaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comuna in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComuna() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comuna.setId(longCount.incrementAndGet());

        // Create the Comuna
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComunaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(comunaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comuna in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComuna() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        comuna.setId(longCount.incrementAndGet());

        // Create the Comuna
        ComunaDTO comunaDTO = comunaMapper.toDto(comuna);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComunaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(comunaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comuna in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteComuna() throws Exception {
        // Initialize the database
        insertedComuna = comunaRepository.saveAndFlush(comuna);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the comuna
        restComunaMockMvc
            .perform(delete(ENTITY_API_URL_ID, comuna.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return comunaRepository.count();
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

    protected Comuna getPersistedComuna(Comuna comuna) {
        return comunaRepository.findById(comuna.getId()).orElseThrow();
    }

    protected void assertPersistedComunaToMatchAllProperties(Comuna expectedComuna) {
        assertComunaAllPropertiesEquals(expectedComuna, getPersistedComuna(expectedComuna));
    }

    protected void assertPersistedComunaToMatchUpdatableProperties(Comuna expectedComuna) {
        assertComunaAllUpdatablePropertiesEquals(expectedComuna, getPersistedComuna(expectedComuna));
    }
}
