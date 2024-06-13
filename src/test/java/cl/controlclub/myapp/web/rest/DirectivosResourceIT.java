package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.DirectivosAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Directivos;
import cl.controlclub.myapp.repository.DirectivosRepository;
import cl.controlclub.myapp.service.dto.DirectivosDTO;
import cl.controlclub.myapp.service.mapper.DirectivosMapper;
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
 * Integration tests for the {@link DirectivosResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DirectivosResourceIT {

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_CARGO = "AAAAAAAAAA";
    private static final String UPDATED_CARGO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_ELECCION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_ELECCION = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_FECHA_VENCIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENCIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/directivos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DirectivosRepository directivosRepository;

    @Autowired
    private DirectivosMapper directivosMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDirectivosMockMvc;

    private Directivos directivos;

    private Directivos insertedDirectivos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Directivos createEntity(EntityManager em) {
        Directivos directivos = new Directivos()
            .nombres(DEFAULT_NOMBRES)
            .apellidos(DEFAULT_APELLIDOS)
            .cargo(DEFAULT_CARGO)
            .telefono(DEFAULT_TELEFONO)
            .fechaEleccion(DEFAULT_FECHA_ELECCION)
            .fechaVencimiento(DEFAULT_FECHA_VENCIMIENTO)
            .email(DEFAULT_EMAIL);
        return directivos;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Directivos createUpdatedEntity(EntityManager em) {
        Directivos directivos = new Directivos()
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .cargo(UPDATED_CARGO)
            .telefono(UPDATED_TELEFONO)
            .fechaEleccion(UPDATED_FECHA_ELECCION)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .email(UPDATED_EMAIL);
        return directivos;
    }

    @BeforeEach
    public void initTest() {
        directivos = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedDirectivos != null) {
            directivosRepository.delete(insertedDirectivos);
            insertedDirectivos = null;
        }
    }

    @Test
    @Transactional
    void createDirectivos() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Directivos
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);
        var returnedDirectivosDTO = om.readValue(
            restDirectivosMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(directivosDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DirectivosDTO.class
        );

        // Validate the Directivos in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDirectivos = directivosMapper.toEntity(returnedDirectivosDTO);
        assertDirectivosUpdatableFieldsEquals(returnedDirectivos, getPersistedDirectivos(returnedDirectivos));

        insertedDirectivos = returnedDirectivos;
    }

    @Test
    @Transactional
    void createDirectivosWithExistingId() throws Exception {
        // Create the Directivos with an existing ID
        directivos.setId(1L);
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDirectivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(directivosDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Directivos in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombresIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        directivos.setNombres(null);

        // Create the Directivos, which fails.
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        restDirectivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(directivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        directivos.setApellidos(null);

        // Create the Directivos, which fails.
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        restDirectivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(directivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCargoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        directivos.setCargo(null);

        // Create the Directivos, which fails.
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        restDirectivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(directivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        directivos.setTelefono(null);

        // Create the Directivos, which fails.
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        restDirectivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(directivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaEleccionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        directivos.setFechaEleccion(null);

        // Create the Directivos, which fails.
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        restDirectivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(directivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaVencimientoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        directivos.setFechaVencimiento(null);

        // Create the Directivos, which fails.
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        restDirectivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(directivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        directivos.setEmail(null);

        // Create the Directivos, which fails.
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        restDirectivosMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(directivosDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDirectivos() throws Exception {
        // Initialize the database
        insertedDirectivos = directivosRepository.saveAndFlush(directivos);

        // Get all the directivosList
        restDirectivosMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(directivos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaEleccion").value(hasItem(DEFAULT_FECHA_ELECCION.toString())))
            .andExpect(jsonPath("$.[*].fechaVencimiento").value(hasItem(DEFAULT_FECHA_VENCIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getDirectivos() throws Exception {
        // Initialize the database
        insertedDirectivos = directivosRepository.saveAndFlush(directivos);

        // Get the directivos
        restDirectivosMockMvc
            .perform(get(ENTITY_API_URL_ID, directivos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(directivos.getId().intValue()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.fechaEleccion").value(DEFAULT_FECHA_ELECCION.toString()))
            .andExpect(jsonPath("$.fechaVencimiento").value(DEFAULT_FECHA_VENCIMIENTO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingDirectivos() throws Exception {
        // Get the directivos
        restDirectivosMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDirectivos() throws Exception {
        // Initialize the database
        insertedDirectivos = directivosRepository.saveAndFlush(directivos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the directivos
        Directivos updatedDirectivos = directivosRepository.findById(directivos.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDirectivos are not directly saved in db
        em.detach(updatedDirectivos);
        updatedDirectivos
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .cargo(UPDATED_CARGO)
            .telefono(UPDATED_TELEFONO)
            .fechaEleccion(UPDATED_FECHA_ELECCION)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .email(UPDATED_EMAIL);
        DirectivosDTO directivosDTO = directivosMapper.toDto(updatedDirectivos);

        restDirectivosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, directivosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(directivosDTO))
            )
            .andExpect(status().isOk());

        // Validate the Directivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDirectivosToMatchAllProperties(updatedDirectivos);
    }

    @Test
    @Transactional
    void putNonExistingDirectivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        directivos.setId(longCount.incrementAndGet());

        // Create the Directivos
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDirectivosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, directivosDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(directivosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Directivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDirectivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        directivos.setId(longCount.incrementAndGet());

        // Create the Directivos
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectivosMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(directivosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Directivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDirectivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        directivos.setId(longCount.incrementAndGet());

        // Create the Directivos
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectivosMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(directivosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Directivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDirectivosWithPatch() throws Exception {
        // Initialize the database
        insertedDirectivos = directivosRepository.saveAndFlush(directivos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the directivos using partial update
        Directivos partialUpdatedDirectivos = new Directivos();
        partialUpdatedDirectivos.setId(directivos.getId());

        partialUpdatedDirectivos
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .telefono(UPDATED_TELEFONO)
            .fechaEleccion(UPDATED_FECHA_ELECCION)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO);

        restDirectivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDirectivos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDirectivos))
            )
            .andExpect(status().isOk());

        // Validate the Directivos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDirectivosUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDirectivos, directivos),
            getPersistedDirectivos(directivos)
        );
    }

    @Test
    @Transactional
    void fullUpdateDirectivosWithPatch() throws Exception {
        // Initialize the database
        insertedDirectivos = directivosRepository.saveAndFlush(directivos);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the directivos using partial update
        Directivos partialUpdatedDirectivos = new Directivos();
        partialUpdatedDirectivos.setId(directivos.getId());

        partialUpdatedDirectivos
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .cargo(UPDATED_CARGO)
            .telefono(UPDATED_TELEFONO)
            .fechaEleccion(UPDATED_FECHA_ELECCION)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .email(UPDATED_EMAIL);

        restDirectivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDirectivos.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDirectivos))
            )
            .andExpect(status().isOk());

        // Validate the Directivos in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDirectivosUpdatableFieldsEquals(partialUpdatedDirectivos, getPersistedDirectivos(partialUpdatedDirectivos));
    }

    @Test
    @Transactional
    void patchNonExistingDirectivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        directivos.setId(longCount.incrementAndGet());

        // Create the Directivos
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDirectivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, directivosDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(directivosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Directivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDirectivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        directivos.setId(longCount.incrementAndGet());

        // Create the Directivos
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectivosMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(directivosDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Directivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDirectivos() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        directivos.setId(longCount.incrementAndGet());

        // Create the Directivos
        DirectivosDTO directivosDTO = directivosMapper.toDto(directivos);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDirectivosMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(directivosDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Directivos in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDirectivos() throws Exception {
        // Initialize the database
        insertedDirectivos = directivosRepository.saveAndFlush(directivos);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the directivos
        restDirectivosMockMvc
            .perform(delete(ENTITY_API_URL_ID, directivos.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return directivosRepository.count();
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

    protected Directivos getPersistedDirectivos(Directivos directivos) {
        return directivosRepository.findById(directivos.getId()).orElseThrow();
    }

    protected void assertPersistedDirectivosToMatchAllProperties(Directivos expectedDirectivos) {
        assertDirectivosAllPropertiesEquals(expectedDirectivos, getPersistedDirectivos(expectedDirectivos));
    }

    protected void assertPersistedDirectivosToMatchUpdatableProperties(Directivos expectedDirectivos) {
        assertDirectivosAllUpdatablePropertiesEquals(expectedDirectivos, getPersistedDirectivos(expectedDirectivos));
    }
}
