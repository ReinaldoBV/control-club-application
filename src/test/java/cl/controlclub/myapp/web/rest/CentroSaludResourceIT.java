package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.CentroSaludAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.CentroSalud;
import cl.controlclub.myapp.repository.CentroSaludRepository;
import cl.controlclub.myapp.service.dto.CentroSaludDTO;
import cl.controlclub.myapp.service.mapper.CentroSaludMapper;
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
 * Integration tests for the {@link CentroSaludResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CentroSaludResourceIT {

    private static final String DEFAULT_CENTRO_SALUD = "AAAAAAAAAA";
    private static final String UPDATED_CENTRO_SALUD = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/centro-saluds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CentroSaludRepository centroSaludRepository;

    @Autowired
    private CentroSaludMapper centroSaludMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCentroSaludMockMvc;

    private CentroSalud centroSalud;

    private CentroSalud insertedCentroSalud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroSalud createEntity(EntityManager em) {
        CentroSalud centroSalud = new CentroSalud().centroSalud(DEFAULT_CENTRO_SALUD);
        return centroSalud;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CentroSalud createUpdatedEntity(EntityManager em) {
        CentroSalud centroSalud = new CentroSalud().centroSalud(UPDATED_CENTRO_SALUD);
        return centroSalud;
    }

    @BeforeEach
    public void initTest() {
        centroSalud = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCentroSalud != null) {
            centroSaludRepository.delete(insertedCentroSalud);
            insertedCentroSalud = null;
        }
    }

    @Test
    @Transactional
    void createCentroSalud() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CentroSalud
        CentroSaludDTO centroSaludDTO = centroSaludMapper.toDto(centroSalud);
        var returnedCentroSaludDTO = om.readValue(
            restCentroSaludMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroSaludDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CentroSaludDTO.class
        );

        // Validate the CentroSalud in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCentroSalud = centroSaludMapper.toEntity(returnedCentroSaludDTO);
        assertCentroSaludUpdatableFieldsEquals(returnedCentroSalud, getPersistedCentroSalud(returnedCentroSalud));

        insertedCentroSalud = returnedCentroSalud;
    }

    @Test
    @Transactional
    void createCentroSaludWithExistingId() throws Exception {
        // Create the CentroSalud with an existing ID
        centroSalud.setId(1L);
        CentroSaludDTO centroSaludDTO = centroSaludMapper.toDto(centroSalud);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCentroSaludMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroSaludDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCentroSaludIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        centroSalud.setCentroSalud(null);

        // Create the CentroSalud, which fails.
        CentroSaludDTO centroSaludDTO = centroSaludMapper.toDto(centroSalud);

        restCentroSaludMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroSaludDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCentroSaluds() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        // Get all the centroSaludList
        restCentroSaludMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(centroSalud.getId().intValue())))
            .andExpect(jsonPath("$.[*].centroSalud").value(hasItem(DEFAULT_CENTRO_SALUD)));
    }

    @Test
    @Transactional
    void getCentroSalud() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        // Get the centroSalud
        restCentroSaludMockMvc
            .perform(get(ENTITY_API_URL_ID, centroSalud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(centroSalud.getId().intValue()))
            .andExpect(jsonPath("$.centroSalud").value(DEFAULT_CENTRO_SALUD));
    }

    @Test
    @Transactional
    void getNonExistingCentroSalud() throws Exception {
        // Get the centroSalud
        restCentroSaludMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCentroSalud() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroSalud
        CentroSalud updatedCentroSalud = centroSaludRepository.findById(centroSalud.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCentroSalud are not directly saved in db
        em.detach(updatedCentroSalud);
        updatedCentroSalud.centroSalud(UPDATED_CENTRO_SALUD);
        CentroSaludDTO centroSaludDTO = centroSaludMapper.toDto(updatedCentroSalud);

        restCentroSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centroSaludDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroSaludDTO))
            )
            .andExpect(status().isOk());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCentroSaludToMatchAllProperties(updatedCentroSalud);
    }

    @Test
    @Transactional
    void putNonExistingCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // Create the CentroSalud
        CentroSaludDTO centroSaludDTO = centroSaludMapper.toDto(centroSalud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, centroSaludDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroSaludDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // Create the CentroSalud
        CentroSaludDTO centroSaludDTO = centroSaludMapper.toDto(centroSalud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(centroSaludDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // Create the CentroSalud
        CentroSaludDTO centroSaludDTO = centroSaludMapper.toDto(centroSalud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(centroSaludDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCentroSaludWithPatch() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroSalud using partial update
        CentroSalud partialUpdatedCentroSalud = new CentroSalud();
        partialUpdatedCentroSalud.setId(centroSalud.getId());

        restCentroSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroSalud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentroSalud))
            )
            .andExpect(status().isOk());

        // Validate the CentroSalud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentroSaludUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCentroSalud, centroSalud),
            getPersistedCentroSalud(centroSalud)
        );
    }

    @Test
    @Transactional
    void fullUpdateCentroSaludWithPatch() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the centroSalud using partial update
        CentroSalud partialUpdatedCentroSalud = new CentroSalud();
        partialUpdatedCentroSalud.setId(centroSalud.getId());

        partialUpdatedCentroSalud.centroSalud(UPDATED_CENTRO_SALUD);

        restCentroSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCentroSalud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCentroSalud))
            )
            .andExpect(status().isOk());

        // Validate the CentroSalud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCentroSaludUpdatableFieldsEquals(partialUpdatedCentroSalud, getPersistedCentroSalud(partialUpdatedCentroSalud));
    }

    @Test
    @Transactional
    void patchNonExistingCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // Create the CentroSalud
        CentroSaludDTO centroSaludDTO = centroSaludMapper.toDto(centroSalud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, centroSaludDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centroSaludDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // Create the CentroSalud
        CentroSaludDTO centroSaludDTO = centroSaludMapper.toDto(centroSalud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(centroSaludDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCentroSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        centroSalud.setId(longCount.incrementAndGet());

        // Create the CentroSalud
        CentroSaludDTO centroSaludDTO = centroSaludMapper.toDto(centroSalud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCentroSaludMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(centroSaludDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CentroSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCentroSalud() throws Exception {
        // Initialize the database
        insertedCentroSalud = centroSaludRepository.saveAndFlush(centroSalud);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the centroSalud
        restCentroSaludMockMvc
            .perform(delete(ENTITY_API_URL_ID, centroSalud.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return centroSaludRepository.count();
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

    protected CentroSalud getPersistedCentroSalud(CentroSalud centroSalud) {
        return centroSaludRepository.findById(centroSalud.getId()).orElseThrow();
    }

    protected void assertPersistedCentroSaludToMatchAllProperties(CentroSalud expectedCentroSalud) {
        assertCentroSaludAllPropertiesEquals(expectedCentroSalud, getPersistedCentroSalud(expectedCentroSalud));
    }

    protected void assertPersistedCentroSaludToMatchUpdatableProperties(CentroSalud expectedCentroSalud) {
        assertCentroSaludAllUpdatablePropertiesEquals(expectedCentroSalud, getPersistedCentroSalud(expectedCentroSalud));
    }
}
