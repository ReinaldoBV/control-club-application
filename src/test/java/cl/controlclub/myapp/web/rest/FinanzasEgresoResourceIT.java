package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.FinanzasEgresoAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static cl.controlclub.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.FinanzasEgreso;
import cl.controlclub.myapp.domain.enumeration.TipoEgreso;
import cl.controlclub.myapp.repository.FinanzasEgresoRepository;
import cl.controlclub.myapp.service.dto.FinanzasEgresoDTO;
import cl.controlclub.myapp.service.mapper.FinanzasEgresoMapper;
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
 * Integration tests for the {@link FinanzasEgresoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FinanzasEgresoResourceIT {

    private static final TipoEgreso DEFAULT_TIPO = TipoEgreso.TRANSFERENCIA;
    private static final TipoEgreso UPDATED_TIPO = TipoEgreso.EFECTIVO;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTO = new BigDecimal(2);

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/finanzas-egresos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FinanzasEgresoRepository finanzasEgresoRepository;

    @Autowired
    private FinanzasEgresoMapper finanzasEgresoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFinanzasEgresoMockMvc;

    private FinanzasEgreso finanzasEgreso;

    private FinanzasEgreso insertedFinanzasEgreso;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinanzasEgreso createEntity(EntityManager em) {
        FinanzasEgreso finanzasEgreso = new FinanzasEgreso()
            .tipo(DEFAULT_TIPO)
            .descripcion(DEFAULT_DESCRIPCION)
            .monto(DEFAULT_MONTO)
            .fecha(DEFAULT_FECHA);
        return finanzasEgreso;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FinanzasEgreso createUpdatedEntity(EntityManager em) {
        FinanzasEgreso finanzasEgreso = new FinanzasEgreso()
            .tipo(UPDATED_TIPO)
            .descripcion(UPDATED_DESCRIPCION)
            .monto(UPDATED_MONTO)
            .fecha(UPDATED_FECHA);
        return finanzasEgreso;
    }

    @BeforeEach
    public void initTest() {
        finanzasEgreso = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedFinanzasEgreso != null) {
            finanzasEgresoRepository.delete(insertedFinanzasEgreso);
            insertedFinanzasEgreso = null;
        }
    }

    @Test
    @Transactional
    void createFinanzasEgreso() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FinanzasEgreso
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);
        var returnedFinanzasEgresoDTO = om.readValue(
            restFinanzasEgresoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasEgresoDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FinanzasEgresoDTO.class
        );

        // Validate the FinanzasEgreso in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedFinanzasEgreso = finanzasEgresoMapper.toEntity(returnedFinanzasEgresoDTO);
        assertFinanzasEgresoUpdatableFieldsEquals(returnedFinanzasEgreso, getPersistedFinanzasEgreso(returnedFinanzasEgreso));

        insertedFinanzasEgreso = returnedFinanzasEgreso;
    }

    @Test
    @Transactional
    void createFinanzasEgresoWithExistingId() throws Exception {
        // Create the FinanzasEgreso with an existing ID
        finanzasEgreso.setId(1L);
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFinanzasEgresoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasEgresoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FinanzasEgreso in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        finanzasEgreso.setTipo(null);

        // Create the FinanzasEgreso, which fails.
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);

        restFinanzasEgresoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasEgresoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        finanzasEgreso.setDescripcion(null);

        // Create the FinanzasEgreso, which fails.
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);

        restFinanzasEgresoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasEgresoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        finanzasEgreso.setMonto(null);

        // Create the FinanzasEgreso, which fails.
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);

        restFinanzasEgresoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasEgresoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        finanzasEgreso.setFecha(null);

        // Create the FinanzasEgreso, which fails.
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);

        restFinanzasEgresoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasEgresoDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFinanzasEgresos() throws Exception {
        // Initialize the database
        insertedFinanzasEgreso = finanzasEgresoRepository.saveAndFlush(finanzasEgreso);

        // Get all the finanzasEgresoList
        restFinanzasEgresoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(finanzasEgreso.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(sameNumber(DEFAULT_MONTO))))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())));
    }

    @Test
    @Transactional
    void getFinanzasEgreso() throws Exception {
        // Initialize the database
        insertedFinanzasEgreso = finanzasEgresoRepository.saveAndFlush(finanzasEgreso);

        // Get the finanzasEgreso
        restFinanzasEgresoMockMvc
            .perform(get(ENTITY_API_URL_ID, finanzasEgreso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(finanzasEgreso.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.monto").value(sameNumber(DEFAULT_MONTO)))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFinanzasEgreso() throws Exception {
        // Get the finanzasEgreso
        restFinanzasEgresoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFinanzasEgreso() throws Exception {
        // Initialize the database
        insertedFinanzasEgreso = finanzasEgresoRepository.saveAndFlush(finanzasEgreso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the finanzasEgreso
        FinanzasEgreso updatedFinanzasEgreso = finanzasEgresoRepository.findById(finanzasEgreso.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFinanzasEgreso are not directly saved in db
        em.detach(updatedFinanzasEgreso);
        updatedFinanzasEgreso.tipo(UPDATED_TIPO).descripcion(UPDATED_DESCRIPCION).monto(UPDATED_MONTO).fecha(UPDATED_FECHA);
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(updatedFinanzasEgreso);

        restFinanzasEgresoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, finanzasEgresoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(finanzasEgresoDTO))
            )
            .andExpect(status().isOk());

        // Validate the FinanzasEgreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFinanzasEgresoToMatchAllProperties(updatedFinanzasEgreso);
    }

    @Test
    @Transactional
    void putNonExistingFinanzasEgreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasEgreso.setId(longCount.incrementAndGet());

        // Create the FinanzasEgreso
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanzasEgresoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, finanzasEgresoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(finanzasEgresoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanzasEgreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFinanzasEgreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasEgreso.setId(longCount.incrementAndGet());

        // Create the FinanzasEgreso
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanzasEgresoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(finanzasEgresoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanzasEgreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFinanzasEgreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasEgreso.setId(longCount.incrementAndGet());

        // Create the FinanzasEgreso
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanzasEgresoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(finanzasEgresoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FinanzasEgreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFinanzasEgresoWithPatch() throws Exception {
        // Initialize the database
        insertedFinanzasEgreso = finanzasEgresoRepository.saveAndFlush(finanzasEgreso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the finanzasEgreso using partial update
        FinanzasEgreso partialUpdatedFinanzasEgreso = new FinanzasEgreso();
        partialUpdatedFinanzasEgreso.setId(finanzasEgreso.getId());

        partialUpdatedFinanzasEgreso.descripcion(UPDATED_DESCRIPCION).monto(UPDATED_MONTO);

        restFinanzasEgresoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinanzasEgreso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFinanzasEgreso))
            )
            .andExpect(status().isOk());

        // Validate the FinanzasEgreso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFinanzasEgresoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFinanzasEgreso, finanzasEgreso),
            getPersistedFinanzasEgreso(finanzasEgreso)
        );
    }

    @Test
    @Transactional
    void fullUpdateFinanzasEgresoWithPatch() throws Exception {
        // Initialize the database
        insertedFinanzasEgreso = finanzasEgresoRepository.saveAndFlush(finanzasEgreso);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the finanzasEgreso using partial update
        FinanzasEgreso partialUpdatedFinanzasEgreso = new FinanzasEgreso();
        partialUpdatedFinanzasEgreso.setId(finanzasEgreso.getId());

        partialUpdatedFinanzasEgreso.tipo(UPDATED_TIPO).descripcion(UPDATED_DESCRIPCION).monto(UPDATED_MONTO).fecha(UPDATED_FECHA);

        restFinanzasEgresoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFinanzasEgreso.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFinanzasEgreso))
            )
            .andExpect(status().isOk());

        // Validate the FinanzasEgreso in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFinanzasEgresoUpdatableFieldsEquals(partialUpdatedFinanzasEgreso, getPersistedFinanzasEgreso(partialUpdatedFinanzasEgreso));
    }

    @Test
    @Transactional
    void patchNonExistingFinanzasEgreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasEgreso.setId(longCount.incrementAndGet());

        // Create the FinanzasEgreso
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFinanzasEgresoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, finanzasEgresoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(finanzasEgresoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanzasEgreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFinanzasEgreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasEgreso.setId(longCount.incrementAndGet());

        // Create the FinanzasEgreso
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanzasEgresoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(finanzasEgresoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FinanzasEgreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFinanzasEgreso() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        finanzasEgreso.setId(longCount.incrementAndGet());

        // Create the FinanzasEgreso
        FinanzasEgresoDTO finanzasEgresoDTO = finanzasEgresoMapper.toDto(finanzasEgreso);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFinanzasEgresoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(finanzasEgresoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FinanzasEgreso in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFinanzasEgreso() throws Exception {
        // Initialize the database
        insertedFinanzasEgreso = finanzasEgresoRepository.saveAndFlush(finanzasEgreso);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the finanzasEgreso
        restFinanzasEgresoMockMvc
            .perform(delete(ENTITY_API_URL_ID, finanzasEgreso.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return finanzasEgresoRepository.count();
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

    protected FinanzasEgreso getPersistedFinanzasEgreso(FinanzasEgreso finanzasEgreso) {
        return finanzasEgresoRepository.findById(finanzasEgreso.getId()).orElseThrow();
    }

    protected void assertPersistedFinanzasEgresoToMatchAllProperties(FinanzasEgreso expectedFinanzasEgreso) {
        assertFinanzasEgresoAllPropertiesEquals(expectedFinanzasEgreso, getPersistedFinanzasEgreso(expectedFinanzasEgreso));
    }

    protected void assertPersistedFinanzasEgresoToMatchUpdatableProperties(FinanzasEgreso expectedFinanzasEgreso) {
        assertFinanzasEgresoAllUpdatablePropertiesEquals(expectedFinanzasEgreso, getPersistedFinanzasEgreso(expectedFinanzasEgreso));
    }
}
