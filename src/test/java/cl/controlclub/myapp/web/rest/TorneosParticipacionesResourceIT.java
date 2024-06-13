package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.TorneosParticipacionesAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static cl.controlclub.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.TorneosParticipaciones;
import cl.controlclub.myapp.repository.TorneosParticipacionesRepository;
import cl.controlclub.myapp.service.dto.TorneosParticipacionesDTO;
import cl.controlclub.myapp.service.mapper.TorneosParticipacionesMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
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
 * Integration tests for the {@link TorneosParticipacionesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TorneosParticipacionesResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTO = new BigDecimal(2);

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/torneos-participaciones";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TorneosParticipacionesRepository torneosParticipacionesRepository;

    @Autowired
    private TorneosParticipacionesMapper torneosParticipacionesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTorneosParticipacionesMockMvc;

    private TorneosParticipaciones torneosParticipaciones;

    private TorneosParticipaciones insertedTorneosParticipaciones;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TorneosParticipaciones createEntity(EntityManager em) {
        TorneosParticipaciones torneosParticipaciones = new TorneosParticipaciones()
            .descripcion(DEFAULT_DESCRIPCION)
            .monto(DEFAULT_MONTO)
            .fecha(DEFAULT_FECHA);
        return torneosParticipaciones;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TorneosParticipaciones createUpdatedEntity(EntityManager em) {
        TorneosParticipaciones torneosParticipaciones = new TorneosParticipaciones()
            .descripcion(UPDATED_DESCRIPCION)
            .monto(UPDATED_MONTO)
            .fecha(UPDATED_FECHA);
        return torneosParticipaciones;
    }

    @BeforeEach
    public void initTest() {
        torneosParticipaciones = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedTorneosParticipaciones != null) {
            torneosParticipacionesRepository.delete(insertedTorneosParticipaciones);
            insertedTorneosParticipaciones = null;
        }
    }

    @Test
    @Transactional
    void createTorneosParticipaciones() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TorneosParticipaciones
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(torneosParticipaciones);
        var returnedTorneosParticipacionesDTO = om.readValue(
            restTorneosParticipacionesMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneosParticipacionesDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TorneosParticipacionesDTO.class
        );

        // Validate the TorneosParticipaciones in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTorneosParticipaciones = torneosParticipacionesMapper.toEntity(returnedTorneosParticipacionesDTO);
        assertTorneosParticipacionesUpdatableFieldsEquals(
            returnedTorneosParticipaciones,
            getPersistedTorneosParticipaciones(returnedTorneosParticipaciones)
        );

        insertedTorneosParticipaciones = returnedTorneosParticipaciones;
    }

    @Test
    @Transactional
    void createTorneosParticipacionesWithExistingId() throws Exception {
        // Create the TorneosParticipaciones with an existing ID
        torneosParticipaciones.setId(1L);
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(torneosParticipaciones);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTorneosParticipacionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneosParticipacionesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TorneosParticipaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        torneosParticipaciones.setDescripcion(null);

        // Create the TorneosParticipaciones, which fails.
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(torneosParticipaciones);

        restTorneosParticipacionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneosParticipacionesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        torneosParticipaciones.setMonto(null);

        // Create the TorneosParticipaciones, which fails.
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(torneosParticipaciones);

        restTorneosParticipacionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneosParticipacionesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        torneosParticipaciones.setFecha(null);

        // Create the TorneosParticipaciones, which fails.
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(torneosParticipaciones);

        restTorneosParticipacionesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneosParticipacionesDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTorneosParticipaciones() throws Exception {
        // Initialize the database
        insertedTorneosParticipaciones = torneosParticipacionesRepository.saveAndFlush(torneosParticipaciones);

        // Get all the torneosParticipacionesList
        restTorneosParticipacionesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(torneosParticipaciones.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(sameNumber(DEFAULT_MONTO))))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    void getTorneosParticipaciones() throws Exception {
        // Initialize the database
        insertedTorneosParticipaciones = torneosParticipacionesRepository.saveAndFlush(torneosParticipaciones);

        // Get the torneosParticipaciones
        restTorneosParticipacionesMockMvc
            .perform(get(ENTITY_API_URL_ID, torneosParticipaciones.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(torneosParticipaciones.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.monto").value(sameNumber(DEFAULT_MONTO)))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTorneosParticipaciones() throws Exception {
        // Get the torneosParticipaciones
        restTorneosParticipacionesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTorneosParticipaciones() throws Exception {
        // Initialize the database
        insertedTorneosParticipaciones = torneosParticipacionesRepository.saveAndFlush(torneosParticipaciones);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the torneosParticipaciones
        TorneosParticipaciones updatedTorneosParticipaciones = torneosParticipacionesRepository
            .findById(torneosParticipaciones.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedTorneosParticipaciones are not directly saved in db
        em.detach(updatedTorneosParticipaciones);
        updatedTorneosParticipaciones.descripcion(UPDATED_DESCRIPCION).monto(UPDATED_MONTO).fecha(UPDATED_FECHA);
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(updatedTorneosParticipaciones);

        restTorneosParticipacionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, torneosParticipacionesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(torneosParticipacionesDTO))
            )
            .andExpect(status().isOk());

        // Validate the TorneosParticipaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTorneosParticipacionesToMatchAllProperties(updatedTorneosParticipaciones);
    }

    @Test
    @Transactional
    void putNonExistingTorneosParticipaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneosParticipaciones.setId(longCount.incrementAndGet());

        // Create the TorneosParticipaciones
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(torneosParticipaciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTorneosParticipacionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, torneosParticipacionesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(torneosParticipacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TorneosParticipaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTorneosParticipaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneosParticipaciones.setId(longCount.incrementAndGet());

        // Create the TorneosParticipaciones
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(torneosParticipaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorneosParticipacionesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(torneosParticipacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TorneosParticipaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTorneosParticipaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneosParticipaciones.setId(longCount.incrementAndGet());

        // Create the TorneosParticipaciones
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(torneosParticipaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorneosParticipacionesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(torneosParticipacionesDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TorneosParticipaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTorneosParticipacionesWithPatch() throws Exception {
        // Initialize the database
        insertedTorneosParticipaciones = torneosParticipacionesRepository.saveAndFlush(torneosParticipaciones);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the torneosParticipaciones using partial update
        TorneosParticipaciones partialUpdatedTorneosParticipaciones = new TorneosParticipaciones();
        partialUpdatedTorneosParticipaciones.setId(torneosParticipaciones.getId());

        restTorneosParticipacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTorneosParticipaciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTorneosParticipaciones))
            )
            .andExpect(status().isOk());

        // Validate the TorneosParticipaciones in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTorneosParticipacionesUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTorneosParticipaciones, torneosParticipaciones),
            getPersistedTorneosParticipaciones(torneosParticipaciones)
        );
    }

    @Test
    @Transactional
    void fullUpdateTorneosParticipacionesWithPatch() throws Exception {
        // Initialize the database
        insertedTorneosParticipaciones = torneosParticipacionesRepository.saveAndFlush(torneosParticipaciones);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the torneosParticipaciones using partial update
        TorneosParticipaciones partialUpdatedTorneosParticipaciones = new TorneosParticipaciones();
        partialUpdatedTorneosParticipaciones.setId(torneosParticipaciones.getId());

        partialUpdatedTorneosParticipaciones.descripcion(UPDATED_DESCRIPCION).monto(UPDATED_MONTO).fecha(UPDATED_FECHA);

        restTorneosParticipacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTorneosParticipaciones.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTorneosParticipaciones))
            )
            .andExpect(status().isOk());

        // Validate the TorneosParticipaciones in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTorneosParticipacionesUpdatableFieldsEquals(
            partialUpdatedTorneosParticipaciones,
            getPersistedTorneosParticipaciones(partialUpdatedTorneosParticipaciones)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTorneosParticipaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneosParticipaciones.setId(longCount.incrementAndGet());

        // Create the TorneosParticipaciones
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(torneosParticipaciones);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTorneosParticipacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, torneosParticipacionesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(torneosParticipacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TorneosParticipaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTorneosParticipaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneosParticipaciones.setId(longCount.incrementAndGet());

        // Create the TorneosParticipaciones
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(torneosParticipaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorneosParticipacionesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(torneosParticipacionesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TorneosParticipaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTorneosParticipaciones() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        torneosParticipaciones.setId(longCount.incrementAndGet());

        // Create the TorneosParticipaciones
        TorneosParticipacionesDTO torneosParticipacionesDTO = torneosParticipacionesMapper.toDto(torneosParticipaciones);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTorneosParticipacionesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(torneosParticipacionesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TorneosParticipaciones in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTorneosParticipaciones() throws Exception {
        // Initialize the database
        insertedTorneosParticipaciones = torneosParticipacionesRepository.saveAndFlush(torneosParticipaciones);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the torneosParticipaciones
        restTorneosParticipacionesMockMvc
            .perform(delete(ENTITY_API_URL_ID, torneosParticipaciones.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return torneosParticipacionesRepository.count();
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

    protected TorneosParticipaciones getPersistedTorneosParticipaciones(TorneosParticipaciones torneosParticipaciones) {
        return torneosParticipacionesRepository.findById(torneosParticipaciones.getId()).orElseThrow();
    }

    protected void assertPersistedTorneosParticipacionesToMatchAllProperties(TorneosParticipaciones expectedTorneosParticipaciones) {
        assertTorneosParticipacionesAllPropertiesEquals(
            expectedTorneosParticipaciones,
            getPersistedTorneosParticipaciones(expectedTorneosParticipaciones)
        );
    }

    protected void assertPersistedTorneosParticipacionesToMatchUpdatableProperties(TorneosParticipaciones expectedTorneosParticipaciones) {
        assertTorneosParticipacionesAllUpdatablePropertiesEquals(
            expectedTorneosParticipaciones,
            getPersistedTorneosParticipaciones(expectedTorneosParticipaciones)
        );
    }
}
