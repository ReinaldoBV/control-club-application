package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.CuentasAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static cl.controlclub.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Cuentas;
import cl.controlclub.myapp.domain.enumeration.EstadoCuenta;
import cl.controlclub.myapp.domain.enumeration.TipoCuenta;
import cl.controlclub.myapp.repository.CuentasRepository;
import cl.controlclub.myapp.service.dto.CuentasDTO;
import cl.controlclub.myapp.service.mapper.CuentasMapper;
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
 * Integration tests for the {@link CuentasResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CuentasResourceIT {

    private static final TipoCuenta DEFAULT_TIPO = TipoCuenta.PAGAR;
    private static final TipoCuenta UPDATED_TIPO = TipoCuenta.COBRAR;

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MONTO = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTO = new BigDecimal(2);

    private static final Long DEFAULT_NRO_CUOTAS = 1L;
    private static final Long UPDATED_NRO_CUOTAS = 2L;

    private static final LocalDate DEFAULT_FECHA_VENCIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_VENCIMIENTO = LocalDate.now(ZoneId.systemDefault());

    private static final EstadoCuenta DEFAULT_ESTADO = EstadoCuenta.PENDIENTE;
    private static final EstadoCuenta UPDATED_ESTADO = EstadoCuenta.PAGADO;

    private static final String ENTITY_API_URL = "/api/cuentas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CuentasRepository cuentasRepository;

    @Autowired
    private CuentasMapper cuentasMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCuentasMockMvc;

    private Cuentas cuentas;

    private Cuentas insertedCuentas;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuentas createEntity(EntityManager em) {
        Cuentas cuentas = new Cuentas()
            .tipo(DEFAULT_TIPO)
            .descripcion(DEFAULT_DESCRIPCION)
            .monto(DEFAULT_MONTO)
            .nroCuotas(DEFAULT_NRO_CUOTAS)
            .fechaVencimiento(DEFAULT_FECHA_VENCIMIENTO)
            .estado(DEFAULT_ESTADO);
        return cuentas;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cuentas createUpdatedEntity(EntityManager em) {
        Cuentas cuentas = new Cuentas()
            .tipo(UPDATED_TIPO)
            .descripcion(UPDATED_DESCRIPCION)
            .monto(UPDATED_MONTO)
            .nroCuotas(UPDATED_NRO_CUOTAS)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .estado(UPDATED_ESTADO);
        return cuentas;
    }

    @BeforeEach
    public void initTest() {
        cuentas = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCuentas != null) {
            cuentasRepository.delete(insertedCuentas);
            insertedCuentas = null;
        }
    }

    @Test
    @Transactional
    void createCuentas() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Cuentas
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);
        var returnedCuentasDTO = om.readValue(
            restCuentasMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentasDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CuentasDTO.class
        );

        // Validate the Cuentas in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCuentas = cuentasMapper.toEntity(returnedCuentasDTO);
        assertCuentasUpdatableFieldsEquals(returnedCuentas, getPersistedCuentas(returnedCuentas));

        insertedCuentas = returnedCuentas;
    }

    @Test
    @Transactional
    void createCuentasWithExistingId() throws Exception {
        // Create the Cuentas with an existing ID
        cuentas.setId(1L);
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCuentasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentasDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cuentas in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuentas.setTipo(null);

        // Create the Cuentas, which fails.
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        restCuentasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDescripcionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuentas.setDescripcion(null);

        // Create the Cuentas, which fails.
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        restCuentasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMontoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuentas.setMonto(null);

        // Create the Cuentas, which fails.
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        restCuentasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNroCuotasIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuentas.setNroCuotas(null);

        // Create the Cuentas, which fails.
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        restCuentasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaVencimientoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuentas.setFechaVencimiento(null);

        // Create the Cuentas, which fails.
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        restCuentasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        cuentas.setEstado(null);

        // Create the Cuentas, which fails.
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        restCuentasMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentasDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCuentas() throws Exception {
        // Initialize the database
        insertedCuentas = cuentasRepository.saveAndFlush(cuentas);

        // Get all the cuentasList
        restCuentasMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cuentas.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].monto").value(hasItem(sameNumber(DEFAULT_MONTO))))
            .andExpect(jsonPath("$.[*].nroCuotas").value(hasItem(DEFAULT_NRO_CUOTAS.intValue())))
            .andExpect(jsonPath("$.[*].fechaVencimiento").value(hasItem(DEFAULT_FECHA_VENCIMIENTO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }

    @Test
    @Transactional
    void getCuentas() throws Exception {
        // Initialize the database
        insertedCuentas = cuentasRepository.saveAndFlush(cuentas);

        // Get the cuentas
        restCuentasMockMvc
            .perform(get(ENTITY_API_URL_ID, cuentas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cuentas.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.monto").value(sameNumber(DEFAULT_MONTO)))
            .andExpect(jsonPath("$.nroCuotas").value(DEFAULT_NRO_CUOTAS.intValue()))
            .andExpect(jsonPath("$.fechaVencimiento").value(DEFAULT_FECHA_VENCIMIENTO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCuentas() throws Exception {
        // Get the cuentas
        restCuentasMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCuentas() throws Exception {
        // Initialize the database
        insertedCuentas = cuentasRepository.saveAndFlush(cuentas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuentas
        Cuentas updatedCuentas = cuentasRepository.findById(cuentas.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCuentas are not directly saved in db
        em.detach(updatedCuentas);
        updatedCuentas
            .tipo(UPDATED_TIPO)
            .descripcion(UPDATED_DESCRIPCION)
            .monto(UPDATED_MONTO)
            .nroCuotas(UPDATED_NRO_CUOTAS)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .estado(UPDATED_ESTADO);
        CuentasDTO cuentasDTO = cuentasMapper.toDto(updatedCuentas);

        restCuentasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cuentasDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentasDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cuentas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCuentasToMatchAllProperties(updatedCuentas);
    }

    @Test
    @Transactional
    void putNonExistingCuentas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentas.setId(longCount.incrementAndGet());

        // Create the Cuentas
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuentasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cuentasDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuentas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCuentas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentas.setId(longCount.incrementAndGet());

        // Create the Cuentas
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentasMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(cuentasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuentas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCuentas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentas.setId(longCount.incrementAndGet());

        // Create the Cuentas
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentasMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(cuentasDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cuentas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCuentasWithPatch() throws Exception {
        // Initialize the database
        insertedCuentas = cuentasRepository.saveAndFlush(cuentas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuentas using partial update
        Cuentas partialUpdatedCuentas = new Cuentas();
        partialUpdatedCuentas.setId(cuentas.getId());

        partialUpdatedCuentas
            .tipo(UPDATED_TIPO)
            .descripcion(UPDATED_DESCRIPCION)
            .monto(UPDATED_MONTO)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO);

        restCuentasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuentas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCuentas))
            )
            .andExpect(status().isOk());

        // Validate the Cuentas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCuentasUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCuentas, cuentas), getPersistedCuentas(cuentas));
    }

    @Test
    @Transactional
    void fullUpdateCuentasWithPatch() throws Exception {
        // Initialize the database
        insertedCuentas = cuentasRepository.saveAndFlush(cuentas);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the cuentas using partial update
        Cuentas partialUpdatedCuentas = new Cuentas();
        partialUpdatedCuentas.setId(cuentas.getId());

        partialUpdatedCuentas
            .tipo(UPDATED_TIPO)
            .descripcion(UPDATED_DESCRIPCION)
            .monto(UPDATED_MONTO)
            .nroCuotas(UPDATED_NRO_CUOTAS)
            .fechaVencimiento(UPDATED_FECHA_VENCIMIENTO)
            .estado(UPDATED_ESTADO);

        restCuentasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCuentas.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCuentas))
            )
            .andExpect(status().isOk());

        // Validate the Cuentas in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCuentasUpdatableFieldsEquals(partialUpdatedCuentas, getPersistedCuentas(partialUpdatedCuentas));
    }

    @Test
    @Transactional
    void patchNonExistingCuentas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentas.setId(longCount.incrementAndGet());

        // Create the Cuentas
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCuentasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cuentasDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cuentasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuentas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCuentas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentas.setId(longCount.incrementAndGet());

        // Create the Cuentas
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentasMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(cuentasDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cuentas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCuentas() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        cuentas.setId(longCount.incrementAndGet());

        // Create the Cuentas
        CuentasDTO cuentasDTO = cuentasMapper.toDto(cuentas);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCuentasMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(cuentasDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cuentas in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCuentas() throws Exception {
        // Initialize the database
        insertedCuentas = cuentasRepository.saveAndFlush(cuentas);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the cuentas
        restCuentasMockMvc
            .perform(delete(ENTITY_API_URL_ID, cuentas.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return cuentasRepository.count();
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

    protected Cuentas getPersistedCuentas(Cuentas cuentas) {
        return cuentasRepository.findById(cuentas.getId()).orElseThrow();
    }

    protected void assertPersistedCuentasToMatchAllProperties(Cuentas expectedCuentas) {
        assertCuentasAllPropertiesEquals(expectedCuentas, getPersistedCuentas(expectedCuentas));
    }

    protected void assertPersistedCuentasToMatchUpdatableProperties(Cuentas expectedCuentas) {
        assertCuentasAllUpdatablePropertiesEquals(expectedCuentas, getPersistedCuentas(expectedCuentas));
    }
}
