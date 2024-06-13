package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.PrevisionSaludAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.PrevisionSalud;
import cl.controlclub.myapp.domain.enumeration.TipoPrevision;
import cl.controlclub.myapp.repository.PrevisionSaludRepository;
import cl.controlclub.myapp.service.dto.PrevisionSaludDTO;
import cl.controlclub.myapp.service.mapper.PrevisionSaludMapper;
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
 * Integration tests for the {@link PrevisionSaludResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PrevisionSaludResourceIT {

    private static final TipoPrevision DEFAULT_TIPO_PREVISION = TipoPrevision.FONASA;
    private static final TipoPrevision UPDATED_TIPO_PREVISION = TipoPrevision.ISAPRE;

    private static final String ENTITY_API_URL = "/api/prevision-saluds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PrevisionSaludRepository previsionSaludRepository;

    @Autowired
    private PrevisionSaludMapper previsionSaludMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrevisionSaludMockMvc;

    private PrevisionSalud previsionSalud;

    private PrevisionSalud insertedPrevisionSalud;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrevisionSalud createEntity(EntityManager em) {
        PrevisionSalud previsionSalud = new PrevisionSalud().tipoPrevision(DEFAULT_TIPO_PREVISION);
        return previsionSalud;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrevisionSalud createUpdatedEntity(EntityManager em) {
        PrevisionSalud previsionSalud = new PrevisionSalud().tipoPrevision(UPDATED_TIPO_PREVISION);
        return previsionSalud;
    }

    @BeforeEach
    public void initTest() {
        previsionSalud = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPrevisionSalud != null) {
            previsionSaludRepository.delete(insertedPrevisionSalud);
            insertedPrevisionSalud = null;
        }
    }

    @Test
    @Transactional
    void createPrevisionSalud() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PrevisionSalud
        PrevisionSaludDTO previsionSaludDTO = previsionSaludMapper.toDto(previsionSalud);
        var returnedPrevisionSaludDTO = om.readValue(
            restPrevisionSaludMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(previsionSaludDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PrevisionSaludDTO.class
        );

        // Validate the PrevisionSalud in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPrevisionSalud = previsionSaludMapper.toEntity(returnedPrevisionSaludDTO);
        assertPrevisionSaludUpdatableFieldsEquals(returnedPrevisionSalud, getPersistedPrevisionSalud(returnedPrevisionSalud));

        insertedPrevisionSalud = returnedPrevisionSalud;
    }

    @Test
    @Transactional
    void createPrevisionSaludWithExistingId() throws Exception {
        // Create the PrevisionSalud with an existing ID
        previsionSalud.setId(1L);
        PrevisionSaludDTO previsionSaludDTO = previsionSaludMapper.toDto(previsionSalud);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrevisionSaludMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(previsionSaludDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrevisionSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoPrevisionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        previsionSalud.setTipoPrevision(null);

        // Create the PrevisionSalud, which fails.
        PrevisionSaludDTO previsionSaludDTO = previsionSaludMapper.toDto(previsionSalud);

        restPrevisionSaludMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(previsionSaludDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPrevisionSaluds() throws Exception {
        // Initialize the database
        insertedPrevisionSalud = previsionSaludRepository.saveAndFlush(previsionSalud);

        // Get all the previsionSaludList
        restPrevisionSaludMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(previsionSalud.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoPrevision").value(hasItem(DEFAULT_TIPO_PREVISION.toString())));
    }

    @Test
    @Transactional
    void getPrevisionSalud() throws Exception {
        // Initialize the database
        insertedPrevisionSalud = previsionSaludRepository.saveAndFlush(previsionSalud);

        // Get the previsionSalud
        restPrevisionSaludMockMvc
            .perform(get(ENTITY_API_URL_ID, previsionSalud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(previsionSalud.getId().intValue()))
            .andExpect(jsonPath("$.tipoPrevision").value(DEFAULT_TIPO_PREVISION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPrevisionSalud() throws Exception {
        // Get the previsionSalud
        restPrevisionSaludMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrevisionSalud() throws Exception {
        // Initialize the database
        insertedPrevisionSalud = previsionSaludRepository.saveAndFlush(previsionSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the previsionSalud
        PrevisionSalud updatedPrevisionSalud = previsionSaludRepository.findById(previsionSalud.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPrevisionSalud are not directly saved in db
        em.detach(updatedPrevisionSalud);
        updatedPrevisionSalud.tipoPrevision(UPDATED_TIPO_PREVISION);
        PrevisionSaludDTO previsionSaludDTO = previsionSaludMapper.toDto(updatedPrevisionSalud);

        restPrevisionSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, previsionSaludDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(previsionSaludDTO))
            )
            .andExpect(status().isOk());

        // Validate the PrevisionSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPrevisionSaludToMatchAllProperties(updatedPrevisionSalud);
    }

    @Test
    @Transactional
    void putNonExistingPrevisionSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        previsionSalud.setId(longCount.incrementAndGet());

        // Create the PrevisionSalud
        PrevisionSaludDTO previsionSaludDTO = previsionSaludMapper.toDto(previsionSalud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrevisionSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, previsionSaludDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(previsionSaludDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrevisionSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrevisionSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        previsionSalud.setId(longCount.incrementAndGet());

        // Create the PrevisionSalud
        PrevisionSaludDTO previsionSaludDTO = previsionSaludMapper.toDto(previsionSalud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrevisionSaludMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(previsionSaludDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrevisionSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrevisionSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        previsionSalud.setId(longCount.incrementAndGet());

        // Create the PrevisionSalud
        PrevisionSaludDTO previsionSaludDTO = previsionSaludMapper.toDto(previsionSalud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrevisionSaludMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(previsionSaludDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrevisionSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePrevisionSaludWithPatch() throws Exception {
        // Initialize the database
        insertedPrevisionSalud = previsionSaludRepository.saveAndFlush(previsionSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the previsionSalud using partial update
        PrevisionSalud partialUpdatedPrevisionSalud = new PrevisionSalud();
        partialUpdatedPrevisionSalud.setId(previsionSalud.getId());

        partialUpdatedPrevisionSalud.tipoPrevision(UPDATED_TIPO_PREVISION);

        restPrevisionSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrevisionSalud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrevisionSalud))
            )
            .andExpect(status().isOk());

        // Validate the PrevisionSalud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrevisionSaludUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPrevisionSalud, previsionSalud),
            getPersistedPrevisionSalud(previsionSalud)
        );
    }

    @Test
    @Transactional
    void fullUpdatePrevisionSaludWithPatch() throws Exception {
        // Initialize the database
        insertedPrevisionSalud = previsionSaludRepository.saveAndFlush(previsionSalud);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the previsionSalud using partial update
        PrevisionSalud partialUpdatedPrevisionSalud = new PrevisionSalud();
        partialUpdatedPrevisionSalud.setId(previsionSalud.getId());

        partialUpdatedPrevisionSalud.tipoPrevision(UPDATED_TIPO_PREVISION);

        restPrevisionSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrevisionSalud.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrevisionSalud))
            )
            .andExpect(status().isOk());

        // Validate the PrevisionSalud in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPrevisionSaludUpdatableFieldsEquals(partialUpdatedPrevisionSalud, getPersistedPrevisionSalud(partialUpdatedPrevisionSalud));
    }

    @Test
    @Transactional
    void patchNonExistingPrevisionSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        previsionSalud.setId(longCount.incrementAndGet());

        // Create the PrevisionSalud
        PrevisionSaludDTO previsionSaludDTO = previsionSaludMapper.toDto(previsionSalud);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrevisionSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, previsionSaludDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(previsionSaludDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrevisionSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrevisionSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        previsionSalud.setId(longCount.incrementAndGet());

        // Create the PrevisionSalud
        PrevisionSaludDTO previsionSaludDTO = previsionSaludMapper.toDto(previsionSalud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrevisionSaludMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(previsionSaludDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PrevisionSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrevisionSalud() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        previsionSalud.setId(longCount.incrementAndGet());

        // Create the PrevisionSalud
        PrevisionSaludDTO previsionSaludDTO = previsionSaludMapper.toDto(previsionSalud);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPrevisionSaludMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(previsionSaludDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PrevisionSalud in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrevisionSalud() throws Exception {
        // Initialize the database
        insertedPrevisionSalud = previsionSaludRepository.saveAndFlush(previsionSalud);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the previsionSalud
        restPrevisionSaludMockMvc
            .perform(delete(ENTITY_API_URL_ID, previsionSalud.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return previsionSaludRepository.count();
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

    protected PrevisionSalud getPersistedPrevisionSalud(PrevisionSalud previsionSalud) {
        return previsionSaludRepository.findById(previsionSalud.getId()).orElseThrow();
    }

    protected void assertPersistedPrevisionSaludToMatchAllProperties(PrevisionSalud expectedPrevisionSalud) {
        assertPrevisionSaludAllPropertiesEquals(expectedPrevisionSalud, getPersistedPrevisionSalud(expectedPrevisionSalud));
    }

    protected void assertPersistedPrevisionSaludToMatchUpdatableProperties(PrevisionSalud expectedPrevisionSalud) {
        assertPrevisionSaludAllUpdatablePropertiesEquals(expectedPrevisionSalud, getPersistedPrevisionSalud(expectedPrevisionSalud));
    }
}
