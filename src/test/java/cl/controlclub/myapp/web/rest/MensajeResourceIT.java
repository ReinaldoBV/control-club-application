package cl.controlclub.myapp.web.rest;

import static cl.controlclub.myapp.domain.MensajeAsserts.*;
import static cl.controlclub.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cl.controlclub.myapp.IntegrationTest;
import cl.controlclub.myapp.domain.Mensaje;
import cl.controlclub.myapp.repository.MensajeRepository;
import cl.controlclub.myapp.service.dto.MensajeDTO;
import cl.controlclub.myapp.service.mapper.MensajeMapper;
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
 * Integration tests for the {@link MensajeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MensajeResourceIT {

    private static final Long DEFAULT_REMITENTE_ID = 1L;
    private static final Long UPDATED_REMITENTE_ID = 2L;

    private static final Long DEFAULT_DESTINATARIO_ID = 1L;
    private static final Long UPDATED_DESTINATARIO_ID = 2L;

    private static final String DEFAULT_MENSAJE = "AAAAAAAAAA";
    private static final String UPDATED_MENSAJE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_LEIDO = false;
    private static final Boolean UPDATED_LEIDO = true;

    private static final String ENTITY_API_URL = "/api/mensajes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private MensajeMapper mensajeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMensajeMockMvc;

    private Mensaje mensaje;

    private Mensaje insertedMensaje;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mensaje createEntity(EntityManager em) {
        Mensaje mensaje = new Mensaje()
            .remitenteId(DEFAULT_REMITENTE_ID)
            .destinatarioId(DEFAULT_DESTINATARIO_ID)
            .mensaje(DEFAULT_MENSAJE)
            .leido(DEFAULT_LEIDO);
        return mensaje;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Mensaje createUpdatedEntity(EntityManager em) {
        Mensaje mensaje = new Mensaje()
            .remitenteId(UPDATED_REMITENTE_ID)
            .destinatarioId(UPDATED_DESTINATARIO_ID)
            .mensaje(UPDATED_MENSAJE)
            .leido(UPDATED_LEIDO);
        return mensaje;
    }

    @BeforeEach
    public void initTest() {
        mensaje = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedMensaje != null) {
            mensajeRepository.delete(insertedMensaje);
            insertedMensaje = null;
        }
    }

    @Test
    @Transactional
    void createMensaje() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Mensaje
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);
        var returnedMensajeDTO = om.readValue(
            restMensajeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MensajeDTO.class
        );

        // Validate the Mensaje in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMensaje = mensajeMapper.toEntity(returnedMensajeDTO);
        assertMensajeUpdatableFieldsEquals(returnedMensaje, getPersistedMensaje(returnedMensaje));

        insertedMensaje = returnedMensaje;
    }

    @Test
    @Transactional
    void createMensajeWithExistingId() throws Exception {
        // Create the Mensaje with an existing ID
        mensaje.setId(1L);
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMensajeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Mensaje in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRemitenteIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mensaje.setRemitenteId(null);

        // Create the Mensaje, which fails.
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        restMensajeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDestinatarioIdIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mensaje.setDestinatarioId(null);

        // Create the Mensaje, which fails.
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        restMensajeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMensajeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        mensaje.setMensaje(null);

        // Create the Mensaje, which fails.
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        restMensajeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMensajes() throws Exception {
        // Initialize the database
        insertedMensaje = mensajeRepository.saveAndFlush(mensaje);

        // Get all the mensajeList
        restMensajeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mensaje.getId().intValue())))
            .andExpect(jsonPath("$.[*].remitenteId").value(hasItem(DEFAULT_REMITENTE_ID.intValue())))
            .andExpect(jsonPath("$.[*].destinatarioId").value(hasItem(DEFAULT_DESTINATARIO_ID.intValue())))
            .andExpect(jsonPath("$.[*].mensaje").value(hasItem(DEFAULT_MENSAJE)))
            .andExpect(jsonPath("$.[*].leido").value(hasItem(DEFAULT_LEIDO.booleanValue())));
    }

    @Test
    @Transactional
    void getMensaje() throws Exception {
        // Initialize the database
        insertedMensaje = mensajeRepository.saveAndFlush(mensaje);

        // Get the mensaje
        restMensajeMockMvc
            .perform(get(ENTITY_API_URL_ID, mensaje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mensaje.getId().intValue()))
            .andExpect(jsonPath("$.remitenteId").value(DEFAULT_REMITENTE_ID.intValue()))
            .andExpect(jsonPath("$.destinatarioId").value(DEFAULT_DESTINATARIO_ID.intValue()))
            .andExpect(jsonPath("$.mensaje").value(DEFAULT_MENSAJE))
            .andExpect(jsonPath("$.leido").value(DEFAULT_LEIDO.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingMensaje() throws Exception {
        // Get the mensaje
        restMensajeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMensaje() throws Exception {
        // Initialize the database
        insertedMensaje = mensajeRepository.saveAndFlush(mensaje);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mensaje
        Mensaje updatedMensaje = mensajeRepository.findById(mensaje.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMensaje are not directly saved in db
        em.detach(updatedMensaje);
        updatedMensaje
            .remitenteId(UPDATED_REMITENTE_ID)
            .destinatarioId(UPDATED_DESTINATARIO_ID)
            .mensaje(UPDATED_MENSAJE)
            .leido(UPDATED_LEIDO);
        MensajeDTO mensajeDTO = mensajeMapper.toDto(updatedMensaje);

        restMensajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mensajeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Mensaje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMensajeToMatchAllProperties(updatedMensaje);
    }

    @Test
    @Transactional
    void putNonExistingMensaje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensaje.setId(longCount.incrementAndGet());

        // Create the Mensaje
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mensajeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mensaje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMensaje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensaje.setId(longCount.incrementAndGet());

        // Create the Mensaje
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensajeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(mensajeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mensaje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMensaje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensaje.setId(longCount.incrementAndGet());

        // Create the Mensaje
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensajeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(mensajeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mensaje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMensajeWithPatch() throws Exception {
        // Initialize the database
        insertedMensaje = mensajeRepository.saveAndFlush(mensaje);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mensaje using partial update
        Mensaje partialUpdatedMensaje = new Mensaje();
        partialUpdatedMensaje.setId(mensaje.getId());

        restMensajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMensaje.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMensaje))
            )
            .andExpect(status().isOk());

        // Validate the Mensaje in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMensajeUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedMensaje, mensaje), getPersistedMensaje(mensaje));
    }

    @Test
    @Transactional
    void fullUpdateMensajeWithPatch() throws Exception {
        // Initialize the database
        insertedMensaje = mensajeRepository.saveAndFlush(mensaje);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the mensaje using partial update
        Mensaje partialUpdatedMensaje = new Mensaje();
        partialUpdatedMensaje.setId(mensaje.getId());

        partialUpdatedMensaje
            .remitenteId(UPDATED_REMITENTE_ID)
            .destinatarioId(UPDATED_DESTINATARIO_ID)
            .mensaje(UPDATED_MENSAJE)
            .leido(UPDATED_LEIDO);

        restMensajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMensaje.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMensaje))
            )
            .andExpect(status().isOk());

        // Validate the Mensaje in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMensajeUpdatableFieldsEquals(partialUpdatedMensaje, getPersistedMensaje(partialUpdatedMensaje));
    }

    @Test
    @Transactional
    void patchNonExistingMensaje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensaje.setId(longCount.incrementAndGet());

        // Create the Mensaje
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMensajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mensajeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mensajeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mensaje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMensaje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensaje.setId(longCount.incrementAndGet());

        // Create the Mensaje
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensajeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(mensajeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Mensaje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMensaje() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        mensaje.setId(longCount.incrementAndGet());

        // Create the Mensaje
        MensajeDTO mensajeDTO = mensajeMapper.toDto(mensaje);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMensajeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(mensajeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Mensaje in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMensaje() throws Exception {
        // Initialize the database
        insertedMensaje = mensajeRepository.saveAndFlush(mensaje);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the mensaje
        restMensajeMockMvc
            .perform(delete(ENTITY_API_URL_ID, mensaje.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return mensajeRepository.count();
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

    protected Mensaje getPersistedMensaje(Mensaje mensaje) {
        return mensajeRepository.findById(mensaje.getId()).orElseThrow();
    }

    protected void assertPersistedMensajeToMatchAllProperties(Mensaje expectedMensaje) {
        assertMensajeAllPropertiesEquals(expectedMensaje, getPersistedMensaje(expectedMensaje));
    }

    protected void assertPersistedMensajeToMatchUpdatableProperties(Mensaje expectedMensaje) {
        assertMensajeAllUpdatablePropertiesEquals(expectedMensaje, getPersistedMensaje(expectedMensaje));
    }
}
