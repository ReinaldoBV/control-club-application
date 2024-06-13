package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.AnuncioAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Anuncio;
import cl.controlclub.myapp.repository.AnuncioRepository;
import cl.controlclub.myapp.service.dto.AnuncioDTO;
import cl.controlclub.myapp.service.mapper.AnuncioMapper;
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
 * Integration tests for the {@link AnuncioResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnuncioResourceIT {

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENIDO = "AAAAAAAAAA";
    private static final String UPDATED_CONTENIDO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_PUBLICACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_PUBLICACION = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/anuncios";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AnuncioRepository anuncioRepository;

    @Autowired
    private AnuncioMapper anuncioMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnuncioMockMvc;

    private Anuncio anuncio;

    private Anuncio insertedAnuncio;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anuncio createEntity(EntityManager em) {
        Anuncio anuncio = new Anuncio().titulo(DEFAULT_TITULO).contenido(DEFAULT_CONTENIDO).fechaPublicacion(DEFAULT_FECHA_PUBLICACION);
        return anuncio;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Anuncio createUpdatedEntity(EntityManager em) {
        Anuncio anuncio = new Anuncio().titulo(UPDATED_TITULO).contenido(UPDATED_CONTENIDO).fechaPublicacion(UPDATED_FECHA_PUBLICACION);
        return anuncio;
    }

    @BeforeEach
    public void initTest() {
        anuncio = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedAnuncio != null) {
            anuncioRepository.delete(insertedAnuncio);
            insertedAnuncio = null;
        }
    }

    @Test
    @Transactional
    void createAnuncio() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Anuncio
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(anuncio);
        var returnedAnuncioDTO = om.readValue(
            restAnuncioMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anuncioDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AnuncioDTO.class
        );

        // Validate the Anuncio in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedAnuncio = anuncioMapper.toEntity(returnedAnuncioDTO);
        assertAnuncioUpdatableFieldsEquals(returnedAnuncio, getPersistedAnuncio(returnedAnuncio));

        insertedAnuncio = returnedAnuncio;
    }

    @Test
    @Transactional
    void createAnuncioWithExistingId() throws Exception {
        // Create the Anuncio with an existing ID
        anuncio.setId(1L);
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(anuncio);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnuncioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anuncioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Anuncio in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTituloIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        anuncio.setTitulo(null);

        // Create the Anuncio, which fails.
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(anuncio);

        restAnuncioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anuncioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContenidoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        anuncio.setContenido(null);

        // Create the Anuncio, which fails.
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(anuncio);

        restAnuncioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anuncioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaPublicacionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        anuncio.setFechaPublicacion(null);

        // Create the Anuncio, which fails.
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(anuncio);

        restAnuncioMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anuncioDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAnuncios() throws Exception {
        // Initialize the database
        insertedAnuncio = anuncioRepository.saveAndFlush(anuncio);

        // Get all the anuncioList
        restAnuncioMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anuncio.getId().intValue())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO)))
            .andExpect(jsonPath("$.[*].contenido").value(hasItem(DEFAULT_CONTENIDO)))
            .andExpect(jsonPath("$.[*].fechaPublicacion").value(hasItem(DEFAULT_FECHA_PUBLICACION.toString())));
    }

    @Test
    @Transactional
    void getAnuncio() throws Exception {
        // Initialize the database
        insertedAnuncio = anuncioRepository.saveAndFlush(anuncio);

        // Get the anuncio
        restAnuncioMockMvc
            .perform(get(ENTITY_API_URL_ID, anuncio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(anuncio.getId().intValue()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO))
            .andExpect(jsonPath("$.contenido").value(DEFAULT_CONTENIDO))
            .andExpect(jsonPath("$.fechaPublicacion").value(DEFAULT_FECHA_PUBLICACION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAnuncio() throws Exception {
        // Get the anuncio
        restAnuncioMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAnuncio() throws Exception {
        // Initialize the database
        insertedAnuncio = anuncioRepository.saveAndFlush(anuncio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anuncio
        Anuncio updatedAnuncio = anuncioRepository.findById(anuncio.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAnuncio are not directly saved in db
        em.detach(updatedAnuncio);
        updatedAnuncio.titulo(UPDATED_TITULO).contenido(UPDATED_CONTENIDO).fechaPublicacion(UPDATED_FECHA_PUBLICACION);
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(updatedAnuncio);

        restAnuncioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anuncioDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anuncioDTO))
            )
            .andExpect(status().isOk());

        // Validate the Anuncio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAnuncioToMatchAllProperties(updatedAnuncio);
    }

    @Test
    @Transactional
    void putNonExistingAnuncio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncio.setId(longCount.incrementAndGet());

        // Create the Anuncio
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(anuncio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnuncioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, anuncioDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anuncioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anuncio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnuncio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncio.setId(longCount.incrementAndGet());

        // Create the Anuncio
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(anuncio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnuncioMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(anuncioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anuncio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnuncio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncio.setId(longCount.incrementAndGet());

        // Create the Anuncio
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(anuncio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnuncioMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(anuncioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anuncio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnuncioWithPatch() throws Exception {
        // Initialize the database
        insertedAnuncio = anuncioRepository.saveAndFlush(anuncio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anuncio using partial update
        Anuncio partialUpdatedAnuncio = new Anuncio();
        partialUpdatedAnuncio.setId(anuncio.getId());

        partialUpdatedAnuncio.contenido(UPDATED_CONTENIDO);

        restAnuncioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnuncio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnuncio))
            )
            .andExpect(status().isOk());

        // Validate the Anuncio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnuncioUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedAnuncio, anuncio), getPersistedAnuncio(anuncio));
    }

    @Test
    @Transactional
    void fullUpdateAnuncioWithPatch() throws Exception {
        // Initialize the database
        insertedAnuncio = anuncioRepository.saveAndFlush(anuncio);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the anuncio using partial update
        Anuncio partialUpdatedAnuncio = new Anuncio();
        partialUpdatedAnuncio.setId(anuncio.getId());

        partialUpdatedAnuncio.titulo(UPDATED_TITULO).contenido(UPDATED_CONTENIDO).fechaPublicacion(UPDATED_FECHA_PUBLICACION);

        restAnuncioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnuncio.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAnuncio))
            )
            .andExpect(status().isOk());

        // Validate the Anuncio in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAnuncioUpdatableFieldsEquals(partialUpdatedAnuncio, getPersistedAnuncio(partialUpdatedAnuncio));
    }

    @Test
    @Transactional
    void patchNonExistingAnuncio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncio.setId(longCount.incrementAndGet());

        // Create the Anuncio
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(anuncio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnuncioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, anuncioDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anuncioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anuncio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnuncio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncio.setId(longCount.incrementAndGet());

        // Create the Anuncio
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(anuncio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnuncioMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(anuncioDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Anuncio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnuncio() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        anuncio.setId(longCount.incrementAndGet());

        // Create the Anuncio
        AnuncioDTO anuncioDTO = anuncioMapper.toDto(anuncio);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnuncioMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(anuncioDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Anuncio in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnuncio() throws Exception {
        // Initialize the database
        insertedAnuncio = anuncioRepository.saveAndFlush(anuncio);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the anuncio
        restAnuncioMockMvc
            .perform(delete(ENTITY_API_URL_ID, anuncio.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return anuncioRepository.count();
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

    protected Anuncio getPersistedAnuncio(Anuncio anuncio) {
        return anuncioRepository.findById(anuncio.getId()).orElseThrow();
    }

    protected void assertPersistedAnuncioToMatchAllProperties(Anuncio expectedAnuncio) {
        assertAnuncioAllPropertiesEquals(expectedAnuncio, getPersistedAnuncio(expectedAnuncio));
    }

    protected void assertPersistedAnuncioToMatchUpdatableProperties(Anuncio expectedAnuncio) {
        assertAnuncioAllUpdatablePropertiesEquals(expectedAnuncio, getPersistedAnuncio(expectedAnuncio));
    }
}
