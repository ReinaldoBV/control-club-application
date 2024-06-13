package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.PartidoAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Partido;
import cl.controlclub.myapp.repository.PartidoRepository;
import cl.controlclub.myapp.service.dto.PartidoDTO;
import cl.controlclub.myapp.service.mapper.PartidoMapper;
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
 * Integration tests for the {@link PartidoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PartidoResourceIT {

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OPONENTE = "AAAAAAAAAA";
    private static final String UPDATED_OPONENTE = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/partidos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private PartidoMapper partidoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartidoMockMvc;

    private Partido partido;

    private Partido insertedPartido;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partido createEntity(EntityManager em) {
        Partido partido = new Partido().fecha(DEFAULT_FECHA).oponente(DEFAULT_OPONENTE).resultado(DEFAULT_RESULTADO);
        return partido;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Partido createUpdatedEntity(EntityManager em) {
        Partido partido = new Partido().fecha(UPDATED_FECHA).oponente(UPDATED_OPONENTE).resultado(UPDATED_RESULTADO);
        return partido;
    }

    @BeforeEach
    public void initTest() {
        partido = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPartido != null) {
            partidoRepository.delete(insertedPartido);
            insertedPartido = null;
        }
    }

    @Test
    @Transactional
    void createPartido() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);
        var returnedPartidoDTO = om.readValue(
            restPartidoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PartidoDTO.class
        );

        // Validate the Partido in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPartido = partidoMapper.toEntity(returnedPartidoDTO);
        assertPartidoUpdatableFieldsEquals(returnedPartido, getPersistedPartido(returnedPartido));

        insertedPartido = returnedPartido;
    }

    @Test
    @Transactional
    void createPartidoWithExistingId() throws Exception {
        // Create the Partido with an existing ID
        partido.setId(1L);
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        partido.setFecha(null);

        // Create the Partido, which fails.
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        restPartidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOponenteIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        partido.setOponente(null);

        // Create the Partido, which fails.
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        restPartidoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPartidos() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.saveAndFlush(partido);

        // Get all the partidoList
        restPartidoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partido.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].oponente").value(hasItem(DEFAULT_OPONENTE)))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO)));
    }

    @Test
    @Transactional
    void getPartido() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.saveAndFlush(partido);

        // Get the partido
        restPartidoMockMvc
            .perform(get(ENTITY_API_URL_ID, partido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partido.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.oponente").value(DEFAULT_OPONENTE))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO));
    }

    @Test
    @Transactional
    void getNonExistingPartido() throws Exception {
        // Get the partido
        restPartidoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPartido() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.saveAndFlush(partido);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partido
        Partido updatedPartido = partidoRepository.findById(partido.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPartido are not directly saved in db
        em.detach(updatedPartido);
        updatedPartido.fecha(UPDATED_FECHA).oponente(UPDATED_OPONENTE).resultado(UPDATED_RESULTADO);
        PartidoDTO partidoDTO = partidoMapper.toDto(updatedPartido);

        restPartidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partidoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPartidoToMatchAllProperties(updatedPartido);
    }

    @Test
    @Transactional
    void putNonExistingPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(longCount.incrementAndGet());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, partidoDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(longCount.incrementAndGet());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(partidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(longCount.incrementAndGet());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePartidoWithPatch() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.saveAndFlush(partido);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partido using partial update
        Partido partialUpdatedPartido = new Partido();
        partialUpdatedPartido.setId(partido.getId());

        partialUpdatedPartido.fecha(UPDATED_FECHA);

        restPartidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartido.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartido))
            )
            .andExpect(status().isOk());

        // Validate the Partido in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartidoUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPartido, partido), getPersistedPartido(partido));
    }

    @Test
    @Transactional
    void fullUpdatePartidoWithPatch() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.saveAndFlush(partido);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the partido using partial update
        Partido partialUpdatedPartido = new Partido();
        partialUpdatedPartido.setId(partido.getId());

        partialUpdatedPartido.fecha(UPDATED_FECHA).oponente(UPDATED_OPONENTE).resultado(UPDATED_RESULTADO);

        restPartidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPartido.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPartido))
            )
            .andExpect(status().isOk());

        // Validate the Partido in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPartidoUpdatableFieldsEquals(partialUpdatedPartido, getPersistedPartido(partialUpdatedPartido));
    }

    @Test
    @Transactional
    void patchNonExistingPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(longCount.incrementAndGet());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partidoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(longCount.incrementAndGet());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partidoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPartido() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        partido.setId(longCount.incrementAndGet());

        // Create the Partido
        PartidoDTO partidoDTO = partidoMapper.toDto(partido);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPartidoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(partidoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Partido in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePartido() throws Exception {
        // Initialize the database
        insertedPartido = partidoRepository.saveAndFlush(partido);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the partido
        restPartidoMockMvc
            .perform(delete(ENTITY_API_URL_ID, partido.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return partidoRepository.count();
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

    protected Partido getPersistedPartido(Partido partido) {
        return partidoRepository.findById(partido.getId()).orElseThrow();
    }

    protected void assertPersistedPartidoToMatchAllProperties(Partido expectedPartido) {
        assertPartidoAllPropertiesEquals(expectedPartido, getPersistedPartido(expectedPartido));
    }

    protected void assertPersistedPartidoToMatchUpdatableProperties(Partido expectedPartido) {
        assertPartidoAllUpdatablePropertiesEquals(expectedPartido, getPersistedPartido(expectedPartido));
    }
}
