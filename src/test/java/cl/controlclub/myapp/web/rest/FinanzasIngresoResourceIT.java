package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.FinanzasIngresoAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static cl.controlclub.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.FinanzasIngreso;
import cl.controlclub.myapp.domain.enumeration.TipoIngreso;
import cl.controlclub.myapp.repository.FinanzasIngresoRepository;
import cl.controlclub.myapp.service.dto.FinanzasIngresoDTO;
import cl.controlclub.myapp.service.mapper.FinanzasIngresoMapper;
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
 * Integration tests for the {@link FinanzasIngresoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FinanzasIngresoResourceIT {

    private static final TipoIngreso DEFAULT_TIPO = TipoIngreso.TRANSFERENCIA;
    private static final TipoIngreso UPDATED_TIPO = TipoIngreso.EFECTIVO;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTO = new BigDecimal(2);

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/finanzas-ingresos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FinanzasIngresoRepository finanzasIngresoRepository;

    @Autowired
    private FinanzasIngresoMapper finanzasIngresoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFinanzasIngresoMockMvc;

    private FinanzasIngreso finanzasIngreso;

    private FinanzasIngreso insertedFinanzasIngreso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinanzasIngreso createEntity(EntityManager em) {
        FinanzasIngreso finanzasIngreso = new FinanzasIngreso()
            .tipo(DEFAULT_TIPO)
            .descripcion(DEFAULT_DESCRIPCION)
            .monto(DEFAULT_MONTO)
            .fecha(DEFAULT_FECHA);
        return finanzasIngreso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinanzasIngreso createUpdatedEntity(EntityManager em) {
        FinanzasIngreso finanzasIngreso = new FinanzasIngreso()
            .tipo(UPDATED_TIPO)
            .descripcion(UPDATED_DESCRIPCION)
            .monto(UPDATED_MONTO)
            .fecha(UPDATED_FECHA);
        return finanzasIngreso;
    }

    @BeforeEach
    public void initTest() {
        finanzasIngreso = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFinanzasIngreso != null) {
            finanzasIngresoRepository.delete(insertedFinanzasIngreso);
            insertedFinanzasIngreso = null;
        }
    }

    @Test
    @Transactional
    void createFinanzasIngreso() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FinanzasIngreso
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);
        var returnedFinanzasIngresoDTO = om.readValue(
            restFinanzasIngresoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasIngresoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FinanzasIngresoDTO.class
        );

        // Validate the FinanzasIngreso in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFinanzasIngreso = finanzasIngresoMapper.toEntity(returnedFinanzasIngresoDTO);
        assertFinanzasIngresoUpdatableFieldsEquals(returnedFinanzasIngreso, getPersistedFinanzasIngreso(returnedFinanzasIngreso));

        insertedFinanzasIngreso = returnedFinanzasIngreso;
    }

    @Test
    @Transactional
    void createFinanzasIngresoWithExistingId() throws Exception {
        // Create the FinanzasIngreso with an existing ID
        finanzasIngreso.setId(1L);
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinanzasIngresoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasIngresoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinanzasIngreso in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        finanzasIngreso.setTipo(null);

        // Create the FinanzasIngreso, which fails.
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);

        restFinanzasIngresoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasIngresoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        finanzasIngreso.setDescripcion(null);

        // Create the FinanzasIngreso, which fails.
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);

        restFinanzasIngresoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasIngresoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        finanzasIngreso.setMonto(null);

        // Create the FinanzasIngreso, which fails.
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);

        restFinanzasIngresoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasIngresoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        finanzasIngreso.setFecha(null);

        // Create the FinanzasIngreso, which fails.
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);

        restFinanzasIngresoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasIngresoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFinanzasIngresos() throws Exception {
        // Initialize the database
        insertedFinanzasIngreso = finanzasIngresoRepository.saveAndFlush(finanzasIngreso);

        // Get all the finanzasIngresoList
        restFinanzasIngresoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finanzasIngreso.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(sameNumber(DEFAULT_MONTO))))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    void getFinanzasIngreso() throws Exception {
        // Initialize the database
        insertedFinanzasIngreso = finanzasIngresoRepository.saveAndFlush(finanzasIngreso);

        // Get the finanzasIngreso
        restFinanzasIngresoMockMvc
            .perform(get(ENTITY_API_URL_ID, finanzasIngreso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(finanzasIngreso.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.monto").value(sameNumber(DEFAULT_MONTO)))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFinanzasIngreso() throws Exception {
        // Get the finanzasIngreso
        restFinanzasIngresoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFinanzasIngreso() throws Exception {
        // Initialize the database
        insertedFinanzasIngreso = finanzasIngresoRepository.saveAndFlush(finanzasIngreso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the finanzasIngreso
        FinanzasIngreso updatedFinanzasIngreso = finanzasIngresoRepository.findById(finanzasIngreso.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFinanzasIngreso are not directly saved in db
        em.detach(updatedFinanzasIngreso);
        updatedFinanzasIngreso.tipo(UPDATED_TIPO).descripcion(UPDATED_DESCRIPCION).monto(UPDATED_MONTO).fecha(UPDATED_FECHA);
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(updatedFinanzasIngreso);

        restFinanzasIngresoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, finanzasIngresoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(finanzasIngresoDTO))
            )
            .andExpect(status().isOk());

        // Validate the FinanzasIngreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFinanzasIngresoToMatchAllProperties(updatedFinanzasIngreso);
    }

    @Test
    @Transactional
    void putNonExistingFinanzasIngreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasIngreso.setId(longCount.incrementAndGet());

        // Create the FinanzasIngreso
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanzasIngresoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, finanzasIngresoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(finanzasIngresoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanzasIngreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFinanzasIngreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasIngreso.setId(longCount.incrementAndGet());

        // Create the FinanzasIngreso
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanzasIngresoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(finanzasIngresoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanzasIngreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFinanzasIngreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasIngreso.setId(longCount.incrementAndGet());

        // Create the FinanzasIngreso
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanzasIngresoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasIngresoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FinanzasIngreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFinanzasIngresoWithPatch() throws Exception {
        // Initialize the database
        insertedFinanzasIngreso = finanzasIngresoRepository.saveAndFlush(finanzasIngreso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the finanzasIngreso using partial update
        FinanzasIngreso partialUpdatedFinanzasIngreso = new FinanzasIngreso();
        partialUpdatedFinanzasIngreso.setId(finanzasIngreso.getId());

        partialUpdatedFinanzasIngreso.monto(UPDATED_MONTO).fecha(UPDATED_FECHA);

        restFinanzasIngresoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinanzasIngreso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFinanzasIngreso))
            )
            .andExpect(status().isOk());

        // Validate the FinanzasIngreso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFinanzasIngresoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFinanzasIngreso, finanzasIngreso),
            getPersistedFinanzasIngreso(finanzasIngreso)
        );
    }

    @Test
    @Transactional
    void fullUpdateFinanzasIngresoWithPatch() throws Exception {
        // Initialize the database
        insertedFinanzasIngreso = finanzasIngresoRepository.saveAndFlush(finanzasIngreso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the finanzasIngreso using partial update
        FinanzasIngreso partialUpdatedFinanzasIngreso = new FinanzasIngreso();
        partialUpdatedFinanzasIngreso.setId(finanzasIngreso.getId());

        partialUpdatedFinanzasIngreso.tipo(UPDATED_TIPO).descripcion(UPDATED_DESCRIPCION).monto(UPDATED_MONTO).fecha(UPDATED_FECHA);

        restFinanzasIngresoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinanzasIngreso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFinanzasIngreso))
            )
            .andExpect(status().isOk());

        // Validate the FinanzasIngreso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFinanzasIngresoUpdatableFieldsEquals(
            partialUpdatedFinanzasIngreso,
            getPersistedFinanzasIngreso(partialUpdatedFinanzasIngreso)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFinanzasIngreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasIngreso.setId(longCount.incrementAndGet());

        // Create the FinanzasIngreso
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanzasIngresoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, finanzasIngresoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(finanzasIngresoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanzasIngreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFinanzasIngreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasIngreso.setId(longCount.incrementAndGet());

        // Create the FinanzasIngreso
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanzasIngresoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(finanzasIngresoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanzasIngreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFinanzasIngreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasIngreso.setId(longCount.incrementAndGet());

        // Create the FinanzasIngreso
        FinanzasIngresoDTO finanzasIngresoDTO = finanzasIngresoMapper.toDto(finanzasIngreso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanzasIngresoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(finanzasIngresoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FinanzasIngreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFinanzasIngreso() throws Exception {
        // Initialize the database
        insertedFinanzasIngreso = finanzasIngresoRepository.saveAndFlush(finanzasIngreso);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the finanzasIngreso
        restFinanzasIngresoMockMvc
            .perform(delete(ENTITY_API_URL_ID, finanzasIngreso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return finanzasIngresoRepository.count();
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

    protected FinanzasIngreso getPersistedFinanzasIngreso(FinanzasIngreso finanzasIngreso) {
        return finanzasIngresoRepository.findById(finanzasIngreso.getId()).orElseThrow();
    }

    protected void assertPersistedFinanzasIngresoToMatchAllProperties(FinanzasIngreso expectedFinanzasIngreso) {
        assertFinanzasIngresoAllPropertiesEquals(expectedFinanzasIngreso, getPersistedFinanzasIngreso(expectedFinanzasIngreso));
    }

    protected void assertPersistedFinanzasIngresoToMatchUpdatableProperties(FinanzasIngreso expectedFinanzasIngreso) {
        assertFinanzasIngresoAllUpdatablePropertiesEquals(expectedFinanzasIngreso, getPersistedFinanzasIngreso(expectedFinanzasIngreso));
    }
}
