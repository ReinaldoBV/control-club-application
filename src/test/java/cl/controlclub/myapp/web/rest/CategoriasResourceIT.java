package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.CategoriasAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Categorias;
import cl.controlclub.myapp.repository.CategoriasRepository;
import cl.controlclub.myapp.service.dto.CategoriasDTO;
import cl.controlclub.myapp.service.mapper.CategoriasMapper;
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
 * Integration tests for the {@link CategoriasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategoriasResourceIT {

    private static final Long DEFAULT_ANIO_INICIO = 1L;
    private static final Long UPDATED_ANIO_INICIO = 2L;

    private static final Long DEFAULT_ANIO_FINAL = 1L;
    private static final Long UPDATED_ANIO_FINAL = 2L;

    private static final String DEFAULT_NOMBRE_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CATEGORIA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categorias";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CategoriasRepository categoriasRepository;

    @Autowired
    private CategoriasMapper categoriasMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategoriasMockMvc;

    private Categorias categorias;

    private Categorias insertedCategorias;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorias createEntity(EntityManager em) {
        Categorias categorias = new Categorias()
            .anioInicio(DEFAULT_ANIO_INICIO)
            .anioFinal(DEFAULT_ANIO_FINAL)
            .nombreCategoria(DEFAULT_NOMBRE_CATEGORIA);
        return categorias;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Categorias createUpdatedEntity(EntityManager em) {
        Categorias categorias = new Categorias()
            .anioInicio(UPDATED_ANIO_INICIO)
            .anioFinal(UPDATED_ANIO_FINAL)
            .nombreCategoria(UPDATED_NOMBRE_CATEGORIA);
        return categorias;
    }

    @BeforeEach
    public void initTest() {
        categorias = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCategorias != null) {
            categoriasRepository.delete(insertedCategorias);
            insertedCategorias = null;
        }
    }

    @Test
    @Transactional
    void createCategorias() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Categorias
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(categorias);
        var returnedCategoriasDTO = om.readValue(
            restCategoriasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriasDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CategoriasDTO.class
        );

        // Validate the Categorias in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCategorias = categoriasMapper.toEntity(returnedCategoriasDTO);
        assertCategoriasUpdatableFieldsEquals(returnedCategorias, getPersistedCategorias(returnedCategorias));

        insertedCategorias = returnedCategorias;
    }

    @Test
    @Transactional
    void createCategoriasWithExistingId() throws Exception {
        // Create the Categorias with an existing ID
        categorias.setId(1L);
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(categorias);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategoriasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Categorias in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAnioInicioIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        categorias.setAnioInicio(null);

        // Create the Categorias, which fails.
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(categorias);

        restCategoriasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAnioFinalIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        categorias.setAnioFinal(null);

        // Create the Categorias, which fails.
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(categorias);

        restCategoriasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreCategoriaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        categorias.setNombreCategoria(null);

        // Create the Categorias, which fails.
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(categorias);

        restCategoriasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCategorias() throws Exception {
        // Initialize the database
        insertedCategorias = categoriasRepository.saveAndFlush(categorias);

        // Get all the categoriasList
        restCategoriasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorias.getId().intValue())))
            .andExpect(jsonPath("$.[*].anioInicio").value(hasItem(DEFAULT_ANIO_INICIO.intValue())))
            .andExpect(jsonPath("$.[*].anioFinal").value(hasItem(DEFAULT_ANIO_FINAL.intValue())))
            .andExpect(jsonPath("$.[*].nombreCategoria").value(hasItem(DEFAULT_NOMBRE_CATEGORIA)));
    }

    @Test
    @Transactional
    void getCategorias() throws Exception {
        // Initialize the database
        insertedCategorias = categoriasRepository.saveAndFlush(categorias);

        // Get the categorias
        restCategoriasMockMvc
            .perform(get(ENTITY_API_URL_ID, categorias.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorias.getId().intValue()))
            .andExpect(jsonPath("$.anioInicio").value(DEFAULT_ANIO_INICIO.intValue()))
            .andExpect(jsonPath("$.anioFinal").value(DEFAULT_ANIO_FINAL.intValue()))
            .andExpect(jsonPath("$.nombreCategoria").value(DEFAULT_NOMBRE_CATEGORIA));
    }

    @Test
    @Transactional
    void getNonExistingCategorias() throws Exception {
        // Get the categorias
        restCategoriasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCategorias() throws Exception {
        // Initialize the database
        insertedCategorias = categoriasRepository.saveAndFlush(categorias);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorias
        Categorias updatedCategorias = categoriasRepository.findById(categorias.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCategorias are not directly saved in db
        em.detach(updatedCategorias);
        updatedCategorias.anioInicio(UPDATED_ANIO_INICIO).anioFinal(UPDATED_ANIO_FINAL).nombreCategoria(UPDATED_NOMBRE_CATEGORIA);
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(updatedCategorias);

        restCategoriasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriasDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categoriasDTO))
            )
            .andExpect(status().isOk());

        // Validate the Categorias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCategoriasToMatchAllProperties(updatedCategorias);
    }

    @Test
    @Transactional
    void putNonExistingCategorias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorias.setId(longCount.incrementAndGet());

        // Create the Categorias
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(categorias);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categoriasDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categoriasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorias.setId(longCount.incrementAndGet());

        // Create the Categorias
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(categorias);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(categoriasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorias.setId(longCount.incrementAndGet());

        // Create the Categorias
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(categorias);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(categoriasDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categorias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategoriasWithPatch() throws Exception {
        // Initialize the database
        insertedCategorias = categoriasRepository.saveAndFlush(categorias);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorias using partial update
        Categorias partialUpdatedCategorias = new Categorias();
        partialUpdatedCategorias.setId(categorias.getId());

        partialUpdatedCategorias.nombreCategoria(UPDATED_NOMBRE_CATEGORIA);

        restCategoriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorias.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCategorias))
            )
            .andExpect(status().isOk());

        // Validate the Categorias in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategoriasUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCategorias, categorias),
            getPersistedCategorias(categorias)
        );
    }

    @Test
    @Transactional
    void fullUpdateCategoriasWithPatch() throws Exception {
        // Initialize the database
        insertedCategorias = categoriasRepository.saveAndFlush(categorias);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the categorias using partial update
        Categorias partialUpdatedCategorias = new Categorias();
        partialUpdatedCategorias.setId(categorias.getId());

        partialUpdatedCategorias.anioInicio(UPDATED_ANIO_INICIO).anioFinal(UPDATED_ANIO_FINAL).nombreCategoria(UPDATED_NOMBRE_CATEGORIA);

        restCategoriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorias.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCategorias))
            )
            .andExpect(status().isOk());

        // Validate the Categorias in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCategoriasUpdatableFieldsEquals(partialUpdatedCategorias, getPersistedCategorias(partialUpdatedCategorias));
    }

    @Test
    @Transactional
    void patchNonExistingCategorias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorias.setId(longCount.incrementAndGet());

        // Create the Categorias
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(categorias);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategoriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categoriasDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(categoriasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorias.setId(longCount.incrementAndGet());

        // Create the Categorias
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(categorias);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(categoriasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Categorias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorias() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        categorias.setId(longCount.incrementAndGet());

        // Create the Categorias
        CategoriasDTO categoriasDTO = categoriasMapper.toDto(categorias);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategoriasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(categoriasDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Categorias in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorias() throws Exception {
        // Initialize the database
        insertedCategorias = categoriasRepository.saveAndFlush(categorias);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the categorias
        restCategoriasMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorias.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return categoriasRepository.count();
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

    protected Categorias getPersistedCategorias(Categorias categorias) {
        return categoriasRepository.findById(categorias.getId()).orElseThrow();
    }

    protected void assertPersistedCategoriasToMatchAllProperties(Categorias expectedCategorias) {
        assertCategoriasAllPropertiesEquals(expectedCategorias, getPersistedCategorias(expectedCategorias));
    }

    protected void assertPersistedCategoriasToMatchUpdatableProperties(Categorias expectedCategorias) {
        assertCategoriasAllUpdatablePropertiesEquals(expectedCategorias, getPersistedCategorias(expectedCategorias));
    }
}
