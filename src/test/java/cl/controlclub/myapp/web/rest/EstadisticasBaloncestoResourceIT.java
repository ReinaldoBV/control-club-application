package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.EstadisticasBaloncestoAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static cl.controlclub.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.EstadisticasBaloncesto;
import cl.controlclub.myapp.repository.EstadisticasBaloncestoRepository;
import cl.controlclub.myapp.service.dto.EstadisticasBaloncestoDTO;
import cl.controlclub.myapp.service.mapper.EstadisticasBaloncestoMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link EstadisticasBaloncestoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstadisticasBaloncestoResourceIT {

    private static final Integer DEFAULT_PUNTOS = 1;
    private static final Integer UPDATED_PUNTOS = 2;

    private static final Integer DEFAULT_REBOTES = 1;
    private static final Integer UPDATED_REBOTES = 2;

    private static final Integer DEFAULT_ASISTENCIAS = 1;
    private static final Integer UPDATED_ASISTENCIAS = 2;

    private static final Integer DEFAULT_ROBOS = 1;
    private static final Integer UPDATED_ROBOS = 2;

    private static final Integer DEFAULT_BLOQUEOS = 1;
    private static final Integer UPDATED_BLOQUEOS = 2;

    private static final BigDecimal DEFAULT_PORCENTAJE_TIRO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PORCENTAJE_TIRO = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/estadisticas-baloncestos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private EstadisticasBaloncestoRepository estadisticasBaloncestoRepository;

    @Autowired
    private EstadisticasBaloncestoMapper estadisticasBaloncestoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstadisticasBaloncestoMockMvc;

    private EstadisticasBaloncesto estadisticasBaloncesto;

    private EstadisticasBaloncesto insertedEstadisticasBaloncesto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadisticasBaloncesto createEntity(EntityManager em) {
        EstadisticasBaloncesto estadisticasBaloncesto = new EstadisticasBaloncesto()
            .puntos(DEFAULT_PUNTOS)
            .rebotes(DEFAULT_REBOTES)
            .asistencias(DEFAULT_ASISTENCIAS)
            .robos(DEFAULT_ROBOS)
            .bloqueos(DEFAULT_BLOQUEOS)
            .porcentajeTiro(DEFAULT_PORCENTAJE_TIRO);
        return estadisticasBaloncesto;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstadisticasBaloncesto createUpdatedEntity(EntityManager em) {
        EstadisticasBaloncesto estadisticasBaloncesto = new EstadisticasBaloncesto()
            .puntos(UPDATED_PUNTOS)
            .rebotes(UPDATED_REBOTES)
            .asistencias(UPDATED_ASISTENCIAS)
            .robos(UPDATED_ROBOS)
            .bloqueos(UPDATED_BLOQUEOS)
            .porcentajeTiro(UPDATED_PORCENTAJE_TIRO);
        return estadisticasBaloncesto;
    }

    @BeforeEach
    public void initTest() {
        estadisticasBaloncesto = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedEstadisticasBaloncesto != null) {
            estadisticasBaloncestoRepository.delete(insertedEstadisticasBaloncesto);
            insertedEstadisticasBaloncesto = null;
        }
    }

    @Test
    @Transactional
    void createEstadisticasBaloncesto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the EstadisticasBaloncesto
        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO = estadisticasBaloncestoMapper.toDto(estadisticasBaloncesto);
        var returnedEstadisticasBaloncestoDTO = om.readValue(
            restEstadisticasBaloncestoMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estadisticasBaloncestoDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            EstadisticasBaloncestoDTO.class
        );

        // Validate the EstadisticasBaloncesto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedEstadisticasBaloncesto = estadisticasBaloncestoMapper.toEntity(returnedEstadisticasBaloncestoDTO);
        assertEstadisticasBaloncestoUpdatableFieldsEquals(
            returnedEstadisticasBaloncesto,
            getPersistedEstadisticasBaloncesto(returnedEstadisticasBaloncesto)
        );

        insertedEstadisticasBaloncesto = returnedEstadisticasBaloncesto;
    }

    @Test
    @Transactional
    void createEstadisticasBaloncestoWithExistingId() throws Exception {
        // Create the EstadisticasBaloncesto with an existing ID
        estadisticasBaloncesto.setId(1L);
        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO = estadisticasBaloncestoMapper.toDto(estadisticasBaloncesto);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstadisticasBaloncestoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estadisticasBaloncestoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EstadisticasBaloncesto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEstadisticasBaloncestos() throws Exception {
        // Initialize the database
        insertedEstadisticasBaloncesto = estadisticasBaloncestoRepository.saveAndFlush(estadisticasBaloncesto);

        // Get all the estadisticasBaloncestoList
        restEstadisticasBaloncestoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estadisticasBaloncesto.getId().intValue())))
            .andExpect(jsonPath("$.[*].puntos").value(hasItem(DEFAULT_PUNTOS)))
            .andExpect(jsonPath("$.[*].rebotes").value(hasItem(DEFAULT_REBOTES)))
            .andExpect(jsonPath("$.[*].asistencias").value(hasItem(DEFAULT_ASISTENCIAS)))
            .andExpect(jsonPath("$.[*].robos").value(hasItem(DEFAULT_ROBOS)))
            .andExpect(jsonPath("$.[*].bloqueos").value(hasItem(DEFAULT_BLOQUEOS)))
            .andExpect(jsonPath("$.[*].porcentajeTiro").value(hasItem(sameNumber(DEFAULT_PORCENTAJE_TIRO))));
    }

    @Test
    @Transactional
    void getEstadisticasBaloncesto() throws Exception {
        // Initialize the database
        insertedEstadisticasBaloncesto = estadisticasBaloncestoRepository.saveAndFlush(estadisticasBaloncesto);

        // Get the estadisticasBaloncesto
        restEstadisticasBaloncestoMockMvc
            .perform(get(ENTITY_API_URL_ID, estadisticasBaloncesto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estadisticasBaloncesto.getId().intValue()))
            .andExpect(jsonPath("$.puntos").value(DEFAULT_PUNTOS))
            .andExpect(jsonPath("$.rebotes").value(DEFAULT_REBOTES))
            .andExpect(jsonPath("$.asistencias").value(DEFAULT_ASISTENCIAS))
            .andExpect(jsonPath("$.robos").value(DEFAULT_ROBOS))
            .andExpect(jsonPath("$.bloqueos").value(DEFAULT_BLOQUEOS))
            .andExpect(jsonPath("$.porcentajeTiro").value(sameNumber(DEFAULT_PORCENTAJE_TIRO)));
    }

    @Test
    @Transactional
    void getNonExistingEstadisticasBaloncesto() throws Exception {
        // Get the estadisticasBaloncesto
        restEstadisticasBaloncestoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingEstadisticasBaloncesto() throws Exception {
        // Initialize the database
        insertedEstadisticasBaloncesto = estadisticasBaloncestoRepository.saveAndFlush(estadisticasBaloncesto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estadisticasBaloncesto
        EstadisticasBaloncesto updatedEstadisticasBaloncesto = estadisticasBaloncestoRepository
            .findById(estadisticasBaloncesto.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedEstadisticasBaloncesto are not directly saved in db
        em.detach(updatedEstadisticasBaloncesto);
        updatedEstadisticasBaloncesto
            .puntos(UPDATED_PUNTOS)
            .rebotes(UPDATED_REBOTES)
            .asistencias(UPDATED_ASISTENCIAS)
            .robos(UPDATED_ROBOS)
            .bloqueos(UPDATED_BLOQUEOS)
            .porcentajeTiro(UPDATED_PORCENTAJE_TIRO);
        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO = estadisticasBaloncestoMapper.toDto(updatedEstadisticasBaloncesto);

        restEstadisticasBaloncestoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadisticasBaloncestoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(estadisticasBaloncestoDTO))
            )
            .andExpect(status().isOk());

        // Validate the EstadisticasBaloncesto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedEstadisticasBaloncestoToMatchAllProperties(updatedEstadisticasBaloncesto);
    }

    @Test
    @Transactional
    void putNonExistingEstadisticasBaloncesto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadisticasBaloncesto.setId(longCount.incrementAndGet());

        // Create the EstadisticasBaloncesto
        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO = estadisticasBaloncestoMapper.toDto(estadisticasBaloncesto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadisticasBaloncestoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estadisticasBaloncestoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(estadisticasBaloncestoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadisticasBaloncesto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstadisticasBaloncesto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadisticasBaloncesto.setId(longCount.incrementAndGet());

        // Create the EstadisticasBaloncesto
        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO = estadisticasBaloncestoMapper.toDto(estadisticasBaloncesto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadisticasBaloncestoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(estadisticasBaloncestoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadisticasBaloncesto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstadisticasBaloncesto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadisticasBaloncesto.setId(longCount.incrementAndGet());

        // Create the EstadisticasBaloncesto
        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO = estadisticasBaloncestoMapper.toDto(estadisticasBaloncesto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadisticasBaloncestoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(estadisticasBaloncestoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadisticasBaloncesto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstadisticasBaloncestoWithPatch() throws Exception {
        // Initialize the database
        insertedEstadisticasBaloncesto = estadisticasBaloncestoRepository.saveAndFlush(estadisticasBaloncesto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estadisticasBaloncesto using partial update
        EstadisticasBaloncesto partialUpdatedEstadisticasBaloncesto = new EstadisticasBaloncesto();
        partialUpdatedEstadisticasBaloncesto.setId(estadisticasBaloncesto.getId());

        partialUpdatedEstadisticasBaloncesto
            .rebotes(UPDATED_REBOTES)
            .asistencias(UPDATED_ASISTENCIAS)
            .bloqueos(UPDATED_BLOQUEOS)
            .porcentajeTiro(UPDATED_PORCENTAJE_TIRO);

        restEstadisticasBaloncestoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadisticasBaloncesto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEstadisticasBaloncesto))
            )
            .andExpect(status().isOk());

        // Validate the EstadisticasBaloncesto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEstadisticasBaloncestoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedEstadisticasBaloncesto, estadisticasBaloncesto),
            getPersistedEstadisticasBaloncesto(estadisticasBaloncesto)
        );
    }

    @Test
    @Transactional
    void fullUpdateEstadisticasBaloncestoWithPatch() throws Exception {
        // Initialize the database
        insertedEstadisticasBaloncesto = estadisticasBaloncestoRepository.saveAndFlush(estadisticasBaloncesto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the estadisticasBaloncesto using partial update
        EstadisticasBaloncesto partialUpdatedEstadisticasBaloncesto = new EstadisticasBaloncesto();
        partialUpdatedEstadisticasBaloncesto.setId(estadisticasBaloncesto.getId());

        partialUpdatedEstadisticasBaloncesto
            .puntos(UPDATED_PUNTOS)
            .rebotes(UPDATED_REBOTES)
            .asistencias(UPDATED_ASISTENCIAS)
            .robos(UPDATED_ROBOS)
            .bloqueos(UPDATED_BLOQUEOS)
            .porcentajeTiro(UPDATED_PORCENTAJE_TIRO);

        restEstadisticasBaloncestoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstadisticasBaloncesto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedEstadisticasBaloncesto))
            )
            .andExpect(status().isOk());

        // Validate the EstadisticasBaloncesto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertEstadisticasBaloncestoUpdatableFieldsEquals(
            partialUpdatedEstadisticasBaloncesto,
            getPersistedEstadisticasBaloncesto(partialUpdatedEstadisticasBaloncesto)
        );
    }

    @Test
    @Transactional
    void patchNonExistingEstadisticasBaloncesto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadisticasBaloncesto.setId(longCount.incrementAndGet());

        // Create the EstadisticasBaloncesto
        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO = estadisticasBaloncestoMapper.toDto(estadisticasBaloncesto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstadisticasBaloncestoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estadisticasBaloncestoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(estadisticasBaloncestoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadisticasBaloncesto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstadisticasBaloncesto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadisticasBaloncesto.setId(longCount.incrementAndGet());

        // Create the EstadisticasBaloncesto
        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO = estadisticasBaloncestoMapper.toDto(estadisticasBaloncesto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadisticasBaloncestoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(estadisticasBaloncestoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstadisticasBaloncesto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstadisticasBaloncesto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        estadisticasBaloncesto.setId(longCount.incrementAndGet());

        // Create the EstadisticasBaloncesto
        EstadisticasBaloncestoDTO estadisticasBaloncestoDTO = estadisticasBaloncestoMapper.toDto(estadisticasBaloncesto);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstadisticasBaloncestoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(estadisticasBaloncestoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstadisticasBaloncesto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstadisticasBaloncesto() throws Exception {
        // Initialize the database
        insertedEstadisticasBaloncesto = estadisticasBaloncestoRepository.saveAndFlush(estadisticasBaloncesto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the estadisticasBaloncesto
        restEstadisticasBaloncestoMockMvc
            .perform(delete(ENTITY_API_URL_ID, estadisticasBaloncesto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return estadisticasBaloncestoRepository.count();
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

    protected EstadisticasBaloncesto getPersistedEstadisticasBaloncesto(EstadisticasBaloncesto estadisticasBaloncesto) {
        return estadisticasBaloncestoRepository.findById(estadisticasBaloncesto.getId()).orElseThrow();
    }

    protected void assertPersistedEstadisticasBaloncestoToMatchAllProperties(EstadisticasBaloncesto expectedEstadisticasBaloncesto) {
        assertEstadisticasBaloncestoAllPropertiesEquals(
            expectedEstadisticasBaloncesto,
            getPersistedEstadisticasBaloncesto(expectedEstadisticasBaloncesto)
        );
    }

    protected void assertPersistedEstadisticasBaloncestoToMatchUpdatableProperties(EstadisticasBaloncesto expectedEstadisticasBaloncesto) {
        assertEstadisticasBaloncestoAllUpdatablePropertiesEquals(
            expectedEstadisticasBaloncesto,
            getPersistedEstadisticasBaloncesto(expectedEstadisticasBaloncesto)
        );
    }
}
