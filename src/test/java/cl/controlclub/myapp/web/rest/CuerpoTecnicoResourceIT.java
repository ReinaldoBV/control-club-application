package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.CuerpoTecnicoAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.CuerpoTecnico;
import cl.controlclub.myapp.repository.CuerpoTecnicoRepository;
import cl.controlclub.myapp.service.dto.CuerpoTecnicoDTO;
import cl.controlclub.myapp.service.mapper.CuerpoTecnicoMapper;
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
 * Integration tests for the {@link CuerpoTecnicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CuerpoTecnicoResourceIT {

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_ROL_TECNICO = "AAAAAAAAAA";
    private static final String UPDATED_ROL_TECNICO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_INICIO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_INICIO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cuerpo-tecnicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CuerpoTecnicoRepository cuerpoTecnicoRepository;

    @Autowired
    private CuerpoTecnicoMapper cuerpoTecnicoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCuerpoTecnicoMockMvc;

    private CuerpoTecnico cuerpoTecnico;

    private CuerpoTecnico insertedCuerpoTecnico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CuerpoTecnico createEntity(EntityManager em) {
        CuerpoTecnico cuerpoTecnico = new CuerpoTecnico()
            .nombres(DEFAULT_NOMBRES)
            .apellidos(DEFAULT_APELLIDOS)
            .rolTecnico(DEFAULT_ROL_TECNICO)
            .telefono(DEFAULT_TELEFONO)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .email(DEFAULT_EMAIL);
        return cuerpoTecnico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CuerpoTecnico createUpdatedEntity(EntityManager em) {
        CuerpoTecnico cuerpoTecnico = new CuerpoTecnico()
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .rolTecnico(UPDATED_ROL_TECNICO)
            .telefono(UPDATED_TELEFONO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .email(UPDATED_EMAIL);
        return cuerpoTecnico;
    }

    @BeforeEach
    public void initTest() {
        cuerpoTecnico = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCuerpoTecnico != null) {
            cuerpoTecnicoRepository.delete(insertedCuerpoTecnico);
            insertedCuerpoTecnico = null;
        }
    }

    @Test
    @Transactional
    void createCuerpoTecnico() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CuerpoTecnico
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);
        var returnedCuerpoTecnicoDTO = om.readValue(
            restCuerpoTecnicoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuerpoTecnicoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CuerpoTecnicoDTO.class
        );

        // Validate the CuerpoTecnico in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCuerpoTecnico = cuerpoTecnicoMapper.toEntity(returnedCuerpoTecnicoDTO);
        assertCuerpoTecnicoUpdatableFieldsEquals(returnedCuerpoTecnico, getPersistedCuerpoTecnico(returnedCuerpoTecnico));

        insertedCuerpoTecnico = returnedCuerpoTecnico;
    }

    @Test
    @Transactional
    void createCuerpoTecnicoWithExistingId() throws Exception {
        // Create the CuerpoTecnico with an existing ID
        cuerpoTecnico.setId(1L);
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuerpoTecnicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuerpoTecnicoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CuerpoTecnico in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombresIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuerpoTecnico.setNombres(null);

        // Create the CuerpoTecnico, which fails.
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        restCuerpoTecnicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuerpoTecnicoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuerpoTecnico.setApellidos(null);

        // Create the CuerpoTecnico, which fails.
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        restCuerpoTecnicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuerpoTecnicoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRolTecnicoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuerpoTecnico.setRolTecnico(null);

        // Create the CuerpoTecnico, which fails.
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        restCuerpoTecnicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuerpoTecnicoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuerpoTecnico.setTelefono(null);

        // Create the CuerpoTecnico, which fails.
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        restCuerpoTecnicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuerpoTecnicoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaInicioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuerpoTecnico.setFechaInicio(null);

        // Create the CuerpoTecnico, which fails.
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        restCuerpoTecnicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuerpoTecnicoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuerpoTecnico.setEmail(null);

        // Create the CuerpoTecnico, which fails.
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        restCuerpoTecnicoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuerpoTecnicoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCuerpoTecnicos() throws Exception {
        // Initialize the database
        insertedCuerpoTecnico = cuerpoTecnicoRepository.saveAndFlush(cuerpoTecnico);

        // Get all the cuerpoTecnicoList
        restCuerpoTecnicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuerpoTecnico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].rolTecnico").value(hasItem(DEFAULT_ROL_TECNICO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getCuerpoTecnico() throws Exception {
        // Initialize the database
        insertedCuerpoTecnico = cuerpoTecnicoRepository.saveAndFlush(cuerpoTecnico);

        // Get the cuerpoTecnico
        restCuerpoTecnicoMockMvc
            .perform(get(ENTITY_API_URL_ID, cuerpoTecnico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cuerpoTecnico.getId().intValue()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.rolTecnico").value(DEFAULT_ROL_TECNICO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingCuerpoTecnico() throws Exception {
        // Get the cuerpoTecnico
        restCuerpoTecnicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCuerpoTecnico() throws Exception {
        // Initialize the database
        insertedCuerpoTecnico = cuerpoTecnicoRepository.saveAndFlush(cuerpoTecnico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuerpoTecnico
        CuerpoTecnico updatedCuerpoTecnico = cuerpoTecnicoRepository.findById(cuerpoTecnico.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCuerpoTecnico are not directly saved in db
        em.detach(updatedCuerpoTecnico);
        updatedCuerpoTecnico
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .rolTecnico(UPDATED_ROL_TECNICO)
            .telefono(UPDATED_TELEFONO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .email(UPDATED_EMAIL);
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(updatedCuerpoTecnico);

        restCuerpoTecnicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cuerpoTecnicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cuerpoTecnicoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CuerpoTecnico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCuerpoTecnicoToMatchAllProperties(updatedCuerpoTecnico);
    }

    @Test
    @Transactional
    void putNonExistingCuerpoTecnico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuerpoTecnico.setId(longCount.incrementAndGet());

        // Create the CuerpoTecnico
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuerpoTecnicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cuerpoTecnicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cuerpoTecnicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CuerpoTecnico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCuerpoTecnico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuerpoTecnico.setId(longCount.incrementAndGet());

        // Create the CuerpoTecnico
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuerpoTecnicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cuerpoTecnicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CuerpoTecnico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCuerpoTecnico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuerpoTecnico.setId(longCount.incrementAndGet());

        // Create the CuerpoTecnico
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuerpoTecnicoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuerpoTecnicoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CuerpoTecnico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCuerpoTecnicoWithPatch() throws Exception {
        // Initialize the database
        insertedCuerpoTecnico = cuerpoTecnicoRepository.saveAndFlush(cuerpoTecnico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuerpoTecnico using partial update
        CuerpoTecnico partialUpdatedCuerpoTecnico = new CuerpoTecnico();
        partialUpdatedCuerpoTecnico.setId(cuerpoTecnico.getId());

        partialUpdatedCuerpoTecnico
            .apellidos(UPDATED_APELLIDOS)
            .rolTecnico(UPDATED_ROL_TECNICO)
            .telefono(UPDATED_TELEFONO)
            .fechaInicio(UPDATED_FECHA_INICIO);

        restCuerpoTecnicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuerpoTecnico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCuerpoTecnico))
            )
            .andExpect(status().isOk());

        // Validate the CuerpoTecnico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCuerpoTecnicoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCuerpoTecnico, cuerpoTecnico),
            getPersistedCuerpoTecnico(cuerpoTecnico)
        );
    }

    @Test
    @Transactional
    void fullUpdateCuerpoTecnicoWithPatch() throws Exception {
        // Initialize the database
        insertedCuerpoTecnico = cuerpoTecnicoRepository.saveAndFlush(cuerpoTecnico);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuerpoTecnico using partial update
        CuerpoTecnico partialUpdatedCuerpoTecnico = new CuerpoTecnico();
        partialUpdatedCuerpoTecnico.setId(cuerpoTecnico.getId());

        partialUpdatedCuerpoTecnico
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .rolTecnico(UPDATED_ROL_TECNICO)
            .telefono(UPDATED_TELEFONO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .email(UPDATED_EMAIL);

        restCuerpoTecnicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuerpoTecnico.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCuerpoTecnico))
            )
            .andExpect(status().isOk());

        // Validate the CuerpoTecnico in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCuerpoTecnicoUpdatableFieldsEquals(partialUpdatedCuerpoTecnico, getPersistedCuerpoTecnico(partialUpdatedCuerpoTecnico));
    }

    @Test
    @Transactional
    void patchNonExistingCuerpoTecnico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuerpoTecnico.setId(longCount.incrementAndGet());

        // Create the CuerpoTecnico
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuerpoTecnicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cuerpoTecnicoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cuerpoTecnicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CuerpoTecnico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCuerpoTecnico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuerpoTecnico.setId(longCount.incrementAndGet());

        // Create the CuerpoTecnico
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuerpoTecnicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cuerpoTecnicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CuerpoTecnico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCuerpoTecnico() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuerpoTecnico.setId(longCount.incrementAndGet());

        // Create the CuerpoTecnico
        CuerpoTecnicoDTO cuerpoTecnicoDTO = cuerpoTecnicoMapper.toDto(cuerpoTecnico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuerpoTecnicoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cuerpoTecnicoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CuerpoTecnico in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCuerpoTecnico() throws Exception {
        // Initialize the database
        insertedCuerpoTecnico = cuerpoTecnicoRepository.saveAndFlush(cuerpoTecnico);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cuerpoTecnico
        restCuerpoTecnicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, cuerpoTecnico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cuerpoTecnicoRepository.count();
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

    protected CuerpoTecnico getPersistedCuerpoTecnico(CuerpoTecnico cuerpoTecnico) {
        return cuerpoTecnicoRepository.findById(cuerpoTecnico.getId()).orElseThrow();
    }

    protected void assertPersistedCuerpoTecnicoToMatchAllProperties(CuerpoTecnico expectedCuerpoTecnico) {
        assertCuerpoTecnicoAllPropertiesEquals(expectedCuerpoTecnico, getPersistedCuerpoTecnico(expectedCuerpoTecnico));
    }

    protected void assertPersistedCuerpoTecnicoToMatchUpdatableProperties(CuerpoTecnico expectedCuerpoTecnico) {
        assertCuerpoTecnicoAllUpdatablePropertiesEquals(expectedCuerpoTecnico, getPersistedCuerpoTecnico(expectedCuerpoTecnico));
    }
}
