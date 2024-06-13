package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.AsistenciaAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Asistencia;
import cl.controlclub.myapp.domain.enumeration.TipoAsistencia;
import cl.controlclub.myapp.repository.AsistenciaRepository;
import cl.controlclub.myapp.service.dto.AsistenciaDTO;
import cl.controlclub.myapp.service.mapper.AsistenciaMapper;
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
 * Integration tests for the {@link AsistenciaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AsistenciaResourceIT {

    private static final TipoAsistencia DEFAULT_TIPO = TipoAsistencia.ENTRENAMIENTO;
    private static final TipoAsistencia UPDATED_TIPO = TipoAsistencia.PARTIDO;

    private static final Long DEFAULT_ID_EVENTO = 1L;
    private static final Long UPDATED_ID_EVENTO = 2L;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ASISTENCIA = false;
    private static final Boolean UPDATED_ASISTENCIA = true;

    private static final String ENTITY_API_URL = "/api/asistencias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AsistenciaRepository asistenciaRepository;

    @Autowired
    private AsistenciaMapper asistenciaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAsistenciaMockMvc;

    private Asistencia asistencia;

    private Asistencia insertedAsistencia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asistencia createEntity(EntityManager em) {
        Asistencia asistencia = new Asistencia()
            .tipo(DEFAULT_TIPO)
            .idEvento(DEFAULT_ID_EVENTO)
            .fecha(DEFAULT_FECHA)
            .asistencia(DEFAULT_ASISTENCIA);
        return asistencia;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asistencia createUpdatedEntity(EntityManager em) {
        Asistencia asistencia = new Asistencia()
            .tipo(UPDATED_TIPO)
            .idEvento(UPDATED_ID_EVENTO)
            .fecha(UPDATED_FECHA)
            .asistencia(UPDATED_ASISTENCIA);
        return asistencia;
    }

    @BeforeEach
    public void initTest() {
        asistencia = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAsistencia != null) {
            asistenciaRepository.delete(insertedAsistencia);
            insertedAsistencia = null;
        }
    }

    @Test
    @Transactional
    void createAsistencia() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Asistencia
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);
        var returnedAsistenciaDTO = om.readValue(
            restAsistenciaMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asistenciaDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AsistenciaDTO.class
        );

        // Validate the Asistencia in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAsistencia = asistenciaMapper.toEntity(returnedAsistenciaDTO);
        assertAsistenciaUpdatableFieldsEquals(returnedAsistencia, getPersistedAsistencia(returnedAsistencia));

        insertedAsistencia = returnedAsistencia;
    }

    @Test
    @Transactional
    void createAsistenciaWithExistingId() throws Exception {
        // Create the Asistencia with an existing ID
        asistencia.setId(1L);
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAsistenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asistenciaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Asistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asistencia.setTipo(null);

        // Create the Asistencia, which fails.
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);

        restAsistenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asistenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIdEventoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asistencia.setIdEvento(null);

        // Create the Asistencia, which fails.
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);

        restAsistenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asistenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asistencia.setFecha(null);

        // Create the Asistencia, which fails.
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);

        restAsistenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asistenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAsistenciaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asistencia.setAsistencia(null);

        // Create the Asistencia, which fails.
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);

        restAsistenciaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asistenciaDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAsistencias() throws Exception {
        // Initialize the database
        insertedAsistencia = asistenciaRepository.saveAndFlush(asistencia);

        // Get all the asistenciaList
        restAsistenciaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asistencia.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].idEvento").value(hasItem(DEFAULT_ID_EVENTO.intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].asistencia").value(hasItem(DEFAULT_ASISTENCIA.booleanValue())));
    }

    @Test
    @Transactional
    void getAsistencia() throws Exception {
        // Initialize the database
        insertedAsistencia = asistenciaRepository.saveAndFlush(asistencia);

        // Get the asistencia
        restAsistenciaMockMvc
            .perform(get(ENTITY_API_URL_ID, asistencia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(asistencia.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.idEvento").value(DEFAULT_ID_EVENTO.intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.asistencia").value(DEFAULT_ASISTENCIA.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingAsistencia() throws Exception {
        // Get the asistencia
        restAsistenciaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAsistencia() throws Exception {
        // Initialize the database
        insertedAsistencia = asistenciaRepository.saveAndFlush(asistencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asistencia
        Asistencia updatedAsistencia = asistenciaRepository.findById(asistencia.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAsistencia are not directly saved in db
        em.detach(updatedAsistencia);
        updatedAsistencia.tipo(UPDATED_TIPO).idEvento(UPDATED_ID_EVENTO).fecha(UPDATED_FECHA).asistencia(UPDATED_ASISTENCIA);
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(updatedAsistencia);

        restAsistenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, asistenciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(asistenciaDTO))
            )
            .andExpect(status().isOk());

        // Validate the Asistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAsistenciaToMatchAllProperties(updatedAsistencia);
    }

    @Test
    @Transactional
    void putNonExistingAsistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asistencia.setId(longCount.incrementAndGet());

        // Create the Asistencia
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsistenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, asistenciaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(asistenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAsistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asistencia.setId(longCount.incrementAndGet());

        // Create the Asistencia
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsistenciaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(asistenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAsistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asistencia.setId(longCount.incrementAndGet());

        // Create the Asistencia
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsistenciaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asistenciaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAsistenciaWithPatch() throws Exception {
        // Initialize the database
        insertedAsistencia = asistenciaRepository.saveAndFlush(asistencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asistencia using partial update
        Asistencia partialUpdatedAsistencia = new Asistencia();
        partialUpdatedAsistencia.setId(asistencia.getId());

        partialUpdatedAsistencia.idEvento(UPDATED_ID_EVENTO);

        restAsistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsistencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsistencia))
            )
            .andExpect(status().isOk());

        // Validate the Asistencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAsistenciaUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAsistencia, asistencia),
            getPersistedAsistencia(asistencia)
        );
    }

    @Test
    @Transactional
    void fullUpdateAsistenciaWithPatch() throws Exception {
        // Initialize the database
        insertedAsistencia = asistenciaRepository.saveAndFlush(asistencia);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asistencia using partial update
        Asistencia partialUpdatedAsistencia = new Asistencia();
        partialUpdatedAsistencia.setId(asistencia.getId());

        partialUpdatedAsistencia.tipo(UPDATED_TIPO).idEvento(UPDATED_ID_EVENTO).fecha(UPDATED_FECHA).asistencia(UPDATED_ASISTENCIA);

        restAsistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsistencia.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsistencia))
            )
            .andExpect(status().isOk());

        // Validate the Asistencia in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAsistenciaUpdatableFieldsEquals(partialUpdatedAsistencia, getPersistedAsistencia(partialUpdatedAsistencia));
    }

    @Test
    @Transactional
    void patchNonExistingAsistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asistencia.setId(longCount.incrementAndGet());

        // Create the Asistencia
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, asistenciaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(asistenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAsistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asistencia.setId(longCount.incrementAndGet());

        // Create the Asistencia
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsistenciaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(asistenciaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAsistencia() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asistencia.setId(longCount.incrementAndGet());

        // Create the Asistencia
        AsistenciaDTO asistenciaDTO = asistenciaMapper.toDto(asistencia);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsistenciaMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(asistenciaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asistencia in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAsistencia() throws Exception {
        // Initialize the database
        insertedAsistencia = asistenciaRepository.saveAndFlush(asistencia);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the asistencia
        restAsistenciaMockMvc
            .perform(delete(ENTITY_API_URL_ID, asistencia.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return asistenciaRepository.count();
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

    protected Asistencia getPersistedAsistencia(Asistencia asistencia) {
        return asistenciaRepository.findById(asistencia.getId()).orElseThrow();
    }

    protected void assertPersistedAsistenciaToMatchAllProperties(Asistencia expectedAsistencia) {
        assertAsistenciaAllPropertiesEquals(expectedAsistencia, getPersistedAsistencia(expectedAsistencia));
    }

    protected void assertPersistedAsistenciaToMatchUpdatableProperties(Asistencia expectedAsistencia) {
        assertAsistenciaAllUpdatablePropertiesEquals(expectedAsistencia, getPersistedAsistencia(expectedAsistencia));
    }
}
