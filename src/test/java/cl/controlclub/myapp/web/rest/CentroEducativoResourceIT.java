package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.CentroEducativoAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.CentroEducativo;
import cl.controlclub.myapp.repository.CentroEducativoRepository;
import cl.controlclub.myapp.service.dto.CentroEducativoDTO;
import cl.controlclub.myapp.service.mapper.CentroEducativoMapper;
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
 * Integration tests for the {@link CentroEducativoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CentroEducativoResourceIT {

    private static final String DEFAULT_CENTRO_EDUCATIVO = "AAAAAAAAAA";
    private static final String UPDATED_CENTRO_EDUCATIVO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/centro-educativos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CentroEducativoRepository centroEducativoRepository;

    @Autowired
    private CentroEducativoMapper centroEducativoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCentroEducativoMockMvc;

    private CentroEducativo centroEducativo;

    private CentroEducativo insertedCentroEducativo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroEducativo createEntity(EntityManager em) {
        CentroEducativo centroEducativo = new CentroEducativo().centroEducativo(DEFAULT_CENTRO_EDUCATIVO);
        return centroEducativo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroEducativo createUpdatedEntity(EntityManager em) {
        CentroEducativo centroEducativo = new CentroEducativo().centroEducativo(UPDATED_CENTRO_EDUCATIVO);
        return centroEducativo;
    }

    @BeforeEach
    public void initTest() {
        centroEducativo = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCentroEducativo != null) {
            centroEducativoRepository.delete(insertedCentroEducativo);
            insertedCentroEducativo = null;
        }
    }

    @Test
    @Transactional
    void createCentroEducativo() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CentroEducativo
        CentroEducativoDTO centroEducativoDTO = centroEducativoMapper.toDto(centroEducativo);
        var returnedCentroEducativoDTO = om.readValue(
            restCentroEducativoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroEducativoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CentroEducativoDTO.class
        );

        // Validate the CentroEducativo in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCentroEducativo = centroEducativoMapper.toEntity(returnedCentroEducativoDTO);
        assertCentroEducativoUpdatableFieldsEquals(returnedCentroEducativo, getPersistedCentroEducativo(returnedCentroEducativo));

        insertedCentroEducativo = returnedCentroEducativo;
    }

    @Test
    @Transactional
    void createCentroEducativoWithExistingId() throws Exception {
        // Create the CentroEducativo with an existing ID
        centroEducativo.setId(1L);
        CentroEducativoDTO centroEducativoDTO = centroEducativoMapper.toDto(centroEducativo);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentroEducativoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroEducativoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CentroEducativo in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCentroEducativoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        centroEducativo.setCentroEducativo(null);

        // Create the CentroEducativo, which fails.
        CentroEducativoDTO centroEducativoDTO = centroEducativoMapper.toDto(centroEducativo);

        restCentroEducativoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroEducativoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCentroEducativos() throws Exception {
        // Initialize the database
        insertedCentroEducativo = centroEducativoRepository.saveAndFlush(centroEducativo);

        // Get all the centroEducativoList
        restCentroEducativoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centroEducativo.getId().intValue())))
            .andExpect(jsonPath("$.[*].centroEducativo").value(hasItem(DEFAULT_CENTRO_EDUCATIVO)));
    }

    @Test
    @Transactional
    void getCentroEducativo() throws Exception {
        // Initialize the database
        insertedCentroEducativo = centroEducativoRepository.saveAndFlush(centroEducativo);

        // Get the centroEducativo
        restCentroEducativoMockMvc
            .perform(get(ENTITY_API_URL_ID, centroEducativo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centroEducativo.getId().intValue()))
            .andExpect(jsonPath("$.centroEducativo").value(DEFAULT_CENTRO_EDUCATIVO));
    }

    @Test
    @Transactional
    void getNonExistingCentroEducativo() throws Exception {
        // Get the centroEducativo
        restCentroEducativoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCentroEducativo() throws Exception {
        // Initialize the database
        insertedCentroEducativo = centroEducativoRepository.saveAndFlush(centroEducativo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroEducativo
        CentroEducativo updatedCentroEducativo = centroEducativoRepository.findById(centroEducativo.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCentroEducativo are not directly saved in db
        em.detach(updatedCentroEducativo);
        updatedCentroEducativo.centroEducativo(UPDATED_CENTRO_EDUCATIVO);
        CentroEducativoDTO centroEducativoDTO = centroEducativoMapper.toDto(updatedCentroEducativo);

        restCentroEducativoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centroEducativoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroEducativoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CentroEducativo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCentroEducativoToMatchAllProperties(updatedCentroEducativo);
    }

    @Test
    @Transactional
    void putNonExistingCentroEducativo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroEducativo.setId(longCount.incrementAndGet());

        // Create the CentroEducativo
        CentroEducativoDTO centroEducativoDTO = centroEducativoMapper.toDto(centroEducativo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroEducativoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centroEducativoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroEducativoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroEducativo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCentroEducativo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroEducativo.setId(longCount.incrementAndGet());

        // Create the CentroEducativo
        CentroEducativoDTO centroEducativoDTO = centroEducativoMapper.toDto(centroEducativo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroEducativoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroEducativoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroEducativo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCentroEducativo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroEducativo.setId(longCount.incrementAndGet());

        // Create the CentroEducativo
        CentroEducativoDTO centroEducativoDTO = centroEducativoMapper.toDto(centroEducativo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroEducativoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroEducativoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroEducativo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCentroEducativoWithPatch() throws Exception {
        // Initialize the database
        insertedCentroEducativo = centroEducativoRepository.saveAndFlush(centroEducativo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroEducativo using partial update
        CentroEducativo partialUpdatedCentroEducativo = new CentroEducativo();
        partialUpdatedCentroEducativo.setId(centroEducativo.getId());

        partialUpdatedCentroEducativo.centroEducativo(UPDATED_CENTRO_EDUCATIVO);

        restCentroEducativoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroEducativo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentroEducativo))
            )
            .andExpect(status().isOk());

        // Validate the CentroEducativo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentroEducativoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCentroEducativo, centroEducativo),
            getPersistedCentroEducativo(centroEducativo)
        );
    }

    @Test
    @Transactional
    void fullUpdateCentroEducativoWithPatch() throws Exception {
        // Initialize the database
        insertedCentroEducativo = centroEducativoRepository.saveAndFlush(centroEducativo);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroEducativo using partial update
        CentroEducativo partialUpdatedCentroEducativo = new CentroEducativo();
        partialUpdatedCentroEducativo.setId(centroEducativo.getId());

        partialUpdatedCentroEducativo.centroEducativo(UPDATED_CENTRO_EDUCATIVO);

        restCentroEducativoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroEducativo.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentroEducativo))
            )
            .andExpect(status().isOk());

        // Validate the CentroEducativo in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentroEducativoUpdatableFieldsEquals(
            partialUpdatedCentroEducativo,
            getPersistedCentroEducativo(partialUpdatedCentroEducativo)
        );
    }

    @Test
    @Transactional
    void patchNonExistingCentroEducativo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroEducativo.setId(longCount.incrementAndGet());

        // Create the CentroEducativo
        CentroEducativoDTO centroEducativoDTO = centroEducativoMapper.toDto(centroEducativo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroEducativoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centroEducativoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centroEducativoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroEducativo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCentroEducativo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroEducativo.setId(longCount.incrementAndGet());

        // Create the CentroEducativo
        CentroEducativoDTO centroEducativoDTO = centroEducativoMapper.toDto(centroEducativo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroEducativoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centroEducativoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroEducativo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCentroEducativo() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroEducativo.setId(longCount.incrementAndGet());

        // Create the CentroEducativo
        CentroEducativoDTO centroEducativoDTO = centroEducativoMapper.toDto(centroEducativo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroEducativoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(centroEducativoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroEducativo in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCentroEducativo() throws Exception {
        // Initialize the database
        insertedCentroEducativo = centroEducativoRepository.saveAndFlush(centroEducativo);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the centroEducativo
        restCentroEducativoMockMvc
            .perform(delete(ENTITY_API_URL_ID, centroEducativo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return centroEducativoRepository.count();
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

    protected CentroEducativo getPersistedCentroEducativo(CentroEducativo centroEducativo) {
        return centroEducativoRepository.findById(centroEducativo.getId()).orElseThrow();
    }

    protected void assertPersistedCentroEducativoToMatchAllProperties(CentroEducativo expectedCentroEducativo) {
        assertCentroEducativoAllPropertiesEquals(expectedCentroEducativo, getPersistedCentroEducativo(expectedCentroEducativo));
    }

    protected void assertPersistedCentroEducativoToMatchUpdatableProperties(CentroEducativo expectedCentroEducativo) {
        assertCentroEducativoAllUpdatablePropertiesEquals(expectedCentroEducativo, getPersistedCentroEducativo(expectedCentroEducativo));
    }
}
