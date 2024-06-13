package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.PadreAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Padre;
import cl.controlclub.myapp.repository.PadreRepository;
import cl.controlclub.myapp.service.dto.PadreDTO;
import cl.controlclub.myapp.service.mapper.PadreMapper;
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
 * Integration tests for the {@link PadreResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PadreResourceIT {

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_RELACION = "AAAAAAAAAA";
    private static final String UPDATED_RELACION = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ASOCIADO = false;
    private static final Boolean UPDATED_ASOCIADO = true;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/padres";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PadreRepository padreRepository;

    @Autowired
    private PadreMapper padreMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPadreMockMvc;

    private Padre padre;

    private Padre insertedPadre;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Padre createEntity(EntityManager em) {
        Padre padre = new Padre()
            .nombres(DEFAULT_NOMBRES)
            .apellidos(DEFAULT_APELLIDOS)
            .relacion(DEFAULT_RELACION)
            .telefono(DEFAULT_TELEFONO)
            .asociado(DEFAULT_ASOCIADO)
            .email(DEFAULT_EMAIL);
        return padre;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Padre createUpdatedEntity(EntityManager em) {
        Padre padre = new Padre()
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .relacion(UPDATED_RELACION)
            .telefono(UPDATED_TELEFONO)
            .asociado(UPDATED_ASOCIADO)
            .email(UPDATED_EMAIL);
        return padre;
    }

    @BeforeEach
    public void initTest() {
        padre = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedPadre != null) {
            padreRepository.delete(insertedPadre);
            insertedPadre = null;
        }
    }

    @Test
    @Transactional
    void createPadre() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Padre
        PadreDTO padreDTO = padreMapper.toDto(padre);
        var returnedPadreDTO = om.readValue(
            restPadreMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(padreDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PadreDTO.class
        );

        // Validate the Padre in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPadre = padreMapper.toEntity(returnedPadreDTO);
        assertPadreUpdatableFieldsEquals(returnedPadre, getPersistedPadre(returnedPadre));

        insertedPadre = returnedPadre;
    }

    @Test
    @Transactional
    void createPadreWithExistingId() throws Exception {
        // Create the Padre with an existing ID
        padre.setId(1L);
        PadreDTO padreDTO = padreMapper.toDto(padre);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPadreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(padreDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Padre in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombresIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        padre.setNombres(null);

        // Create the Padre, which fails.
        PadreDTO padreDTO = padreMapper.toDto(padre);

        restPadreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(padreDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkApellidosIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        padre.setApellidos(null);

        // Create the Padre, which fails.
        PadreDTO padreDTO = padreMapper.toDto(padre);

        restPadreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(padreDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRelacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        padre.setRelacion(null);

        // Create the Padre, which fails.
        PadreDTO padreDTO = padreMapper.toDto(padre);

        restPadreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(padreDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTelefonoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        padre.setTelefono(null);

        // Create the Padre, which fails.
        PadreDTO padreDTO = padreMapper.toDto(padre);

        restPadreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(padreDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAsociadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        padre.setAsociado(null);

        // Create the Padre, which fails.
        PadreDTO padreDTO = padreMapper.toDto(padre);

        restPadreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(padreDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        padre.setEmail(null);

        // Create the Padre, which fails.
        PadreDTO padreDTO = padreMapper.toDto(padre);

        restPadreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(padreDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPadres() throws Exception {
        // Initialize the database
        insertedPadre = padreRepository.saveAndFlush(padre);

        // Get all the padreList
        restPadreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(padre.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].relacion").value(hasItem(DEFAULT_RELACION)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].asociado").value(hasItem(DEFAULT_ASOCIADO.booleanValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }

    @Test
    @Transactional
    void getPadre() throws Exception {
        // Initialize the database
        insertedPadre = padreRepository.saveAndFlush(padre);

        // Get the padre
        restPadreMockMvc
            .perform(get(ENTITY_API_URL_ID, padre.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(padre.getId().intValue()))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.relacion").value(DEFAULT_RELACION))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.asociado").value(DEFAULT_ASOCIADO.booleanValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }

    @Test
    @Transactional
    void getNonExistingPadre() throws Exception {
        // Get the padre
        restPadreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPadre() throws Exception {
        // Initialize the database
        insertedPadre = padreRepository.saveAndFlush(padre);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the padre
        Padre updatedPadre = padreRepository.findById(padre.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPadre are not directly saved in db
        em.detach(updatedPadre);
        updatedPadre
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .relacion(UPDATED_RELACION)
            .telefono(UPDATED_TELEFONO)
            .asociado(UPDATED_ASOCIADO)
            .email(UPDATED_EMAIL);
        PadreDTO padreDTO = padreMapper.toDto(updatedPadre);

        restPadreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, padreDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(padreDTO))
            )
            .andExpect(status().isOk());

        // Validate the Padre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPadreToMatchAllProperties(updatedPadre);
    }

    @Test
    @Transactional
    void putNonExistingPadre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        padre.setId(longCount.incrementAndGet());

        // Create the Padre
        PadreDTO padreDTO = padreMapper.toDto(padre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPadreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, padreDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(padreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Padre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPadre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        padre.setId(longCount.incrementAndGet());

        // Create the Padre
        PadreDTO padreDTO = padreMapper.toDto(padre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPadreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(padreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Padre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPadre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        padre.setId(longCount.incrementAndGet());

        // Create the Padre
        PadreDTO padreDTO = padreMapper.toDto(padre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPadreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(padreDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Padre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePadreWithPatch() throws Exception {
        // Initialize the database
        insertedPadre = padreRepository.saveAndFlush(padre);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the padre using partial update
        Padre partialUpdatedPadre = new Padre();
        partialUpdatedPadre.setId(padre.getId());

        partialUpdatedPadre.telefono(UPDATED_TELEFONO).asociado(UPDATED_ASOCIADO);

        restPadreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPadre.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPadre))
            )
            .andExpect(status().isOk());

        // Validate the Padre in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPadreUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPadre, padre), getPersistedPadre(padre));
    }

    @Test
    @Transactional
    void fullUpdatePadreWithPatch() throws Exception {
        // Initialize the database
        insertedPadre = padreRepository.saveAndFlush(padre);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the padre using partial update
        Padre partialUpdatedPadre = new Padre();
        partialUpdatedPadre.setId(padre.getId());

        partialUpdatedPadre
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .relacion(UPDATED_RELACION)
            .telefono(UPDATED_TELEFONO)
            .asociado(UPDATED_ASOCIADO)
            .email(UPDATED_EMAIL);

        restPadreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPadre.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPadre))
            )
            .andExpect(status().isOk());

        // Validate the Padre in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPadreUpdatableFieldsEquals(partialUpdatedPadre, getPersistedPadre(partialUpdatedPadre));
    }

    @Test
    @Transactional
    void patchNonExistingPadre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        padre.setId(longCount.incrementAndGet());

        // Create the Padre
        PadreDTO padreDTO = padreMapper.toDto(padre);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPadreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, padreDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(padreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Padre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPadre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        padre.setId(longCount.incrementAndGet());

        // Create the Padre
        PadreDTO padreDTO = padreMapper.toDto(padre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPadreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(padreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Padre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPadre() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        padre.setId(longCount.incrementAndGet());

        // Create the Padre
        PadreDTO padreDTO = padreMapper.toDto(padre);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPadreMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(padreDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Padre in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePadre() throws Exception {
        // Initialize the database
        insertedPadre = padreRepository.saveAndFlush(padre);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the padre
        restPadreMockMvc
            .perform(delete(ENTITY_API_URL_ID, padre.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return padreRepository.count();
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

    protected Padre getPersistedPadre(Padre padre) {
        return padreRepository.findById(padre.getId()).orElseThrow();
    }

    protected void assertPersistedPadreToMatchAllProperties(Padre expectedPadre) {
        assertPadreAllPropertiesEquals(expectedPadre, getPersistedPadre(expectedPadre));
    }

    protected void assertPersistedPadreToMatchUpdatableProperties(Padre expectedPadre) {
        assertPadreAllUpdatablePropertiesEquals(expectedPadre, getPersistedPadre(expectedPadre));
    }
}
