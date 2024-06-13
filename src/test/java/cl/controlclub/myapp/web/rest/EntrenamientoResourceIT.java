package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.EntrenamientoAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Entrenamiento;
import cl.controlclub.myapp.repository.EntrenamientoRepository;
import cl.controlclub.myapp.service.dto.EntrenamientoDTO;
import cl.controlclub.myapp.service.mapper.EntrenamientoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link EntrenamientoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntrenamientoResourceIT {

    private static final LocalDate DEFAULT_FECHA_HORA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_HORA = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_DURACION = 1;
    private static final Integer UPDATED_DURACION = 2;

    private static final String ENTITY_API_URL = "/api/entrenamientos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EntrenamientoRepository entrenamientoRepository;

    @Autowired
    private EntrenamientoMapper entrenamientoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntrenamientoMockMvc;

    private Entrenamiento entrenamiento;

    private Entrenamiento insertedEntrenamiento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entrenamiento createEntity(EntityManager em) {
        Entrenamiento entrenamiento = new Entrenamiento().fechaHora(DEFAULT_FECHA_HORA).duracion(DEFAULT_DURACION);
        return entrenamiento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entrenamiento createUpdatedEntity(EntityManager em) {
        Entrenamiento entrenamiento = new Entrenamiento().fechaHora(UPDATED_FECHA_HORA).duracion(UPDATED_DURACION);
        return entrenamiento;
    }

    @BeforeEach
    public void initTest() {
        entrenamiento = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEntrenamiento != null) {
            entrenamientoRepository.delete(insertedEntrenamiento);
            insertedEntrenamiento = null;
        }
    }

    @Test
    @Transactional
    void createEntrenamiento() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Entrenamiento
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.toDto(entrenamiento);
        var returnedEntrenamientoDTO = om.readValue(
            restEntrenamientoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrenamientoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EntrenamientoDTO.class
        );

        // Validate the Entrenamiento in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEntrenamiento = entrenamientoMapper.toEntity(returnedEntrenamientoDTO);
        assertEntrenamientoUpdatableFieldsEquals(returnedEntrenamiento, getPersistedEntrenamiento(returnedEntrenamiento));

        insertedEntrenamiento = returnedEntrenamiento;
    }

    @Test
    @Transactional
    void createEntrenamientoWithExistingId() throws Exception {
        // Create the Entrenamiento with an existing ID
        entrenamiento.setId(1L);
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.toDto(entrenamiento);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntrenamientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrenamientoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entrenamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaHoraIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entrenamiento.setFechaHora(null);

        // Create the Entrenamiento, which fails.
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.toDto(entrenamiento);

        restEntrenamientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrenamientoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDuracionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        entrenamiento.setDuracion(null);

        // Create the Entrenamiento, which fails.
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.toDto(entrenamiento);

        restEntrenamientoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrenamientoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEntrenamientos() throws Exception {
        // Initialize the database
        insertedEntrenamiento = entrenamientoRepository.saveAndFlush(entrenamiento);

        // Get all the entrenamientoList
        restEntrenamientoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrenamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaHora").value(hasItem(DEFAULT_FECHA_HORA.toString())))
            .andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION)));
    }

    @Test
    @Transactional
    void getEntrenamiento() throws Exception {
        // Initialize the database
        insertedEntrenamiento = entrenamientoRepository.saveAndFlush(entrenamiento);

        // Get the entrenamiento
        restEntrenamientoMockMvc
            .perform(get(ENTITY_API_URL_ID, entrenamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entrenamiento.getId().intValue()))
            .andExpect(jsonPath("$.fechaHora").value(DEFAULT_FECHA_HORA.toString()))
            .andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION));
    }

    @Test
    @Transactional
    void getNonExistingEntrenamiento() throws Exception {
        // Get the entrenamiento
        restEntrenamientoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEntrenamiento() throws Exception {
        // Initialize the database
        insertedEntrenamiento = entrenamientoRepository.saveAndFlush(entrenamiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entrenamiento
        Entrenamiento updatedEntrenamiento = entrenamientoRepository.findById(entrenamiento.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedEntrenamiento are not directly saved in db
        em.detach(updatedEntrenamiento);
        updatedEntrenamiento.fechaHora(UPDATED_FECHA_HORA).duracion(UPDATED_DURACION);
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.toDto(updatedEntrenamiento);

        restEntrenamientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entrenamientoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entrenamientoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Entrenamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEntrenamientoToMatchAllProperties(updatedEntrenamiento);
    }

    @Test
    @Transactional
    void putNonExistingEntrenamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrenamiento.setId(longCount.incrementAndGet());

        // Create the Entrenamiento
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.toDto(entrenamiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrenamientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entrenamientoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entrenamientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrenamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntrenamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrenamiento.setId(longCount.incrementAndGet());

        // Create the Entrenamiento
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.toDto(entrenamiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrenamientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(entrenamientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrenamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntrenamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrenamiento.setId(longCount.incrementAndGet());

        // Create the Entrenamiento
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.toDto(entrenamiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrenamientoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(entrenamientoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entrenamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntrenamientoWithPatch() throws Exception {
        // Initialize the database
        insertedEntrenamiento = entrenamientoRepository.saveAndFlush(entrenamiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entrenamiento using partial update
        Entrenamiento partialUpdatedEntrenamiento = new Entrenamiento();
        partialUpdatedEntrenamiento.setId(entrenamiento.getId());

        partialUpdatedEntrenamiento.fechaHora(UPDATED_FECHA_HORA);

        restEntrenamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntrenamiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEntrenamiento))
            )
            .andExpect(status().isOk());

        // Validate the Entrenamiento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntrenamientoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEntrenamiento, entrenamiento),
            getPersistedEntrenamiento(entrenamiento)
        );
    }

    @Test
    @Transactional
    void fullUpdateEntrenamientoWithPatch() throws Exception {
        // Initialize the database
        insertedEntrenamiento = entrenamientoRepository.saveAndFlush(entrenamiento);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the entrenamiento using partial update
        Entrenamiento partialUpdatedEntrenamiento = new Entrenamiento();
        partialUpdatedEntrenamiento.setId(entrenamiento.getId());

        partialUpdatedEntrenamiento.fechaHora(UPDATED_FECHA_HORA).duracion(UPDATED_DURACION);

        restEntrenamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntrenamiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEntrenamiento))
            )
            .andExpect(status().isOk());

        // Validate the Entrenamiento in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEntrenamientoUpdatableFieldsEquals(partialUpdatedEntrenamiento, getPersistedEntrenamiento(partialUpdatedEntrenamiento));
    }

    @Test
    @Transactional
    void patchNonExistingEntrenamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrenamiento.setId(longCount.incrementAndGet());

        // Create the Entrenamiento
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.toDto(entrenamiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntrenamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entrenamientoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(entrenamientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrenamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntrenamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrenamiento.setId(longCount.incrementAndGet());

        // Create the Entrenamiento
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.toDto(entrenamiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrenamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(entrenamientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entrenamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntrenamiento() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        entrenamiento.setId(longCount.incrementAndGet());

        // Create the Entrenamiento
        EntrenamientoDTO entrenamientoDTO = entrenamientoMapper.toDto(entrenamiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntrenamientoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(entrenamientoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entrenamiento in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntrenamiento() throws Exception {
        // Initialize the database
        insertedEntrenamiento = entrenamientoRepository.saveAndFlush(entrenamiento);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the entrenamiento
        restEntrenamientoMockMvc
            .perform(delete(ENTITY_API_URL_ID, entrenamiento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return entrenamientoRepository.count();
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

    protected Entrenamiento getPersistedEntrenamiento(Entrenamiento entrenamiento) {
        return entrenamientoRepository.findById(entrenamiento.getId()).orElseThrow();
    }

    protected void assertPersistedEntrenamientoToMatchAllProperties(Entrenamiento expectedEntrenamiento) {
        assertEntrenamientoAllPropertiesEquals(expectedEntrenamiento, getPersistedEntrenamiento(expectedEntrenamiento));
    }

    protected void assertPersistedEntrenamientoToMatchUpdatableProperties(Entrenamiento expectedEntrenamiento) {
        assertEntrenamientoAllUpdatablePropertiesEquals(expectedEntrenamiento, getPersistedEntrenamiento(expectedEntrenamiento));
    }
}
