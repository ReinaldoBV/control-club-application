package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.AsociadosAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Asociados;
import cl.controlclub.myapp.repository.AsociadosRepository;
import cl.controlclub.myapp.service.dto.AsociadosDTO;
import cl.controlclub.myapp.service.mapper.AsociadosMapper;
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
 * Integration tests for the {@link AsociadosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AsociadosResourceIT {

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ASOC = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ASOC = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/asociados";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AsociadosRepository asociadosRepository;

    @Autowired
    private AsociadosMapper asociadosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAsociadosMockMvc;

    private Asociados asociados;

    private Asociados insertedAsociados;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asociados createEntity(EntityManager em) {
        Asociados asociados = new Asociados()
            .nombres(DEFAULT_NOMBRES)
            .apellidos(DEFAULT_APELLIDOS)
            .cargo(DEFAULT_CARGO)
            .telefono(DEFAULT_TELEFONO)
            .fechaAsoc(DEFAULT_FECHA_ASOC)
            .email(DEFAULT_EMAIL);
        return asociados;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Asociados createUpdatedEntity(EntityManager em) {
        Asociados asociados = new Asociados()
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .cargo(UPDATED_CARGO)
            .telefono(UPDATED_TELEFONO)
            .fechaAsoc(UPDATED_FECHA_ASOC)
            .email(UPDATED_EMAIL);
        return asociados;
    }

    @BeforeEach
    public void initTest() {
        asociados = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAsociados != null) {
            asociadosRepository.delete(insertedAsociados);
            insertedAsociados = null;
        }
    }

    @Test
    @Transactional
    void createAsociados() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Asociados
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);
        var returnedAsociadosDTO = om.readValue(
            restAsociadosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asociadosDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AsociadosDTO.class
        );

        // Validate the Asociados in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAsociados = asociadosMapper.toEntity(returnedAsociadosDTO);
        assertAsociadosUpdatableFieldsEquals(returnedAsociados, getPersistedAsociados(returnedAsociados));

        insertedAsociados = returnedAsociados;
    }

    @Test
    @Transactional
    void createAsociadosWithExistingId() throws Exception {
        // Create the Asociados with an existing ID
        asociados.setId(1L);
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAsociadosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asociadosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Asociados in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombresIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asociados.setNombres(null);

        // Create the Asociados, which fails.
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        restAsociadosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asociadosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asociados.setApellidos(null);

        // Create the Asociados, which fails.
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        restAsociadosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asociadosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCargoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asociados.setCargo(null);

        // Create the Asociados, which fails.
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        restAsociadosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asociadosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asociados.setTelefono(null);

        // Create the Asociados, which fails.
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        restAsociadosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asociadosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaAsocIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asociados.setFechaAsoc(null);

        // Create the Asociados, which fails.
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        restAsociadosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asociadosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        asociados.setEmail(null);

        // Create the Asociados, which fails.
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        restAsociadosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asociadosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAsociados() throws Exception {
        // Initialize the database
        insertedAsociados = asociadosRepository.saveAndFlush(asociados);

        // Get all the asociadosList
        restAsociadosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asociados.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaAsoc").value(hasItem(DEFAULT_FECHA_ASOC.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getAsociados() throws Exception {
        // Initialize the database
        insertedAsociados = asociadosRepository.saveAndFlush(asociados);

        // Get the asociados
        restAsociadosMockMvc
            .perform(get(ENTITY_API_URL_ID, asociados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(asociados.getId().intValue()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.fechaAsoc").value(DEFAULT_FECHA_ASOC.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingAsociados() throws Exception {
        // Get the asociados
        restAsociadosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAsociados() throws Exception {
        // Initialize the database
        insertedAsociados = asociadosRepository.saveAndFlush(asociados);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asociados
        Asociados updatedAsociados = asociadosRepository.findById(asociados.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAsociados are not directly saved in db
        em.detach(updatedAsociados);
        updatedAsociados
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .cargo(UPDATED_CARGO)
            .telefono(UPDATED_TELEFONO)
            .fechaAsoc(UPDATED_FECHA_ASOC)
            .email(UPDATED_EMAIL);
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(updatedAsociados);

        restAsociadosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, asociadosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(asociadosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Asociados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAsociadosToMatchAllProperties(updatedAsociados);
    }

    @Test
    @Transactional
    void putNonExistingAsociados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asociados.setId(longCount.incrementAndGet());

        // Create the Asociados
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsociadosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, asociadosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(asociadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asociados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAsociados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asociados.setId(longCount.incrementAndGet());

        // Create the Asociados
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(asociadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asociados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAsociados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asociados.setId(longCount.incrementAndGet());

        // Create the Asociados
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(asociadosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asociados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAsociadosWithPatch() throws Exception {
        // Initialize the database
        insertedAsociados = asociadosRepository.saveAndFlush(asociados);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asociados using partial update
        Asociados partialUpdatedAsociados = new Asociados();
        partialUpdatedAsociados.setId(asociados.getId());

        partialUpdatedAsociados.nombres(UPDATED_NOMBRES).apellidos(UPDATED_APELLIDOS).telefono(UPDATED_TELEFONO);

        restAsociadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsociados.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsociados))
            )
            .andExpect(status().isOk());

        // Validate the Asociados in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAsociadosUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAsociados, asociados),
            getPersistedAsociados(asociados)
        );
    }

    @Test
    @Transactional
    void fullUpdateAsociadosWithPatch() throws Exception {
        // Initialize the database
        insertedAsociados = asociadosRepository.saveAndFlush(asociados);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the asociados using partial update
        Asociados partialUpdatedAsociados = new Asociados();
        partialUpdatedAsociados.setId(asociados.getId());

        partialUpdatedAsociados
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .cargo(UPDATED_CARGO)
            .telefono(UPDATED_TELEFONO)
            .fechaAsoc(UPDATED_FECHA_ASOC)
            .email(UPDATED_EMAIL);

        restAsociadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAsociados.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAsociados))
            )
            .andExpect(status().isOk());

        // Validate the Asociados in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAsociadosUpdatableFieldsEquals(partialUpdatedAsociados, getPersistedAsociados(partialUpdatedAsociados));
    }

    @Test
    @Transactional
    void patchNonExistingAsociados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asociados.setId(longCount.incrementAndGet());

        // Create the Asociados
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAsociadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, asociadosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(asociadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asociados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAsociados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asociados.setId(longCount.incrementAndGet());

        // Create the Asociados
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(asociadosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Asociados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAsociados() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        asociados.setId(longCount.incrementAndGet());

        // Create the Asociados
        AsociadosDTO asociadosDTO = asociadosMapper.toDto(asociados);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAsociadosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(asociadosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Asociados in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAsociados() throws Exception {
        // Initialize the database
        insertedAsociados = asociadosRepository.saveAndFlush(asociados);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the asociados
        restAsociadosMockMvc
            .perform(delete(ENTITY_API_URL_ID, asociados.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return asociadosRepository.count();
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

    protected Asociados getPersistedAsociados(Asociados asociados) {
        return asociadosRepository.findById(asociados.getId()).orElseThrow();
    }

    protected void assertPersistedAsociadosToMatchAllProperties(Asociados expectedAsociados) {
        assertAsociadosAllPropertiesEquals(expectedAsociados, getPersistedAsociados(expectedAsociados));
    }

    protected void assertPersistedAsociadosToMatchUpdatableProperties(Asociados expectedAsociados) {
        assertAsociadosAllUpdatablePropertiesEquals(expectedAsociados, getPersistedAsociados(expectedAsociados));
    }
}
